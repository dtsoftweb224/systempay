package com.example.managerpay.window;

import java.util.ArrayList;
import java.util.List;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.ZayvkiClientHist;
import com.example.managerpay.classes.ZayvkiClientHistDB;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class WindowHistoryClient extends Window {
	
	private Table tableHistory;
	private BeanItemContainer<ZayvkiClientHist> beansZayvka = new BeanItemContainer<ZayvkiClientHist>(ZayvkiClientHist.class);
	
	private String[] tableFields = new String[] {"id", "fio", "mail", "id_platezha", "data",
			"summa", "payIn", "payOut"};
	/* Названия полей для отображения*/
	private String[] tableFieldsTitle = new String[] {"Идентификатор" ,"ФИО клиента","Почта", 
			"Номер платежа", "Дата", "Сумма платеда", "Источник средств", 
			"Система зачисления"};
	private String mail;
	
	public WindowHistoryClient(String mailClient) {
		
		super("История операций клиента");
		
		mail = mailClient;
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("70%");
		setHeight("55%");
		setModal(true);	
		
		buildTableHistory();
			
	}
	
	/* Формирование таблицы историй операций */
	private void buildTableHistory() {
		
		ZayvkiClientHistDB zayvkiCardList = new ZayvkiClientHistDB(DB.getConnection());
		
		try {
			List<ZayvkiClientHist> list = new ArrayList<ZayvkiClientHist>(); 
			list = zayvkiCardList.getAllZayvki(mail);
			
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
		
		setContent(tableHistory);
	}

}
