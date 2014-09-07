package com.example.clientpay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.clientpay.classes.DB;
import com.example.clientpay.classes.RegZayvki;
import com.example.clientpay.classes.RegZayvkiDB;
import com.example.clientpay.classes.ZayvkaCard;
import com.example.clientpay.classes.Zayvki;
import com.example.clientpay.classes.ZayvkiCardDB;
import com.example.clientpay.classes.ZayvkiDB;
import com.example.clientpay.support.DbDop;
import com.example.clientpay.support.SendMail;
import com.example.clientpay.support.SendSMS;
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
	private Zayvki zayvkiCard = new Zayvki();
	private BeanItem<Zayvki> beans = null;
	
	private Button OK;
	private Button cancel;
	private ComboBox type;
	private TextField serial;  // Серия паспорта 
	private TextField number;  // Номер паспорта
	private TextField address; // Адрес регистрации
	private ComboBox paySystem;
	private ComboBox banki;
	private VerticalLayout identLayout;
	
	private String[] formFields = new String[] {"wmid", "date", "payOut",
			"fioClient", "valuta", "summaPay", "kommis", "summaCard", 
			"mail"};
	/* Названия полей для отображения*/
	private String[] formFieldsTitle = new String[] {"Кошелек", "Дата", "Банк",
			"ФИО клиента", "Валюта", "Сумма списания", 
			"Комиссия", "Сумма зачисления", "Эл. почта"};
	
	private String[] formFields1 = new String[] {"date", "payIn", "wmid",
			"valuta","mail", "telephone", "lName", "fName", "otch", "summaPay", 
			"kommis", "summaCard", "numSchet", "status"};
	/* Названия полей для отображения*/
	private String[] formFieldsTitle1 = new String[] {"Дата", "Платежная система", "Кошелек", 
			"Валюта", "Mail", "Телефон", "Фамилия", "Имя", "Отчество", 
			"Списание", "Комиссия", "Зачисление", "Счет", "Статус"};

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
	    buildBankiBox();
	    paySystem = new ComboBox("Платежная система");
	    paySystem.addItem("WebMoney");
	    paySystem.addItem("Яндекс");
	    paySystem.setWidth("220px");
	    paySystem.setValue("WebMoney");
	    mainForm.getLayout().addComponent(paySystem);
	    	
	    zayvkiCard.setDate(new Date());
	    beans = new BeanItem<Zayvki>(zayvkiCard);
		mainForm.setItemDataSource(beans); 
		/* Поля видимые на форме */
		 mainForm.setVisibleItemProperties(formFields1);
		 for (int i = 0; i < formFields1.length; i++) {
				 
			 /* Названия полей */
			 mainForm.getField(formFields1[i]).setCaption(formFieldsTitle1[i]);
			 /* Установка ширины поля */
			 mainForm.getField(formFields1[i]).setWidth("220px");
		 }
		 // Временное заполнение полей формы
		 mainForm.getField("mail").setValue("prizrak309@mail.ru");
		 //mainForm.getField("payOut").setValue("Сбербанк");
		 mainForm.getField("payIn").setValue("WebMoney");
		 mainForm.getField("fName").setValue("Иван");	
		 mainForm.getField("lName").setValue("Иванов");	
		 mainForm.getField("otch").setValue("Иванович");
		 mainForm.getField("telephone").setValue("");	
		 mainForm.getField("valuta").setValue("RUR");
		 //		 
		 buildIdentField();
		 buildButtonForm();
    }	
	 
  private void buildTypeBox() {
	    	
	   	type = new ComboBox("Тип оплаты");
	   	type.addItem("На карту");
	   	type.addItem("Наличный");
	   	type.setImmediate(true);
	   	type.setValue("На карту");
	   	type.setWidth("220px");	   
	   	type.setEnabled(false);
	   	
	   	type.addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (type.getValue().equals("Наличный")) {
					serial.setVisible(true);
					number.setVisible(true);
					address.setVisible(true);
				}
				
				if (type.getValue().equals("На карту")) {
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
			String numPay = generateNumberPay();
			try {					
				BeanItem<Zayvki> item = (BeanItem<Zayvki>) mainForm.getItemDataSource();
				zayvkiCard = item.getBean();
				zayvkiCard.setStatus("Принято");
				zayvkiCard.setType(type.getValue().toString());
				zayvkiCard.setPayIn(paySystem.getValue().toString());
				zayvkiCard.setNumberPay(numPay);
				zayvkiCard.setPayOut(banki.getValue().toString());
				ZayvkiDB zayvkiDB = new ZayvkiDB(DB.getConnection());
				
				zayvkiDB.WriteZayvka(zayvkiCard);
				DbDop.WriteRegZayvka(zayvkiCard);
				// Сообщение о выполнении операции
				MessageBox.showPlain(Icon.INFO, "Фомрирование заявки", 
						"Формирование заявки выполнено!", ButtonId.OK);
				// Информация о зарегистрированной заявке
				// Отправка SMS
				/*SendSMS sms = new SendSMS();
				sms.sendRegZayvka("79041698744", numPay);*/
				// Отправка уведомления на e-mail
				SendMail sendMail = new SendMail(zayvkiCard.getMail(), numPay);
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

 	 mainForm.getField("fName").setValue("");	
	 mainForm.getField("lName").setValue("");	
	 mainForm.getField("otch").setValue("");
 	 mainForm.getField("payIn").setValue("");
 	 mainForm.getField("valuta").setValue("");
 	 mainForm.getField("wmid").setValue("");
 	 mainForm.getField("date").setValue(new Date());
 	 banki.setValue("");
 	 //Поля для наличного расчета
 	 serial.setValue("");
 	 number.setValue("");
 	 address.setValue("");
  }
 
 private void buildBankiBox() {
	
	 banki = new ComboBox("Банк");
	 banki.setImmediate(true);
	 banki.setValue("На карту");
	 banki.setWidth("220px");
	 
	 try {
		DbDop.GetBikLoadCombo(banki, true);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
	 mainForm.getLayout().addComponent(banki);
 }

   /* Функция генерации номера платежа */
	private String generateNumberPay() {
		
		String numPay = "";
		int num = 1;
		
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("MMddyy");
		numPay = df.format(now);
		
		if (type.getValue().equals("На карту")) {
			numPay = numPay + "C";
			try {
				num = DbDop.GetNumIndexPay(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			numPay = numPay + String.valueOf(num);
		}
		
		return numPay;
	}

}