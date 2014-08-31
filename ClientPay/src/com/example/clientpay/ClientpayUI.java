package com.example.clientpay;

import java.util.Date;

import com.example.clientpay.classes.DB;
import com.example.clientpay.classes.RegZayvki;
import com.example.clientpay.classes.RegZayvkiDB;
import com.example.clientpay.classes.ZayvkaCard;
import com.example.clientpay.classes.ZayvkiCardDB;
import com.example.clientpay.support.DbDop;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class ClientpayUI extends UI {
	
	private Form mainForm = null;
	private ZayvkaCard zayvkiCard = new ZayvkaCard();
	private BeanItem<ZayvkaCard> beans = null;
	
	private Button OK;
	private Button cancel;
	private ComboBox type;
	private TextField serial;  // Серия паспорта 
	private TextField number;  // Номер паспорта
	private TextField address; // Адрес регистрации
	private ComboBox paySystem; 
	private VerticalLayout identLayout;
	
	private String[] formFields = new String[] {"wmid", "date", "payOut",
			"fioClient", "valuta", "summaPay", "kommis", "summaCard", 
			"mail"};
	/* Названия полей для отображения*/
	private String[] formFieldsTitle = new String[] {"Кошелек", "Дата", "Банк",
			"ФИО клиента", "Валюта", "Сумма списания", 
			"Комиссия", "Сумма зачисления", "Эл. почта"};

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		buildMainForm();
		layout.addComponent(mainForm);
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	private void buildMainForm() {
	    	
	  	mainForm = new Form();
	    mainForm.setVisible(true);	
	    	
	    buildTypeBox();
	    paySystem = new ComboBox("Платежная система");
	    paySystem.addItem("WebMoney");
	    paySystem.addItem("Яндекс");
	    paySystem.setWidth("220px");
	    paySystem.setValue("WebMoney");
	    mainForm.getLayout().addComponent(paySystem);
	    	
	    beans = new BeanItem<ZayvkaCard>(zayvkiCard);
		mainForm.setItemDataSource(beans); 
		/* Поля видимые на форме */
		 mainForm.setVisibleItemProperties(formFields);
		 for (int i = 0; i < formFields.length; i++) {
				 
			 /* Названия полей */
			 mainForm.getField(formFields[i]).setCaption(formFieldsTitle[i]);
			 /* Установка ширины поля */
			 mainForm.getField(formFields[i]).setWidth("220px");
		 }
		 // Временное заполнение полей формы
		 mainForm.getField("mail").setValue("prizrak309@mail.ru");
		 mainForm.getField("payOut").setValue("Сбербанк");
		 mainForm.getField("fioClient").setValue("Иванов Иван Иванович");
		 mainForm.getField("valuta").setValue("USD");
		 //		 
		 buildIdentField();
		 buildButtonForm();
    }	
	 
  private void buildTypeBox() {
	    	
	   	type = new ComboBox("Тип оплаты");
	   	type.addItem("Безналичный");
	   	type.addItem("Наличный");
	   	type.setImmediate(true);
	   	type.setValue("Безналичный");
	   	type.setWidth("220px");	    
	   	
	   	type.addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (type.getValue().equals("Наличный")) {
					serial.setVisible(true);
					number.setVisible(true);
					address.setVisible(true);
				}
				
				if (type.getValue().equals("Безналичный")) {
					serial.setVisible(false);
					number.setVisible(false);
					address.setVisible(false);
				}
			}
		});
	  	    	
	   	mainForm.getLayout().addComponent(type);    	
  }
  @SuppressWarnings("deprecation")    	 
   private void buildIdentField() {
	    	
	   	identLayout = new VerticalLayout();
	   	identLayout.setImmediate(true);
	    	
	   	serial = new TextField("Серия паспорта");
	   	serial.setImmediate(true);
	   	serial.setWidth("220px");
	   	serial.setVisible(false);
	   	mainForm.getLayout().addComponent(serial);
	    	
	   	number = new TextField("Номер паспорта");
	   	number.setImmediate(true);
	   	number.setWidth("220px");
	   	number.setVisible(false);    	
	   	mainForm.getLayout().addComponent(number);
	   	
	   	address = new TextField("Адрес регистрации");
	   	address.setImmediate(true);
	   	address.setWidth("220px");
	   	address.setVisible(false);
	   	mainForm.getLayout().addComponent(address);
	    	
    }
   
 @SuppressWarnings("deprecation")
 private void buildButtonForm() {
   	
   	OK = new Button("Добавить");
   	cancel = new Button("Отмена");
   	
   	mainForm.getFooter().addComponent(cancel);
   	mainForm.getFooter().addComponent(OK);
   	
   	OK.addClickListener(new ClickListener() {
			
		@SuppressWarnings("unchecked")
		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			try {					
				BeanItem<ZayvkaCard> item = (BeanItem<ZayvkaCard>) mainForm.getItemDataSource();
				zayvkiCard = item.getBean();
				zayvkiCard.setStatus("Принято");
				zayvkiCard.setPayIn(paySystem.getValue().toString());
				ZayvkiCardDB zayvkiCardDB = new ZayvkiCardDB(DB.getConnection());
				// Запись с БД
				zayvkiCardDB.WriteZayvka(zayvkiCard);
				DbDop.WriteRegZayvka(zayvkiCard);
				// Сообщение о выполнении операции
				MessageBox.showPlain(Icon.INFO, "Фомрирование заявки", 
						"Формирование заявки выполнено!", ButtonId.OK);
				// Информация о зарегистрированной заявке
				/*RegZayvki regZayvka = new RegZayvki(zayvkiCard);
				RegZayvkiDB regDb = new RegZayvkiDB(DB.getConnection());
				regDb.WriteRegZayvka(regZayvka);*/
				// Очистка данных
				clearFieldsForm();				
			} catch (Exception e) {					
				e.printStackTrace();
			}
		}
	 });
   	
   	cancel.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			// Очистка полей
		}
	});
   }
 
  /* Очистка полей формы */
@SuppressWarnings({ "unchecked", "deprecation" })
private void clearFieldsForm() {

 	 mainForm.getField("payOut").setValue("");
 	 mainForm.getField("fioClient").setValue("");
 	 mainForm.getField("payIn").setValue("");
 	 mainForm.getField("valuta").setValue("");
 	 mainForm.getField("wmid").setValue("");
 	 mainForm.getField("summaPay").setValue("");
 	 mainForm.getField("kommis").setValue("");
 	 mainForm.getField("summaCard").setValue("");
 	 mainForm.getField("date").setValue(new Date());
 	 //Поля для наличного расчета
 	 serial.setValue("");
 	 number.setValue("");
 	 address.setValue("");
  }

}