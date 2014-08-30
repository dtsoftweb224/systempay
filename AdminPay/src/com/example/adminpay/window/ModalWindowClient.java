package com.example.adminpay.window;

import com.example.adminpay.classes.Client;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ModalWindowClient extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FormLayout mainForm = null;
	
	private BeanFieldGroup<Client> binder = new BeanFieldGroup<Client>(Client.class);
	
	/* Поля формы - Клиенты */
	private String[] tableClientFields = new String[] {"id", "telephone", 
			"serial", "number", "fio", "mail"};
	/* Заголовки полей формы - Клиенты */
	private String[] tableClientFieldsTitle = new String[] {"id", "Телефон", 
			"Серия паспорта", "Номер паспорта",	"ФИО клиента", "Электронная почта"};
	
	public ModalWindowClient(BeanItem<Client> tmpClient) {
		
		// Создание окна Редактирования клиенты
		super("Окно редактирования клиента");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("60%");
		setHeight("50%");
		setModal(true);	
		
		// Создание формы
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		if (tmpClient == null) {
			
			Client bean = new Client();			
			binder.setItemDataSource(bean);
		} else {
			binder.setItemDataSource(tmpClient);
		}
		
		// Настройки формы
		for (int i = 0; i < tableClientFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableClientFieldsTitle[i], tableClientFields[i]));		
			binder.getField(tableClientFields[i]).setWidth("250px");		
			//binder.getField(tableClientFields[i]).setRequired(true);
			//binder.getField(tableClientFields[i]).setRequiredError("The Field may not be empty.");
		}
		
		buildButtonForm();
		setContent(mainForm);
	}	
	
	@SuppressWarnings("serial")
	private void buildButtonForm() {
		
		Button ok = new Button("Сохранение");
		Button cancel = new Button("Отмена");
        
		ok.addClickListener(new ClickListener() {
      
			@Override
			public void buttonClick(ClickEvent event) {
			
				try {
					binder.commit();
				} catch (CommitException e1) {					
					e1.printStackTrace();
				}
				//BeanItem<ZayvkaCard> item = binder.getItemDataSource();
				//ZayvkaCard zayvkaCard = item.getBean();
				
				try {
					//ZayvkiCardDB zayvkaDB = new ZayvkiCardDB(DB.getConnection());
					//zayvkaDB.UpdateZayvkaDB(zayvkaCard);
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
		
        mainForm.addComponent(ok);
        mainForm.addComponent(cancel);
	}

}
