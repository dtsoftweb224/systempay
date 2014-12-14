package com.example.adminpay.window;

import com.example.adminpay.classes.Client;
import com.example.adminpay.classes.Kommis;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ModalWindowKommis extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	private ComboBox typePay = null;
	
	private BeanFieldGroup<Kommis> binder = new BeanFieldGroup<Kommis>(Kommis.class);
	private Kommis bean = null;
	
	/* Поля таблицы - Коммисии */
	private Object[] tableKommisFields = new Object[] {"id", "paySystem", 
			"komSystem", "kommis", "minKommis", "maxKommis"};
	/* Заголовки таблицы - Коммисии */
	private String[] tableKommisFieldsTitle = new String[] {"Идентифкатор", 
			"Платежная система", "Комиссия системы", "Наша комиссия", 
			"Мин. комиссия", "Макс. комиссия"};
	
	public ModalWindowKommis(BeanItem<Kommis> tmpKommis) {
		
		// Создание окна Редактирования клиенты
		super("Окно редактирования комиссии");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("400px");
		setHeight("450px");
		setModal(true);	
		
		// Создание формы
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		if (tmpKommis == null) {
			
			bean = new Kommis();			
			//binder.setItemDataSource(bean);
		} else {
			bean = tmpKommis.getBean();
			//binder.setItemDataSource(tmpKommis);
		}
		binder.setItemDataSource(bean);
		
		// Настройки формы
		for (int i = 0; i < tableKommisFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableKommisFieldsTitle[i], tableKommisFields[i]));		
			binder.getField(tableKommisFields[i]).setWidth("250px");				
		}
		
		buildTypePay(bean.getType_pay());
		buildButtonForm();
		setContent(mainForm);
	}
	
	@SuppressWarnings("serial")
	private void buildButtonForm() {
		
		gridLayout = new GridLayout(2, 1);
		gridLayout.setWidth("100%");	
		
		Button ok = new Button("Сохранить");
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
				try {
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
	
	/**
	 * Создание выпадающего списка для выбора 
	 * типа кошелька - ввод/вывод
	 */
	private void buildTypePay(String value) {
		
		typePay = new ComboBox("Тип кошелька");
		typePay.setWidth("250px");
		typePay.addItem("Ввод");
		typePay.addItem("Вывод");
		
		if ((value == null) || (value == "")) {
			typePay.setValue("Ввод");
		} else {
			typePay.setValue(value);
		}		
		
		mainForm.addComponent(typePay);
	}

}
