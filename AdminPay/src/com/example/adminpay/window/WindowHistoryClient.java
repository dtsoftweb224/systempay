package com.example.adminpay.window;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;










import org.apache.tools.ant.listener.MailLogger;

import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.ZayvkiDB;
import com.example.adminpay.classes.Zayvki;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class WindowHistoryClient extends Window {
	
	private VerticalLayout mainLayout = null;
	private Table tableHistory;
	private HorizontalLayout filterPanel = null;
	private DateField fromDate = null; // Дата от
	private DateField toDate = null;   // Дата до
	private BeanItemContainer<Zayvki> beansZayvka = new BeanItemContainer<Zayvki>(Zayvki.class);
	
	private Object[] tableFields = new String[] {"lName","fName", "otch", "mail", "numberPay", "date",
			"summaCard", "payIn", "payOut", "status"};
	/* Названия полей для отображения*/
	private String[] tableFieldsTitle = new String[] {"Фамилия" ,"Имя", "Отчество", "Почта", 
			"Номер платежа", "Дата", "Сумма платежа", "Источник средств", 
			"Система зачисления", "Статус"};
	private String mail;
	
	public WindowHistoryClient(String mailClient) {
		
		super("История операций клиента");
		
		mail = mailClient;
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("70%");
		setHeight("55%");
		setModal(true);	
		
		mainLayout = new VerticalLayout();
		setContent(mainLayout);
		
		buildFilterPanel();
		buildTableHistory();
			
	}
	
	/* Формирование таблицы историй операций */
	private void buildTableHistory() {
		
		ZayvkiDB zayvkiDB = null;
		try {
			zayvkiDB = new ZayvkiDB(DB.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			List<Zayvki> list = new ArrayList<Zayvki>(); 
			list = zayvkiDB.getAllZayvki(mail);
			
			for (int i = 0; i < list.size(); i++) {				
				beansZayvka.addBean(list.get(i));				
			}
		} catch (Exception e) {
		
			e.printStackTrace();		
		}				
	
		tableHistory = new Table("",beansZayvka);
		tableHistory.setWidth("100%");
		tableHistory.setHeight("100%");
		tableHistory.setSelectable(true);
		tableHistory.setMultiSelect(true);
		
		tableHistory.setVisibleColumns(tableFields);		
		for (int i = 0; i < tableFields.length; i++) {
			tableHistory.setColumnHeader(tableFields[i], tableFieldsTitle[i]);
    	}	
		
		mainLayout.addComponent(tableHistory);
	}
	
	private void buildFilterPanel() {
		
		filterPanel = new HorizontalLayout();
		fromDate = new DateField();
		toDate = new DateField();
		
		fromDate.setDateFormat("dd.mm.yyyy");
		toDate.setDateFormat("dd.mm.yyyy");
		toDate.setValue(new Date());
		
		filterPanel.addComponent(new Label("ОТ"));
		filterPanel.addComponent(fromDate);
		filterPanel.addComponent(new Label("ДО"));
		filterPanel.addComponent(toDate);
		
		mainLayout.addComponent(filterPanel);
	}

}
