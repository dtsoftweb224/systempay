package com.example.managerpay;
import java.util.List;

import sun.awt.HorizBagLayout;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.Handling;
import com.example.managerpay.classes.HandlingDB;
import com.example.managerpay.classes.Zayvki;
import com.example.managerpay.classes.ZayvkiDB;
import com.example.managerpay.window.WindowHandlingKomment;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;


@SuppressWarnings("serial")
public class WindowHandlingActive extends Window {
	
	private GridLayout gridForm = null;
	private FormLayout formRod = null;
	private FormLayout formNew = null;
	private VerticalLayout mainLayout = null;
	private HorizontalLayout btnLayout = null;
	
	private Button btnAdd = null;      // Кнопка Завести
	private Button btnExit = null;	   // Кнопка Выход	
	private Button btnComment = null;  // Кнопка Комментарии
	
	private Handling handling = null;
	private Zayvki zayvkaRod = null;
	private Zayvki zayvkaNew = null;
	
	BeanFieldGroup<Zayvki> binderRod = new BeanFieldGroup<Zayvki>(Zayvki.class);
	BeanFieldGroup<Zayvki> binderNew = new BeanFieldGroup<Zayvki>(Zayvki.class);
	
	ZayvkiDB zayvkiDB = null;
	HandlingDB handlingDB = null;
	
	private String[] formFields = new String[] {"numberPay", "wmid", "date", "payOut",
			"payIn", "valuta", "summaPay", "kommis", "summaCard", "numSchet",
			"status","mail", "telephone", "fName","lName", "otch"};
	/* Названия полей для отображения*/
	private String[] formFieldsTitle = new String[] {"Номер заявки","Кошелек", "Дата", "Банк",
			"Платежная система", "Валюта", "Сумма списания","Комиссия", "Сумма зачисления", "Счет",
			"Статус", "Mail", "Телефон", "Имя", "Фамилия", "Отчество"};
	
	public WindowHandlingActive(Handling handl) {
		
		super("Создание обращения");	
				
		setPositionX(200);
		setPositionY(100);
		setHeight("700px");
		setWidth("850px");
		setModal(true);
		
		handling = handl;	
		loadDataZayvki();
		
		buildMainLayout();
		buildGridLayout();
		buildButtonForm();
	}
	
	/**
	 * Создание главного слоя формы
	 */
	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		
		setContent(mainLayout);
	}
	
	/**
	 * Создание слоя с двумя формами, 
	 * которые содержат родительскую и новую заявки
	 */
	private void buildGridLayout() {
		
		gridForm = new GridLayout(2,1);
		
		// Создание формы родительской заявки
		formRod = new FormLayout();
		formRod.setVisible(true);
		formRod.addComponent(new Label("Родительская заявка"));
		formRod.addStyleName("form-handling");		
		
		// Создание формы новой заявки
		formNew = new FormLayout();
		formNew.setVisible(true);
		formNew.addComponent(new Label("Новая заявка"));
		formNew.addStyleName("form-handling");
		
		for (int i = 0; i < formFields.length; i++)
		{
			formRod.addComponent(binderRod.buildAndBind(formFieldsTitle[i], formFields[i]));
			binderRod.getField(formFields[i]).setWidth("220px");
		}
		
		for (int i = 0; i < formFields.length; i++)
		{
			formNew.addComponent(binderNew.buildAndBind(formFieldsTitle[i], formFields[i]));
			binderNew.getField(formFields[i]).setWidth("220px");
		}
		
		gridForm.addComponent(formRod, 0, 0);
		gridForm.addComponent(formNew, 1, 0);
		
		mainLayout.addComponent(gridForm);
	}
	
	/**
	 * Слоя для размещения кнопок
	 */
	private void buildButtonForm() {
		
		btnLayout = new HorizontalLayout();
		
		btnExit = new Button("Выход");
		btnExit.addStyleName("btn-handl-exit");
		btnExit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		btnLayout.addComponent(btnExit);
		
		btnAdd = new Button("Завести");
		btnAdd.addStyleName("btn-handl-form");
		btnAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Создание новой заявки
				try {
					zayvkiDB.InsertNewZayvka(zayvkaNew);
					// Изменение статуса обращения
					handlingDB.UpdateHandlingStatus(DB.getConnection(), handling, "Закрыто");
									
					// Кнопки делаем недоступными
					btnAdd.setEnabled(false);
					btnComment.setEnabled(false);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnLayout.addComponent(btnAdd);
		
		btnComment = new Button("Комментарии");
		btnComment.addStyleName("btn-handl-form");
		btnComment.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Вызов окна для добавления комментария
				Window win = new WindowHandlingKomment(handling);					
				UI.getCurrent().addWindow(win);
				btnAdd.setEnabled(false);
				btnComment.setEnabled(false);
			}
		});
		btnLayout.addComponent(btnComment);
		
		mainLayout.addComponent(btnLayout);
	}
	
	/**
	 * Загрузка заявок из БД
	 */
	private void loadDataZayvki() {
		
		// Загрузка заявок из БД
		zayvkiDB = new ZayvkiDB(DB.getConnection());
		handlingDB = new HandlingDB();		
				
		zayvkaRod = zayvkiDB.getZayvkaID(handling.getIdZayvki_rod());
		zayvkaNew = handlingDB.getZayvkaID(DB.getConnection(), handling.getIdZayvki());
		// Помещение данных в binder
		binderRod.setItemDataSource(zayvkaRod);
		binderNew.setItemDataSource(zayvkaNew);
	}

}


