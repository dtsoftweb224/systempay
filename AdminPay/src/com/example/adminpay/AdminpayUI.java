package com.example.adminpay;

import java.util.List;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.Bik;
import com.example.adminpay.classes.Client;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.RegZayvki;
import com.example.adminpay.classes.RegZayvkiDB;
import com.example.adminpay.window.ModalWindowClient;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class AdminpayUI extends UI {
	
	private VerticalLayout layout;
	private MenuBar mainMenu = null;
	private MenuItem zayvki = null;
	private MenuItem files = null;
	private MenuItem logs = null;
	private MenuItem spsr = null;
	
	private Table registryTable;
	private Table bikTable;
	private Table clientTable;
	
	private Table current;
	
	private BeanItemContainer<RegZayvki> beansZayvkaReg = new BeanItemContainer<RegZayvki>(RegZayvki.class);
	private BeanItemContainer<Bik> beansBik = new BeanItemContainer<Bik>(Bik.class);
	private BeanItemContainer<Client> beansClient = new BeanItemContainer<Client>(Client.class);
	
	private String state;
	
	/* Поля таблицы - Регистрация заявок */
	private String[] tableRegFields = new String[] {"id", "id_zayvki", "data", 
			"summa", "payIn", "payOut", "fioClient", "mailClient"};
	/* Заголовки таблицы - Регистрация заявок */
	private String[] tableRegFieldsTitle = new String[] {"Идентификатор","Номер заявки", 
			"Дата", "Сумма", "Источник платежа", "Адресат платежа", "ФИО клиента", 
			"mail клиента" };
	
	/* Поля таблицы - Клиенты */
	private String[] tableClientFields = new String[] {"id", "telephone", 
			"serial", "number", "fio", "mail"};
	/* Заголовки таблицы - Клиенты */
	private String[] tableClientFieldsTitle = new String[] {"id", "Телефон", 
			"Серия паспорта", "Номер паспорта",	"ФИО клиента", "Электронная почта"};
	
	/* Поля таблицы - Банки БИК */
	private String[] tableBikFields = new String[] {"bik", "swift", 
			"bank", "form_nal", "form_beznal"};
	/* Заголовки таблицы - Банки БИК */
	private String[] tableBikFieldsTitle = new String[] {"БИК", "СВИФТ", 
			"Наименование банка", "Отображение наличные", "Отображение безналичные"};
	
	private BeanItem<Client> tmpClient = null;
	
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
		
		// 
		spsr.addItem("Пользователи", myCommand);
		spsr.addItem("Клиенты", myCommand);
		spsr.addItem("Курсы", myCommand);
		spsr.addItem("Банки БИК", myCommand);
		
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
		registryTable.setHeight("100%");
	
		registryTable.setVisibleColumns(tableRegFields);
	
		for (int i = 0; i < tableRegFields.length; i++) {
			registryTable.setColumnHeader(tableRegFields[i], tableRegFieldsTitle[i]);
		}
	
		layout.addComponent(registryTable);
		current = registryTable;
	}
	
	/* Формирование таблицы БИКОВ */
	private void buildBikTable() {	
				
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
		bikTable.setHeight("100%");
	
		bikTable.setVisibleColumns(tableBikFields);
	
		for (int i = 0; i < tableBikFields.length; i++) {
			bikTable.setColumnHeader(tableBikFields[i], tableBikFieldsTitle[i]);
		}	
	
	}
	
	/* Формирование таблицы клиентов */
	private void buildClientTable() {	
				
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
		clientTable.setHeight("100%");
	
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
					Window win = new ModalWindowClient(tmpClient);					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
	
	}

}