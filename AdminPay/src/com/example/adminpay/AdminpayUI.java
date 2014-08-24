package com.example.adminpay;

import java.util.List;

import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.RegZayvki;
import com.example.adminpay.classes.RegZayvkiDB;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
	
	private BeanItemContainer<RegZayvki> beansZayvkaReg = new BeanItemContainer<RegZayvki>(RegZayvki.class);
	private String state;
	
	private String[] tableRegFields = new String[] {"id","id_zayvki", "data", 
			"summa", "payIn", "payOut", "fioClient", "mailClient"};
	
	private String[] tableRegFieldsTitle = new String[] {"Идентификатор","Номер заявки", 
			"Дата", "Сумма", "Источник платежа", "Адресат платежа", "ФИО клиента", 
			"mail клиента" };
	
	private String[] tableClientFields = new String[] {"id", "mail", "telephone", 
			"serial", "payIn", "payOut", "fioClient", "mailClient"};

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
				
				if (state.equals("Заявки")) {
					
				}
				
				if (state.equals("Файлы")) {
					
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
	registryTable.setWidth("100%");
	registryTable.setHeight("100%");
	
	registryTable.setVisibleColumns(tableRegFields);
	
	for (int i = 0; i < tableRegFields.length; i++) {
		registryTable.setColumnHeader(tableRegFields[i], tableRegFieldsTitle[i]);
	}
	
	layout.addComponent(registryTable);
}

}