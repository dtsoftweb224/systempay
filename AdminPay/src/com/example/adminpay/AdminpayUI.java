package com.example.adminpay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.Bik;
import com.example.adminpay.classes.Client;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.Kommis;
import com.example.adminpay.classes.KommisDB;
import com.example.adminpay.classes.RegZayvki;
import com.example.adminpay.classes.RegZayvkiDB;
import com.example.adminpay.classes.SMS;
import com.example.adminpay.classes.Users;
import com.example.adminpay.classes.UsersDB;
import com.example.adminpay.support.SendSMS;
import com.example.adminpay.window.ModalWindowBik;
import com.example.adminpay.window.ModalWindowClient;
import com.example.adminpay.window.ModalWindowUsers;
import com.example.adminpay.window.WindowHistoryClient;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

/**
 * Main UI class
 */
@Theme("admintheme")
@SuppressWarnings("serial")
public class AdminpayUI extends UI {
	
	private VerticalLayout layout;
	private MenuBar mainMenu = null;
	private MenuItem zayvki = null;
	private MenuItem files = null;
	private MenuItem logs = null;
	private MenuItem spsr = null;
	private MenuItem user = null;
	private MenuItem kommis = null;
	
	private MenuItem add_kommis = null;
	private MenuItem edit_kommis = null;
	private MenuItem del_kommis = null;
	
	private Table registryTable;
	private Table bikTable;
	private Table clientTable;
	private Table smsTable;
	private Table usersTable;
	private Table kommisTable;
	
	private Table current;
	
	private BeanItemContainer<RegZayvki> beansZayvkaReg = new BeanItemContainer<RegZayvki>(RegZayvki.class);
	private BeanItemContainer<Bik> beansBik = new BeanItemContainer<Bik>(Bik.class);
	private BeanItemContainer<Client> beansClient = new BeanItemContainer<Client>(Client.class);
	private BeanItemContainer<SMS> beansSMS = new BeanItemContainer<SMS>(SMS.class);
	private BeanItemContainer<Users> beansUsers = new BeanItemContainer<Users>(Users.class);
	private BeanItemContainer<Kommis> beansKommis = new BeanItemContainer<Kommis>(Kommis.class);
	
	private String state;
	
	/* Поля таблицы - Регистрация заявок */
	private Object[] tableRegFields = new Object[] {"id_zayvki", "data", 
			"summa", "payIn", "payOut", "fioClient", "mailClient"};
	/* Заголовки таблицы - Регистрация заявок */
	private String[] tableRegFieldsTitle = new String[] {"Номер заявки", 
			"Дата", "Сумма", "Источник платежа", "Адресат платежа", "ФИО клиента", 
			"mail клиента" };
	
	/* Поля таблицы - Клиенты */
	private Object[] tableClientFields = new Object[] {"telephone", 
			"serial", "number", "fio", "mail", "adress"};
	/* Заголовки таблицы - Клиенты */
	private String[] tableClientFieldsTitle = new String[] {"Телефон", 
			"Серия паспорта", "Номер паспорта",	"ФИО клиента", "Электронная почта",
			"Адрес"};
	
	/* Поля таблицы - Банки БИК */
	private Object[] tableBikFields = new Object[] {"bik", "swift", 
			"bank", "form_nal", "form_beznal"};
	/* Заголовки таблицы - Банки БИК */
	private String[] tableBikFieldsTitle = new String[] {"БИК", "СВИФТ", 
			"Наименование банка", "Отображение наличные", "Отображение безналичные"};
	
	/* Поля таблицы - Статус SMS */
	private Object[] tableSMSFields = new Object[] {"id_sms", "data", 
			"telephone", "status", "numPay"};
	/* Заголовки таблицы - Статус SMS */
	private String[] tableSMSFieldsTitle = new String[] {"Идентифкатор", 
			"Дата", "Номер телефона", "Статус", "Номер платежа"};
	
	/* Поля таблицы - Пользователи */
	private Object[] tableUsersFields = new Object[] {"login", 
			"fio", "type", "telephone"};
	/* Заголовки таблицы - Пользователи */
	private String[] tableUsersFieldsTitle = new String[] { 
			"Логин", "ФИО пользователя", "Роль", "Телефон"};
	
	/* Поля таблицы - Коммисии */
	private Object[] tableKommisFields = new Object[] {"id", "paySystem", 
			"komSystem", "kommis", "minKommis", "maxKommis"};
	/* Заголовки таблицы - Коммисии */
	private String[] tableKommisFieldsTitle = new String[] {"Идентифкатор", 
			"Платежная система", "Комиссия системы", "Наша комиссия", 
			"Мин. комиссия", "Макс. комиссия"};
	
