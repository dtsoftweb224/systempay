package com.example.managerpay;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.ZayvkaCard;
import com.example.managerpay.classes.Zayvki;
import com.example.managerpay.classes.ZayvkiCardDB;
import com.example.managerpay.classes.ZayvkiDB;
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
	
	BeanFieldGroup<Zayvki> binder = new BeanFieldGroup<Zayvki>(Zayvki.class);
	
	private String[] formFields = new String[] {"id", "wmid", "date", "payOut",
			"fioClient", "payIn", "valuta", "summaPay", "kommis", "summaCard", "status"};

	private String[] formFieldsTitle = new String[] {"Идентификатор","Кошелек", "Дата", "Банк",
			"ФИО клиента", "Платежная система", "Валюта", "Сумма списания", 
			"Комиссия", "Сумма зачисления", "Статус"};
	
	private String[] formFields1 = new String[] {"numberPay", "wmid", "date", "payOut",
			"payIn", "valuta", "summaPay", "kommis", "summaCard", "numSchet",
			"status","mail", "telephone", "fName","lName", "otch"};
	/* Названия полей для отображения*/
	private String[] formFieldsTitle1 = new String[] {"Номер заявки","Кошелек", "Дата", "Банк",
			"Платежная система", "Валюта", "Сумма списания","Комиссия", "Сумма зачисления", "Счет",
			"Статус", "Mail", "Телефон", "Имя", "Фамилия", "Отчество"};
	
	/**
	 * @param tmpZayvka - бин заявки
	 * @param arshive   - если true заявка архивная, иначе нормальная
	 */
	public WindowZayvkiCard(BeanItem<Zayvki> tmpZayvka, boolean arshive) {
		
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
			Zayvki bean = new Zayvki();	
			binder.setItemDataSource(bean);
		} else {
			binder.setItemDataSource(tmpZayvka);
		}		
		
		for (int i = 0; i < formFields1.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(formFieldsTitle1[i], formFields1[i]));
			binder.getField(formFields1[i]).setWidth("220px");
		}
		// Закрытие полей для редактирования
		binder.getField("wmid").setEnabled(false);
		binder.getField("numberPay").setEnabled(false);
		binder.getField("date").setEnabled(false);
		binder.getField("payIn").setEnabled(false);
		binder.getField("summaPay").setEnabled(false);
		//binder.getField("summaCard").setEnabled(false);
		//binder.getField("kommis").setEnabled(false);
		binder.getField("status").setEnabled(false);
		binder.getField("mail").setEnabled(false);
		binder.getField("telephone").setEnabled(false);
		
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
				BeanItem<Zayvki> item = binder.getItemDataSource();
				Zayvki zayvkaCard = item.getBean();
				try {
					/* Редактирование заявки в базе данных */
					ZayvkiDB zayvkaDB = new ZayvkiDB(DB.getConnection());
					zayvkaDB.UpdateZayvka(zayvkaCard);
					// Закрытие окна редактирования
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
