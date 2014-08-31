package com.example.managerpay;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.ZayvkaCard;
import com.example.managerpay.classes.ZayvkiCardDB;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;

public class WindowZayvkiCard extends Window
								implements Window.CloseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	
	BeanFieldGroup<ZayvkaCard> binder = new BeanFieldGroup<ZayvkaCard>(ZayvkaCard.class);
	
	private String[] formFields = new String[] {"id", "wmid", "date", "payOut",
			"fioClient", "payIn", "valuta", "summaPay", "kommis", "summaCard", "status"};

	private String[] formFieldsTitle = new String[] {"Идентификатор","Кошелек", "Дата", "Банк",
			"ФИО клиента", "Платежная система", "Валюта", "Сумма списания", 
			"Комиссия", "Сумма зачисления", "Статус"};
	
	public WindowZayvkiCard(BeanItem<ZayvkaCard> tmpZayvka) {
		
		//mainWindow = main;
		super("Редактирование заявки");	
		
		setPositionX(200);
		setPositionY(100);
		setHeight("600px");
		setWidth("450px");
		setModal(true);
		
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		if (tmpZayvka == null) {
			ZayvkaCard bean = new ZayvkaCard();			
			binder.setItemDataSource(bean);
		} else {
			binder.setItemDataSource(tmpZayvka);
		}		
		
		for (int i = 0; i < formFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(formFieldsTitle[i], formFields[i]));
			binder.getField(formFields[i]).setWidth("220px");
		}
		
		buildButtonForm();
		setContent(mainForm);
	}
	
	@SuppressWarnings("serial")
	private void buildButtonForm() {
		
		gridLayout = new GridLayout(2, 1);
		gridLayout.setWidth("100%");
		
		Button ok = new Button("Сохранение");
		Button cancel = new Button("Отмена");
		
		gridLayout.addComponent(cancel, 0, 0);
		gridLayout.addComponent(ok, 1, 0);
        
		ok.addClickListener(new ClickListener() {
      
			@Override
			public void buttonClick(ClickEvent event) {
			
				try {
					binder.commit();
				} catch (CommitException e1) {					
					e1.printStackTrace();
				}
				BeanItem<ZayvkaCard> item = binder.getItemDataSource();
				ZayvkaCard zayvkaCard = item.getBean();
				
				try {
					ZayvkiCardDB zayvkaDB = new ZayvkiCardDB(DB.getConnection());
					zayvkaDB.UpdateZayvkaDB(zayvkaCard);
					close();
				} catch (Exception e) {					
					e.printStackTrace();
				}
				
			}
        });
		
		cancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				close();
			}
		});
		
        //mainForm.addComponent(ok);
        mainForm.addComponent(gridLayout);
	}

	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