	private BeanItem<Client> tmpClient = null;
	private BeanItem<Bik> tmpBik = null;
	private BeanItem<SMS> tmpSms = null;
	private BeanItem<Users> tmpUsers = null;
	private BeanItem<Kommis> tmpKommis = null;
	
	private final Action ACTION_STATUS = new Action("Проверить статус");
	private final Action[] ACTIONS_MARKED = new Action[] { ACTION_STATUS};
	
	@Override
	protected void init(VaadinRequest request) {
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);		
		
		buildMainMenu();
		buildRegistryTable();
	}
	
	private void buildMainMenu() {
		
		mainMenu = new MenuBar();
		
		MenuBar.Command myCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				state = selectedItem.getText();	
				
				if (state.equals("Отправленные")) {
					layout.removeComponent(current);
					layout.addComponent(registryTable);
					current = registryTable;
				}
				
				if (state.equals("Заявки")) {
					
				}
				
				if (state.equals("Файлы")) {
					
				}
				
				if (state.equals("Статусы SMS")) {
					
					buildSMSTable();
					layout.removeComponent(current);
					layout.addComponent(smsTable);
					current = smsTable;
				}
				
				if (state.equals("Банки БИК")) {
					
					buildBikTable();
					layout.removeComponent(current);
					layout.addComponent(bikTable);
					current = bikTable;
				}				
				
				if (state.equals("Клиенты")) {
					
					buildClientTable();
					layout.removeComponent(current);
					layout.addComponent(clientTable);
					current = clientTable;
				}
				
				if (state.equals("Пользователи")) {
					
					buildUserTable();
					layout.removeComponent(current);
					layout.addComponent(usersTable);
					current = usersTable;
				}
				
				if (state.equals("Комиссии")) {
					
					buildKommisTable();
					layout.removeComponent(current);
					layout.addComponent(kommisTable);
					current = kommisTable;
				}
				
				// Добавить нового пользователя
				if (state.equals("Добавить")) {
					
					if (current == usersTable) {
						Window win = new ModalWindowUsers(null);					
						UI.getCurrent().addWindow(win);
					}
				}
				
				// Редактировать выбранного пользователя
				if (state.equals("Редактировать")) {
					
					if (current == usersTable) {
						Window win = new ModalWindowUsers(tmpUsers);					
						UI.getCurrent().addWindow(win);
					}
				}
				
			}
		};
		
		
		zayvki = mainMenu.addItem("Заявки" , null, null);
		files = mainMenu.addItem("Файлы", null, null); 
		logs = mainMenu.addItem("Логи", null, null);
		spsr = mainMenu.addItem("Справочники", null, null);
		
		// 
		zayvki.addItem("Отправленные", myCommand);
		zayvki.addItem("Измененные", myCommand);
		zayvki.addItem("Выполенные", myCommand);
		
		// 
		files.addItem("Безналичный", myCommand);
		files.addItem("Наличный", myCommand);
		
		// 
		logs.addItem("Вход", myCommand);
		logs.addItem("Статусы SMS", myCommand);
		
		// 
		user = spsr.addItem("Пользователи", myCommand);
		spsr.addItem("Клиенты", myCommand);
		spsr.addItem("Курсы", myCommand);
		spsr.addItem("Банки БИК", myCommand);
		kommis = spsr.addItem("Комиссии", myCommand);
		
		// Подменю меню Пользователи
		user.addItem("Добавить", myCommand);
		user.addItem("Редактировать", myCommand);
		user.addItem("Удалить", myCommand);
		
		add_kommis = kommis.addItem("Добавить комиссию", myCommand);
		edit_kommis = kommis.addItem("Редактировать комиссию", myCommand);
		del_kommis = kommis.addItem("Удалить комиссию", myCommand);
		
		// Изначально скрываем пукнты меню, подменю Комиссия.
		add_kommis.setVisible(false);
		edit_kommis.setVisible(false);
		del_kommis.setVisible(false);
		
		layout.addComponent(mainMenu);
		
	}
	
	/* Формирование таблицы регистрации заявок */
	private void buildRegistryTable() {
	
		RegZayvkiDB regDB = new RegZayvkiDB(DB.getConnection());		
		
		try {
			List<RegZayvki> list = regDB.getAllZayvki();
			for (int i = 0; i < list.size(); i++) {				
				beansZayvkaReg.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		registryTable = new Table("", beansZayvkaReg);
		registryTable.setSelectable(true);
		registryTable.setWidth("100%");
		registryTable.setHeight("550px");
	
		registryTable.setVisibleColumns(tableRegFields);
	
		for (int i = 0; i < tableRegFields.length; i++) {
			registryTable.setColumnHeader(tableRegFields[i], tableRegFieldsTitle[i]);
		}
	
		layout.addComponent(registryTable);
		current = registryTable;
	}
	
	/* Формирование таблицы БИКОВ */
	private void buildBikTable() {	
		
		beansBik.removeAllItems();
		
		try {
			List<Bik> list = BaseRW.getAllBik(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansBik.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		bikTable = new Table("", beansBik);
		bikTable.setSelectable(true);
		bikTable.setWidth("100%");
		bikTable.setHeight("550px");
	
		bikTable.setVisibleColumns(tableBikFields);
	
		for (int i = 0; i < tableBikFields.length; i++) {
			bikTable.setColumnHeader(tableBikFields[i], tableBikFieldsTitle[i]);
		}	
		
		bikTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					tmpBik = beansBik.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new ModalWindowBik(tmpBik);					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
	
	}
	
	/* Формирование таблицы клиентов */
	private void buildClientTable() {	
		
		beansClient.removeAllItems();
		
		try {
			List<Client> list = BaseRW.getAllClient(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansClient.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		clientTable = new Table("", beansClient);
		clientTable.setSelectable(true);
		clientTable.setWidth("100%");
		clientTable.setHeight("550px");
	
		clientTable.setVisibleColumns(tableClientFields);
	
		for (int i = 0; i < tableClientFields.length; i++) {
			clientTable.setColumnHeader(tableClientFields[i], tableClientFieldsTitle[i]);
		}
		
		clientTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					tmpClient = beansClient.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new WindowHistoryClient(tmpClient.getBean().getMail());					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
	
	}
	
	/* Формирование таблицы клиентов */
	private void buildSMSTable() {	
		
		beansSMS.removeAllItems();
		
		try {
			List<SMS> list = BaseRW.getAllRegSMS(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansSMS.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		smsTable = new Table("", beansSMS);
		smsTable.setSelectable(true);
		smsTable.setWidth("100%");
		smsTable.setHeight("550px");
	
		smsTable.setVisibleColumns(tableSMSFields);
	
		for (int i = 0; i < tableSMSFields.length; i++) {
			smsTable.setColumnHeader(tableSMSFields[i], tableSMSFieldsTitle[i]);
		}
		
		smsTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * 
				 */				
				if (event.isDoubleClick()) {			
					
				} 
				tmpSms = beansSMS.getItem(event.getItemId());
			}
		});
		
		smsTable.addActionHandler(new Action.Handler() {			
			@Override
			public void handleAction(Action action, Object sender, Object target) {		
			
				/* Вызов функции проверки статуса SMS */
				if (ACTION_STATUS == action) {
					SendSMS sms = new SendSMS();
					String id = String.valueOf(tmpSms.getBean().getId_sms());
					String str = "";
					try {
						str = sms.verifyState(id);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					MessageBox.showPlain(Icon.INFO, "Проверка статуса", 
							"Статус сообщения SMS "+str, ButtonId.OK);
				}				
			}			
			@Override
			public Action[] getActions(Object target, Object sender) {				
				return ACTIONS_MARKED;							
			}
		});
	}
	
	private void buildUserTable() {
		
		beansUsers.removeAllItems();
		
		try {
			UsersDB userDB = new UsersDB(DB.getConnection());
			List<Users> list = userDB.getAllUsers();
			for (int i = 0; i < list.size(); i++) {				
				beansUsers.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		usersTable = new Table("", beansUsers);
		usersTable.setSelectable(true);
		usersTable.setWidth("100%");
		usersTable.setHeight("550px");
	
		usersTable.setVisibleColumns(tableUsersFields);
	
		for (int i = 0; i < tableUsersFields.length; i++) {
			usersTable.setColumnHeader(tableUsersFields[i], tableUsersFieldsTitle[i]);
		}
		
		usersTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					tmpUsers = beansUsers.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new ModalWindowUsers(tmpUsers);					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
	}
	
	/**
	 * Создание таблицы коммисий
	 */
	private void buildKommisTable() {
		
		beansKommis.removeAllItems();
		
		try {
			KommisDB kommisDB = new KommisDB(DB.getConnection());
			List<Kommis> list = kommisDB.getAllKommis();
			for (int i = 0; i < list.size(); i++) {				
				beansKommis.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		kommisTable = new Table("", beansKommis);
		kommisTable.setSelectable(true);
		kommisTable.setWidth("100%");
		kommisTable.setHeight("550px");
	
		kommisTable.setVisibleColumns(tableKommisFields);
	
		for (int i = 0; i < tableKommisFields.length; i++) {
			kommisTable.setColumnHeader(tableKommisFields[i], tableKommisFieldsTitle[i]);
		}
		
		kommisTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					tmpKommis = beansKommis.getItem(event.getItemId());
					// Создание окна редактирования заявки
					//Window win = new ModalWindowUsers(tmpUsers);					
					//UI.getCurrent().addWindow(win);
				} 			
			}
		});
		
	}
	
}