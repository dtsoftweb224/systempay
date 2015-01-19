package com.example.adminpay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.Bik;
import com.example.adminpay.classes.Client;
import com.example.adminpay.classes.ClientUr;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.Kommis;
import com.example.adminpay.classes.KommisDB;
import com.example.adminpay.classes.Logs;
import com.example.adminpay.classes.PaySystem;
import com.example.adminpay.classes.Rate;
import com.example.adminpay.classes.RegZayvki;
import com.example.adminpay.classes.RegZayvkiDB;
import com.example.adminpay.classes.SMS;
import com.example.adminpay.classes.Users;
import com.example.adminpay.classes.UsersDB;
import com.example.adminpay.classes.Valuta;
import com.example.adminpay.support.Auth;
import com.example.adminpay.support.SendSMS;
import com.example.adminpay.window.ModalWindowBik;
import com.example.adminpay.window.ModalWindowClient;
import com.example.adminpay.window.ModalWindowClientUr;
import com.example.adminpay.window.ModalWindowKommis;
import com.example.adminpay.window.ModalWindowPaySystem;
import com.example.adminpay.window.ModalWindowPaySystemNew;
import com.example.adminpay.window.ModalWindowRate;
import com.example.adminpay.window.ModalWindowUsers;
import com.example.adminpay.window.WindowHistoryClient;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.UIEvents;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
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
	private MenuItem money   = null;   // Электронные деньги
	
	private MenuItem add_kommis = null;
	private MenuItem edit_kommis = null;
	private MenuItem del_kommis = null;
	
	private VerticalLayout toolLayout = null;  // Слой для фильтрации/поиска
	private VerticalLayout tableLayout = null; // Слой для таблиц
	
	private Table registryTable;
	private Table bikTable;
	private Table clientTable;
	private Table clientUrTable;
	private Table smsTable;
	private Table usersTable;
	private Table kommisTable;
	private Table valutaTable;
	private Table payTable;  	// Платежные системы
	private Table rateTable; 	// Тарифы
	private Table logsTable;    // Логи
	
	private Table current;
	private Table newTable;
	
	// Поля для фильтрации созданных заявок
	private GridLayout setLayout = null;
	private PopupDateField data1 = null; // Поле для фильтрации даты с
	private PopupDateField data2 = null; // Поле для фильтрации даты по
	private TextField ipArress = null;   // Поле для фильтрации ip адрес
	private TextField fioClient = null;  // Поле для фильтрации фио клиент
	private TextField numZayvki = null;  // Поле для фильтрации номер заявки 
	private TextField numPurse = null;   // Поле для фильтрации номер 
	private Button btnFilter = null;	 // Кнопка ФИЛЬТР
	private Button btnCancel = null;	 // Кнопка СБРОС фильтрации
	
	// Панель поиска по БИКу
	private GridLayout searchBikPanel = null; 
	private TextField bikNumber = null;   // Поле для ввода номера БИКа
	private Button btnSerachBik = null;	  // Кнопка ПОИСК		
	private Button btnCancelBik = null;   // Кнопка СБРОС
	
	// Панель фильтрации курсов по дате
	private GridLayout searchValutaPanel = null; 
	private PopupDateField dataKurs = null;   // Поле для ввода даты курса
	private Button btnFilterValuta = null;	  // Кнопка ПОИСК		
	private Button btnCancelValuta = null;    // Кнопка СБРОС
	
	// Панель фильтрации юр. лиц
	private GridLayout filterClietnUrPanel = null;
	private TextField innClient = null;
	private TextField kppClient = null;
	private TextField fioUrClient = null;
	private Button btnFilterClient = null;	  // Кнопка ФИЛЬТР		
	private Button btnCancelClient = null;    // Кнопка СБРОС
	
	// Панель авторизации пользователя
	private GridLayout gl = null;
	private PasswordField passPf = null;
	private TextField userName = null;
	
	// Панель фильтрации для тарифов
	private GridLayout filterRatePanel = null;
	private ComboBox operation = null;			// Тип операции
	private ComboBox typeRaschet = null;
	private ComboBox objectPay = null;
	private Button btnFilterRate = null;	  // Кнопка ФИЛЬТР		
	private Button btnCancelRate = null;    // Кнопка СБРОС
	
	private BeanItemContainer<RegZayvki> beansZayvkaReg = new BeanItemContainer<RegZayvki>(RegZayvki.class);
	private BeanItemContainer<Bik> beansBik = new BeanItemContainer<Bik>(Bik.class);
	private BeanItemContainer<Client> beansClient = new BeanItemContainer<Client>(Client.class);
	private BeanItemContainer<ClientUr> beansClientUr = new BeanItemContainer<ClientUr>(ClientUr.class);
	private BeanItemContainer<SMS> beansSMS = new BeanItemContainer<SMS>(SMS.class);
	private BeanItemContainer<Users> beansUsers = new BeanItemContainer<Users>(Users.class);
	private BeanItemContainer<Kommis> beansKommis = new BeanItemContainer<Kommis>(Kommis.class);	
	private BeanItemContainer<Valuta> beansValuta = new BeanItemContainer<Valuta>(Valuta.class);
	private BeanItemContainer<PaySystem> beansPay = new BeanItemContainer<PaySystem>(PaySystem.class);
	private BeanItemContainer<Rate> beansRate = new BeanItemContainer<Rate>(Rate.class);
	private BeanItemContainer<Logs> beansLogs = new BeanItemContainer<Logs>(Logs.class);
	
	private String state;
	private String nameTable = "";
	private int timeRefresh = 0;
	private boolean stateUpdate = false;
	
	/* Поля таблицы - Регистрация заявок */
	private Object[] tableRegFields = new Object[] {"id_zayvki", "data", 
			"summa", "payIn", "payOut", "fioClient", "mailClient", 
			"status", "ip_adress"};
	/* Заголовки таблицы - Регистрация заявок */
	private String[] tableRegFieldsTitle = new String[] {"Номер заявки", 
			"Дата", "Сумма", "Номер кошелька", "Адресат платежа", "ФИО клиента", 
			"mail клиента", "Статус", "IP адрес" };
	
	/* Поля таблицы - Клиенты */
	private Object[] tableClientFields = new Object[] {"telephone", 
			"fio", "mail"};
	/* Заголовки таблицы - Клиенты */
	private String[] tableClientFieldsTitle = new String[] {"Телефон", 
			"ФИО клиента", "Электронная почта"};
	
	/* Поля таблицы - <a> */
	private Object[] tableClientUrFields = new Object[] {"ogrn", 
			"inn", "kpp", "fioDirector", "mail", "telephone"};
	/* Заголовки таблицы - Клиенты */
	private String[] tableClientUrFieldsTitle = new String[] {"ОГРН", 
			"ИНН", "КПП", "ФИО директора", "Mail", "Телефон"};
	
	/* Поля таблицы - Банки БИК */
	private Object[] tableBikFields = new Object[] {"bik",  
			"ks", "name", "ind", "city", "address", "tranzit", "phone",
			"okato", "okpo"};
	/* Заголовки таблицы - Банки БИК */
	private String[] tableBikFieldsTitle = new String[] {"БИК",  
			"Корр. счет", "Название", "Индекс", "Город", 
			"Адрес", "Транзитный счет", "Телефон", "ОКАТО", "ОКПО"};
	
	/* Поля таблицы - Платежные системы */
	private Object[] payFields = new Object[] {"pay", "list_val"};
	/* Заголовки таблицы - Платежные системы */
	private String[] payFieldsTitle = new String[] {"Платежная система", 
			"Типы валют"};
	
	/* Поля таблицы - Комиссии */
	private Object[] tableRateFields = new Object[] {"operation", "type_paschet", "pay_system",
									"point"};
	/* Заголовки таблицы - Комиссии */
	private String[] tableRateFieldsTitle = new String[] {"Тип операции","Тип расчета", 
							"Платежная система", "Объект взаимодействия"};
	
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
	
	/* Поля таблицы - Пользователи */
	private Object[] tableLogsFields = new Object[] {"data", 
			"login", "program"};
	/* Заголовки таблицы - Пользователи */
	private String[] tableLogsFieldsTitle = new String[] { 
			"Дата", "Логин", "Тип ПО"};
	
	/* Поля таблицы - Коммисии */
	private Object[] tableKommisFields = new Object[] {"paySystem", 
			"komSystem", "kommis", "type_pay", "minKommis", "maxKommis"};
	/* Заголовки таблицы - Коммисии */
	private String[] tableKommisFieldsTitle = new String[] { 
			"Платежная система", "Комиссия системы", "Наша комиссия",
			"Тип кошелька",	"Мин. комиссия", "Макс. комиссия"};
	
	/* Поля таблицы - Курсы валюты */
	private Object[] tableValutaFields = new Object[] {"data", 
			"numCode", "charCode", "nominal", "naz", "val"};
	/* Заголовки таблицы - Курсы валюты */
	private String[] tableValutaFieldsTitle = new String[] { 
			"Дата", "Код валюты", "Обозначение",
			"Номинал",	"Название", "Курс"};
	
	private BeanItem<Client> tmpClient = null;
	private BeanItem<Bik> tmpBik = null;
	private BeanItem<SMS> tmpSms = null;
	private BeanItem<Users> tmpUsers = null;
	private BeanItem<Kommis> tmpKommis = null;
	private BeanItem<Rate> tmpRate = null;
	private BeanItem<PaySystem> tmpPaySystem = null;
	
	private final Action ACTION_STATUS = new Action("Проверить статус");
	private final Action ACTION_ADD_BANK = new Action("Добавить банк");
	private final Action ACTION_ADD_CLIENT = new Action("Добавить клиента");
	private final Action ACTION_ADD_PAY_SYS = new Action("Добавить пл. систему");
	private final Action ACTION_DEL_PAY_SYS = new Action("Удалить пл. систему");
	private final Action ACTION_ADD_RATE = new Action("Добавить тарифы");
	
	private final Action[] ACTIONS_MARKED = new Action[] { ACTION_STATUS};
	private final Action[] ACTIONS_BANK = new Action[] { ACTION_ADD_BANK };
	private final Action[] ACTIONS_CLIENT = new Action[] { ACTION_ADD_CLIENT };
	private final Action[] ACTIONS_PAY = new Action[] { ACTION_ADD_PAY_SYS, 
														ACTION_DEL_PAY_SYS };
	private final Action[] ACTIONS_RATE = new Action[] { ACTION_ADD_RATE };
	
	@Override
	protected void init(VaadinRequest request) {
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		// Установка pool Interval
		//setPollInterval(3000);
        addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
            	// Автоматическое обновление таблиц через 18 секунд
            	timeRefresh ++;
            	if (timeRefresh > 6 || (stateUpdate == true)) {
            		
            		timeRefresh = 0;
            		stateUpdate = false;
            		switchTable(nameTable);
            	} 
            }
        });
		
		setContent(layout);		
		
		buildAuthForm();			
	}
	
	/**
	 * Создание основного слоя состоящего из двух
	 * Первый - слой для размещения панелей фильтрации и поиска
	 * Второй - слой для размещения таблиц с данными
	 */
	private void buildMainLayout() {
		
		toolLayout = new VerticalLayout();
		toolLayout.addStyleName("tool-layout");
		layout.addComponent(toolLayout);
		
		tableLayout = new VerticalLayout();
		tableLayout.addStyleName("table-layout");
		layout.addComponent(tableLayout);
	}
	
	/**
	 * Создание основного меню
	 */
	private void buildMainMenu() {
		
		mainMenu = new MenuBar();
		
		MenuBar.Command myCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				state = selectedItem.getText();	
				clearTmp();
				
				if (state.equals("Принятые")) {
					
					switchTable("registryTable");					
				}
				
				if (state.equals("Заявки")) {
					
				}
				
				if (state.equals("Файлы")) {
					
				}
				
				if (state.equals("Вход")) {
					
					switchTable("logsTable");				
				}
				
				if (state.equals("Статусы SMS")) {
					
					switchTable("smsTable");				
				}
				
				if (state.equals("Банки БИК")) {
					
					switchTable("bikTable");					
				}				
				
				if (state.equals("Клиенты физ. лица")) {
					
					switchTable("clientTable");				
				}
				
				if (state.equals("Клиенты юр. лица")) {
					
					switchTable("clientUrTable");
				}
				
				if (state.equals("Пользователи")) {
					
					switchTable("userTable");				
				}
				
				if (state.equals("Пл. системы")) {
					
					//switchTable("kommisTable");
					switchTable("payTable");				
				}
				
				if (state.equals("Курсы")) {
					switchTable("valutaTable");
				}
				
				if (state.equals("Тарифы")) {
					switchTable("rateTable");
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
				
				// Удаление выбранного пользователя
				if (state.equals("Удалить")) {
					
					if ((current == usersTable) && (tmpUsers != null)) {
						// Удаление пользоватлея
					}
				}
				
			}
		};		
		
		zayvki = mainMenu.addItem("Заявки" , null, null);
		files = mainMenu.addItem("Файлы", null, null);
		money = mainMenu.addItem("Электронные деньги", null, null); 
		logs = mainMenu.addItem("Логи", null, null);
		spsr = mainMenu.addItem("Справочники", null, null);
		
		// 
		zayvki.addItem("Принятые", myCommand);
		zayvki.addItem("Измененные", myCommand);
		zayvki.addItem("Выполенные", myCommand);
		
		// 
		files.addItem("Безналичный", myCommand);
		files.addItem("Наличный", myCommand);
		
		// Формирование меню Электронные деньги
		money.addItem("Яндекс-деньги", myCommand);
		money.addItem("Webmoney", myCommand);
		
		// 
		logs.addItem("Вход", myCommand);
		logs.addItem("Статусы SMS", myCommand);
		
		// 
		user = spsr.addItem("Пользователи", myCommand);
		spsr.addItem("Клиенты физ. лица", myCommand);
		spsr.addItem("Клиенты юр. лица", myCommand);
		spsr.addItem("Курсы", myCommand);
		spsr.addItem("Банки БИК", myCommand);
		kommis = spsr.addItem("Пл. системы", myCommand);
		spsr.addItem("Тарифы", myCommand);
		
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
		//toolLayout.addComponent(mainMenu);
		
	}
	
	/* Формирование таблицы регистрации заявок */
	private void buildRegistryTable() {
	
		/*if (zayvkiLayout == null) {
			zayvkiLayout = new VerticalLayout();
		}*/
		double sum_itog = 0.0;
		beansZayvkaReg.removeAllItems();
		
		buildFilterPanel();
		
		RegZayvkiDB regDB = null;
		try {
			regDB = new RegZayvkiDB(DB.getConnection());
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}		
		
		try {
			List<RegZayvki> list = regDB.getAllZayvki();
			for (int i = 0; i < list.size(); i++) {				
				beansZayvkaReg.addBean(list.get(i));
				sum_itog = sum_itog + list.get(i).getSumma();
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		registryTable = new Table("", beansZayvkaReg);
		registryTable.setSelectable(true);
		registryTable.setWidth("100%");
		registryTable.setHeight("500px");
		registryTable.setFooterVisible(true);
	
		registryTable.setVisibleColumns(tableRegFields);
	
		for (int i = 0; i < tableRegFields.length; i++) {
			registryTable.setColumnHeader(tableRegFields[i], tableRegFieldsTitle[i]);
		}
		
		registryTable.setColumnFooter("id_zayvki", "Итого");
		registryTable.setColumnFooter("summa", String.valueOf(sum_itog));
		
		newTable = registryTable;
		nameTable = "registryTable";
	}
	
	/* Формирование таблицы БИКОВ */
	private Table buildBikTable() {	
		
		beansBik.removeAllItems();	
		
		/* Если панель поиска по БИКу не была создана
		   то создаем ее */
		if (searchBikPanel == null) {
			builSearchBikPanel();
		} else {
			toolLayout.addComponent(searchBikPanel);
		}
		
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
		
		bikTable.addActionHandler(new Action.Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				
				if (ACTION_ADD_BANK == action) {
					if (current == bikTable) {
						
						Window win = new ModalWindowBik(null);					
						UI.getCurrent().addWindow(win);
					}
				}
				
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				// TODO Auto-generated method stub
				return ACTIONS_BANK;
			}
		});
		
		newTable = bikTable;
		nameTable = "bikTable";
		return bikTable;	
	}
	
	
	/**
	 * Формирование таблицы Патежные системы
	 */
	private void buildPayTable() {
		
		beansPay.removeAllItems();
		
		try {			
			List<PaySystem> list = BaseRW.getPaySystem(DB.getConnection());		
			
			
			for (int i = 0; i < list.size(); i++) {				
				beansPay.addBean(list.get(i));				
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		}				
		
		payTable = new Table("",beansPay);
		payTable.setWidth("100%");
		payTable.setHeight("100%");
		payTable.setSelectable(true);
		payTable.setMultiSelect(true);
		
		payTable.setVisibleColumns(payFields);		
		for (int i = 0; i < payFields.length; i++) {
			payTable.setColumnHeader(payFields[i], payFieldsTitle[i]);
    	}
		
		payTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {				
				
				tmpPaySystem = beansPay.getItem(event.getItemId());
				
				if (event.isDoubleClick()) {
					// Форма редактирования пл. системы
					tmpPaySystem = beansPay.getItem(event.getItemId());
					//PaySystem pay1 = tmpPaySystem.getBean();
					//Window win = new ModalWindowPaySystem(tmpPaySystem.getBean());
					Window win = new ModalWindowPaySystemNew(tmpPaySystem.getBean());
					UI.getCurrent().addWindow(win);
					
				}
			}
		});
		
		payTable.addActionHandler(new Action.Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				
				if (ACTION_ADD_PAY_SYS == action) {
					if (current == payTable) {						
						//Window win = new ModalWindowPaySystem(null);
						Window win = new ModalWindowPaySystemNew(null);
						UI.getCurrent().addWindow(win);
					}
				}
				
				if (ACTION_DEL_PAY_SYS == action) {
					if (tmpPaySystem != null) {
						// Вызываем функцию удаление пл. системы
						try {
							BaseRW.delPaySystem(DB.getConnection(), tmpPaySystem.getBean());
							stateUpdate = true;
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				
				return ACTIONS_PAY;
			}
		});
		
		newTable = payTable;
		nameTable = "payTable";
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
		
		newTable = clientTable;	
		nameTable = "clientTable";
	}
	
	/* Формирование таблицы клиентов - юридических лиц */
	private void buildClientUrTable() {	
		
		beansClientUr.removeAllItems();
		
		buildFilterClientPanel();
		
		try {
			List<ClientUr> list = BaseRW.getAllClientUr(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansClientUr.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		clientUrTable = new Table("", beansClientUr);
		clientUrTable.setSelectable(true);
		clientUrTable.setWidth("100%");
		clientUrTable.setHeight("550px");
	
		clientUrTable.setVisibleColumns(tableClientUrFields);
	
		for (int i = 0; i < tableClientUrFields.length; i++) {
			clientUrTable.setColumnHeader(tableClientUrFields[i], tableClientUrFieldsTitle[i]);
		}
		
		clientUrTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				/*if (event.isDoubleClick()) {
					
					tmpClient = beansClient.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new WindowHistoryClient(tmpClient.getBean().getMail());					
					UI.getCurrent().addWindow(win);
				} 	*/		
			}
		});
		
		clientUrTable.addActionHandler(new Action.Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				
				if (ACTION_ADD_CLIENT == action) {
					if (current == clientUrTable) {
						
						Window win = new ModalWindowClientUr(null);					
						UI.getCurrent().addWindow(win);
					}
				}				
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				
				return ACTIONS_CLIENT;
			}
		});
		
		newTable = clientUrTable;
		nameTable = "clientUrTable";
	}
	
	/* Формирование таблицы клиентов */
	private Table buildSMSTable() {
		
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
		
		newTable = smsTable;
		nameTable = "smsTable";
		return smsTable;
	}
	
	/**
	 * Создание таблицы пользователей системы
	 */
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
		
		newTable = usersTable;
		nameTable = "usersTable";
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
					Window win = new ModalWindowKommis(tmpKommis);					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
		
		newTable = kommisTable;
		nameTable = "kommisTable";
	}
	
	/**
	 * Формирование таблицы логов
	 */
	private void buildLogsTable() {
		
		beansLogs.removeAllItems();
		
		try {
			List<Logs> list = BaseRW.getAllLogs(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansLogs.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		logsTable = new Table("", beansLogs);
		logsTable.setSelectable(true);
		logsTable.setWidth("100%");
		logsTable.setHeight("550px");
	
		logsTable.setVisibleColumns(tableLogsFields);
	
		for (int i = 0; i < tableLogsFields.length; i++) {
			logsTable.setColumnHeader(tableLogsFields[i], tableLogsFieldsTitle[i]);
		}
		
		newTable = logsTable;
		nameTable = "logsTable";
	}
	
	/**
	 * Формирование таблицы курсов валют
	 */
	private void buildValutaTable() {
		
		beansValuta.removeAllItems();
		
		if (searchValutaPanel == null) {
			buildFilterValutaPanel();
		} else {
			toolLayout.addComponent(searchValutaPanel);
		}
		
		try {
			List<Valuta> list = BaseRW.getAllValuta(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansValuta.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		valutaTable = new Table("", beansValuta);
		valutaTable.setSelectable(true);
		valutaTable.setWidth("100%");
		valutaTable.setHeight("550px");
	
		valutaTable.setVisibleColumns(tableValutaFields);
	
		for (int i = 0; i < tableValutaFields.length; i++) {
			valutaTable.setColumnHeader(tableValutaFields[i], tableValutaFieldsTitle[i]);
		}
		
		newTable = valutaTable;
		nameTable = "valutaTable";		
	}
	
	/**
	 * Формирование таблицы тарифов
	 */
	private void buildRateTable() {
		
		beansRate.removeAllItems();
		
		buildFilterRatePanel();
		
		try {
			List<Rate> list = BaseRW.getAllRate(DB.getConnection());
			for (int i = 0; i < list.size(); i++) {				
				beansRate.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		rateTable = new Table("", beansRate);
		rateTable.setSelectable(true);
		rateTable.setWidth("100%");
		rateTable.setHeight("550px");
	
		rateTable.setVisibleColumns(tableRateFields);
	
		for (int i = 0; i < tableRateFields.length; i++) {
			rateTable.setColumnHeader(tableRateFields[i], tableRateFieldsTitle[i]);
		}
		rateTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {				
				
				if (event.isDoubleClick()) {
					tmpRate = beansRate.getItem(event.getItemId());
					Window win = new ModalWindowRate(tmpRate);					
					UI.getCurrent().addWindow(win);
				}	
			}
		});
		
		// Всплывающее меню Добавить
		rateTable.addActionHandler(new Action.Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (ACTION_ADD_RATE == action) {
					if (current == rateTable) {						
						Window win = new ModalWindowRate(null);					
						UI.getCurrent().addWindow(win);
					}
				}
				
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS_RATE;
			}
		});
		
		newTable = rateTable;
		nameTable = "rateTable";
	}
	
	// Очищаем все временные переменные
	private void clearTmp() {
		
		tmpBik = null;
		tmpClient = null;
		tmpKommis = null;
		tmpSms = null;
		tmpUsers = null;
	}
	
	/**
	 * Панель фильтрации для зарегистрированных
	 * заявок
	 */
	private void buildFilterPanel() {
		
		/* Создание сетки */
		setLayout = new GridLayout(7, 5);
		setLayout.setWidth("100%");		
		
		/* Дата создания заявки - С */
		Label dataLabel1 = new Label("Дата с ");	
		dataLabel1.setWidth(null);
		setLayout.addComponent(dataLabel1, 0, 0);	
		data1 = new PopupDateField();		
		data1.setDateFormat("dd.MM.yyyy");
		data1.setValue(new Date());
		data1.setImmediate(true);
		data1.setWidth("180px");			
		setLayout.addComponent(data1, 1, 0);
		
		/* Дата создания заявки - ПО */
		Label dataLabel2 = new Label("Дата по ");	
		dataLabel2.setWidth(null);
		setLayout.addComponent(dataLabel2, 2, 0);	
		data2 = new PopupDateField();		
		data2.setDateFormat("dd.MM.yyyy");
		data2.setValue(new Date());
		data2.setImmediate(true);
		data2.setWidth("180px");			
		setLayout.addComponent(data2, 3, 0);
		
		/* IP адрес клиента */
		Label ipLabel = new Label("IP адрес");
		ipLabel.setWidth(null);
		setLayout.addComponent(ipLabel, 0, 1);
		ipArress = new TextField();
		ipArress.setWidth("180px");
		ipArress.setImmediate(true);		
		setLayout.addComponent(ipArress, 1, 1);		
		
		/* ФИО клиента */
		Label fioLabel = new Label("ФИО клиента");
		fioLabel.setWidth(null);
		setLayout.addComponent(fioLabel, 2, 1);
		fioClient = new TextField();
		fioClient.setWidth("180px");
		fioClient.setImmediate(true);		
		setLayout.addComponent(fioClient, 3, 1);	
		
		/* Номер заявки */
		Label numLabel = new Label("Номер заявки");
		numLabel.setWidth(null);
		setLayout.addComponent(numLabel, 4, 0);
		numZayvki = new TextField();
		numZayvki.setWidth("180px");
		numZayvki.setImmediate(true);		
		setLayout.addComponent(numZayvki, 5, 0);	
		
		/* Номер кошелька */
		Label purseLabel = new Label("Номер кошелька");
		purseLabel.setWidth(null);
		setLayout.addComponent(purseLabel, 4, 1);
		numPurse = new TextField();
		numPurse.setWidth("180px");
		numPurse.setImmediate(true);		
		setLayout.addComponent(numPurse, 5, 1);
		
		/* Создать кнопку ФИЛЬТР */
		btnFilter = new Button("Фильтр");
		btnFilter.setWidth("100px");
		btnFilter.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				beansZayvkaReg.removeAllContainerFilters();			
				// Добавление фильтров
				// Фильтрация по IP адресу
				String strIpAdress = (String) (ipArress.getValue() == null ? "" : ipArress.getValue());
				
				if (!strIpAdress.equals("")) {
					Filter filterIpAdress = new SimpleStringFilter("ip_adress", strIpAdress,
							true, false);
					beansZayvkaReg.addContainerFilter(filterIpAdress);
				}
				// Фильтрация по ФИО клиента
				String strFio = (String) (fioClient.getValue() == null ? "" : fioClient.getValue());
				
				if (!strFio.equals("")) {
					Filter filterFioClient = new SimpleStringFilter("ip_adress", strFio,
							true, false);
					beansZayvkaReg.addContainerFilter(filterFioClient);
				}
				// Фильтрация по номеру заявки
				String strNumZayvki = (String) (numZayvki.getValue() == null ? "" : numZayvki.getValue());
				
				if (!strNumZayvki.equals("")) {
					Filter filterNumZayvki = new SimpleStringFilter("id_zayvki", strNumZayvki,
							true, false);
					beansZayvkaReg.addContainerFilter(filterNumZayvki);
				}				
			}
		});
		setLayout.addComponent(btnFilter, 0, 2);
		
		/* Создать кнопку Сброс */
		btnCancel = new Button("Сброс");
		btnCancel.setWidth("100px");
		
		btnCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Сброс все фильтров
				beansZayvkaReg.removeAllContainerFilters();
				// очистка полей формы фильрации заявок
				data1.setData(new Date());
				data2.setData(new Date());
				numPurse.setValue("");
				numZayvki.setValue("");
				fioClient.setValue("");
				ipArress.setValue("");
			}
		});
		setLayout.addComponent(btnCancel, 1, 2);
		
		//zayvkiLayout.addComponent(setLayout);
		toolLayout.addComponent(setLayout);
	}	
	
	/**
	 * Функция создания панели поиска по БИКу
	 */
	private void builSearchBikPanel() {
		
		/* Создание сетки */
		searchBikPanel = new GridLayout(4, 2);
		searchBikPanel.setWidth("50%");
		
		/* Номер БИКа */
		Label bikLabel = new Label("БИК номер");
		bikLabel.setWidth(null);
		searchBikPanel.addComponent(bikLabel, 0, 0);
		bikNumber = new TextField();
		bikNumber.setWidth("180px");
		bikNumber.setImmediate(true);		
		searchBikPanel.addComponent(bikNumber, 1, 0);	
		
		/* Создать кнопку ФИЛЬТР */
		btnSerachBik = new Button("Поиск");
		btnSerachBik.setWidth("100px");
		btnSerachBik.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				beansBik.removeAllContainerFilters();
				
				// Поиск через фильтрацию по БИКу
				String bik_num = (String) (bikNumber.getValue() == null ? "" : operation.getValue());
					
				if (!bik_num.equals("")) {
					Filter filterBik = new SimpleStringFilter("bik", bik_num,
							true, false);
					beansBik.addContainerFilter(filterBik);
				}
				
			}
		});
		searchBikPanel.addComponent(btnSerachBik, 2, 0);
		
		/* Создать кнопку Сброс */
		btnCancelBik = new Button("Сброс");
		btnCancelBik.setWidth("100px");
		btnCancelBik.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Сброс всех фильтров для таблицы БИКов
				beansBik.removeAllContainerFilters();				
			}
		});
		searchBikPanel.addComponent(btnCancelBik, 3, 0);
		
		toolLayout.addComponent(searchBikPanel);
	}
	
	/**
	 * Формирование панели фильтрации тарифов
	 */
	private void buildFilterRatePanel() {
		
		/* Создание сетки */
		filterRatePanel = new GridLayout(4, 2);
		filterRatePanel.setWidth("50%");
		
		/* Тип операции */
		Label operationLabel = new Label("Тип операции");
		operationLabel.setWidth(null);
		filterRatePanel.addComponent(operationLabel, 0, 0);
		operation = new ComboBox();
		operation.setWidth("180px");
		operation.setImmediate(true);
		operation.addItem("ВВОД");
		operation.addItem("ВЫВОД");
		operation.setValue("ВЫВОД");
		filterRatePanel.addComponent(operation, 1, 0);
		
		/* Тип расчета */
		Label raschetLabel = new Label("Тип расчета");
		raschetLabel.setWidth(null);
		filterRatePanel.addComponent(raschetLabel, 2, 0);
		typeRaschet = new ComboBox();
		typeRaschet.setWidth("180px");
		typeRaschet.setImmediate(true);
		typeRaschet.addItem("НАЛИЧНЫЙ");
		typeRaschet.addItem("БЕЗНАЛИЧНЫЙ");
		typeRaschet.setValue("БЕЗНАЛИЧНЫЙ");
		filterRatePanel.addComponent(typeRaschet, 3, 0);
		
		/* Создать кнопку ФИЛЬТР */
		btnFilterRate = new Button("ФИЛЬТР");
		btnFilterRate.setWidth("100px");
		// Нажатие на кнопку ФИЛЬТР
		btnFilterRate.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Сброс установленных фильтров
				beansRate.removeAllContainerFilters();
				
				String strOperation = (String) (operation.getValue() == null ? "" : operation.getValue());
				String strRaschet = (String) (typeRaschet.getValue() == null ? "" : typeRaschet.getValue());
				
				// Добавляем фильтр по типу операции
				if (!strOperation.equals("")) {
					Filter filterOper = new SimpleStringFilter("operation", strOperation,
							true, false);
					beansRate.addContainerFilter(filterOper);
				}
				// Добавляем фильтр по типу расчета
				if (!strRaschet.equals("")) {
					Filter filterRaschet = new SimpleStringFilter("type_paschet", strRaschet,
							true, false);
					beansRate.addContainerFilter(filterRaschet);
				}	
			}
		});
		filterRatePanel.addComponent(btnFilterRate, 0, 1);
		
		/* Создать кнопку Сброс */
		btnCancelRate = new Button("Сброс");
		btnCancelRate.setWidth("100px");
		// Сброс всех фильтров
		btnCancelRate.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				beansRate.removeAllContainerFilters();				
			}
		});
		filterRatePanel.addComponent(btnCancelRate, 1, 1);
		
		toolLayout.addComponent(filterRatePanel);
	}
	
	/**
	 * Панель фильтрации для курсов валют
	 */
	private void buildFilterValutaPanel() {
		
		/* Создание сетки */
		searchValutaPanel = new GridLayout(4, 2);
		searchValutaPanel.setWidth("50%");
		
		/* Дата создания заявки - С */
		Label dataLabel = new Label("Дата");	
		dataLabel.setWidth(null);
		searchValutaPanel.addComponent(dataLabel, 0, 0);	
		dataKurs = new PopupDateField();		
		dataKurs.setDateFormat("dd.MM.yyyy");
		dataKurs.setValue(new Date());
		dataKurs.setImmediate(true);
		dataKurs.setWidth("180px");			
		searchValutaPanel.addComponent(dataKurs, 1, 0);
		
		/* Создать кнопку ФИЛЬТР */
		btnFilterValuta = new Button("Фильтр");
		btnFilterValuta.setWidth("100px");
		searchValutaPanel.addComponent(btnFilterValuta, 2, 0);
		
		/* Создать кнопку Сброс */
		btnCancelValuta = new Button("Сброс");
		btnCancelValuta.setWidth("100px");
		searchValutaPanel.addComponent(btnCancelValuta, 3, 0);
		
		toolLayout.addComponent(searchValutaPanel);
	}
	
	/**
	 * Панель фильтрации для юр. лиц
	 */
	private void buildFilterClientPanel() {
		
		/* Создание сетки */
		filterClietnUrPanel = new GridLayout(4, 4);
		filterClietnUrPanel.setWidth("70%");
		
		/* Номер ИНН клиента */
		Label innLabel = new Label("ИНН клиента");
		innLabel.setWidth(null);
		filterClietnUrPanel.addComponent(innLabel, 0, 0);
		innClient = new TextField();
		innClient.setWidth("180px");
		innClient.setImmediate(true);		
		filterClietnUrPanel.addComponent(innClient, 1, 0);
		
		/* Номер ИНН клиента */
		Label kppLabel = new Label("КПП клиента");
		kppLabel.setWidth(null);
		filterClietnUrPanel.addComponent(kppLabel, 2, 0);
		kppClient = new TextField();
		kppClient.setWidth("180px");
		kppClient.setImmediate(true);		
		filterClietnUrPanel.addComponent(kppClient, 3, 0);
		
		/* Создать кнопку ФИЛЬТР */
		btnFilterClient = new Button("Фильтр");
		btnFilterClient.setWidth("100px");
		filterClietnUrPanel.addComponent(btnFilterClient, 0, 1);
		
		/* Создать кнопку Сброс */
		btnCancelClient = new Button("Сброс");
		btnCancelClient.setWidth("100px");
		filterClietnUrPanel.addComponent(btnCancelClient, 1, 1);
		
		toolLayout.addComponent(filterClietnUrPanel);
	}
	
	private void buildAuthForm() {
		
		/* Создание сетки для формы авторизации */
		gl = null; 
		Label userLb = null; //Пользователь
		//Поле для отображения логина пользователя		
		Label passLb = null; //Введите пароль
		Label depLb = null;
		
		Button auBt = null; // кнопка авторизации
		Button exBt = null; // кнопка выхода
		
		/* Создание полей для ввод алогина и пароля */
		userLb = new Label("Пользователь", Label.CONTENT_XHTML);		
		userName = new TextField();
		
		passLb = new Label("Пароль", Label.CONTENT_XHTML);

		/* Поле для ввода пароля */
		passPf = new PasswordField();		
		passPf.setImmediate(true);
		passPf.setRequired(true);
		passPf.setRequiredError("Введите пароль.");
		
		/* Создание кнопки Вход */
		auBt = new Button("Вход");		
		auBt.setImmediate(true);		

		/* Создание кнопки Отмена */
		exBt = new Button("Отмена");
		exBt.setImmediate(true);		
		
		/* Создание сетки */
		gl = new GridLayout(3, 4);
		gl.setSpacing(true);
		
		/* Добавление поля для отображения логина пол-ля */ 
		gl.addComponent(userLb, 0, 0);
		gl.addComponent(userName, 1, 0, 2, 0);
		
		/* Добавление поля для ввода пароля */ 
		gl.addComponent(passLb, 0, 1);
		gl.addComponent(passPf, 1, 1, 2, 1);
		
		/* Выравниваем элементы по левому краю */
		gl.setComponentAlignment(userLb, Alignment.MIDDLE_LEFT);
		gl.setComponentAlignment(userName, Alignment.MIDDLE_LEFT);		
		gl.setComponentAlignment(passLb, Alignment.MIDDLE_LEFT);
		gl.setComponentAlignment(passPf, Alignment.MIDDLE_LEFT);
		
		/* Добавляем кнопки */
		gl.addComponent(auBt, 1, 3);
		gl.addComponent(exBt, 2, 3);
		
		/* Обработчик нажатия кнопки ВХОД */
		auBt.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {		
							
				try {
					goAuth();
				} catch (Exception e) {					
					e.printStackTrace();
				}
			}
		});		
				
		layout.addComponent(gl);
	}	
	
	/**
	 * Функция авторизации
	 * @throws Exception
	 */
	private void goAuth() throws Exception {
		
		String login = userName.getValue().toString();
		String pass = passPf.getValue().toString();
		
		// Проверяем что поля логин и пароль заполнены
		if(login.equals("") || pass.equals("")) {
		//if (false) {
			MessageBox.showPlain(Icon.ERROR, "Авторизация", 
					"Неверно введ логин пользователя или пароль 1", ButtonId.OK);
		} else {
		
			if (Auth.authUser(login, pass)) {
			//if (true) {
				layout.removeComponent(gl);
				buildMainMenu();
				buildMainLayout();		
				buildRegistryTable();
				tableLayout.addComponent(registryTable);
				current = registryTable;
				setPollInterval(2000);
			} else {
				MessageBox.showPlain(Icon.ERROR, "Авторизация", 
					"Неверно введ логин пользователя или пароль 2", ButtonId.OK);
				userName.setValue("");
				passPf.setValue("");
			}
		}	
	}
	
	/**
	 * Функция для переключения таблиц
	 * 
	 * @param table - имя таблицы которая должна отобразиться
	 */
	private void switchTable(String table) {
		
		toolLayout.removeAllComponents();
		tableLayout.removeAllComponents();
		/* Таблица статусов смс */
		if (table.equals("smsTable")) {
			buildSMSTable();
		}
		/* Таблица БИКов */
		if (table.equals("bikTable")) {
			buildBikTable();
		}
		/* Таблица клиенты */
		if (table.equals("clientTable")) {
			buildClientTable();
		}
		/* Таблица пользователи */
		if (table.equals("userTable")) {
			buildUserTable();
		}
		/* Таблица коммисии */
		if (table.equals("kommisTable")) {
			buildKommisTable();
		}
		/* Таблица юридические лица */
		if (table.equals("clientUrTable")) {
			buildClientUrTable();
		}
		/* Таблица курсы валют */
		if (table.equals("valutaTable")) {
			buildValutaTable();
		}
		/* Таблица регистрации заявок */
		if (table.equals("registryTable")) {
			buildRegistryTable();
		}
		/* Таблица платежных систем */
		if (table.equals("payTable")) {
			buildPayTable();
		}
		/* Таблица логов входа в приложение */
		if (table.equals("logsTable")) {
			buildLogsTable();
		}
		/* Таблица тарифов */
		if (table.equals("rateTable")) {
			buildRateTable();
		}
		tableLayout.removeComponent(current);
		tableLayout.addComponent(newTable);
		current = newTable;
	}
}