package com.example.adminpay.window;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.PaySystem;
import com.example.adminpay.classes.Users;
import com.example.adminpay.classes.UsersDB;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

public class ModalWindowPaySystem extends Window {
	
	private VerticalLayout mainLayout = null;
	private FormLayout mainForm = null;
	private GridLayout grid = null;
	private GridLayout btnLayout = null;
	private TextField paySystem;
	private Button addVal;	
	
	private int countValut = 2;
	private List<TextField> listFileds = new ArrayList<TextField>();
	
	@SuppressWarnings("serial")
	public ModalWindowPaySystem() {
		
		super("Добавление новой пл. системы");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("600px");
		setHeight("300px");
		setModal(true);	
		
		// Создание формы
		//mainForm = new FormLayout();
		//mainForm.setVisible(true);
		mainLayout = new VerticalLayout();
		
		// Поле ввода названия пл. системы
		paySystem = new TextField(); 
		paySystem.setWidth("180px");
		paySystem.addStyleName("valuta-field");
		//mainLayout.addComponent(paySystem);
		
		// Формирование
		grid = new GridLayout(3, 6);
		grid.setWidth("80%");
		// Добавление поля пл. система
		grid.addComponent(new Label("Платежная система"), 0, 0);
		grid.addComponent(paySystem, 1, 0);		
		
		// Добавление первого поля валюта
		TextField tmpField = new TextField();
		tmpField.setWidth("180px");
		tmpField.addStyleName("valuta-field");
		// Добавление поля в список полей
		//listFileds.add(tmpField);
		grid.addComponent(new Label("Валюта " + String.valueOf(countValut)), 0, 1);
		grid.addComponent(tmpField, 1, 1);		
		// Формирование кнопки Добавить
		addVal = new Button("Добавить");
		grid.addComponent(addVal, 2, 1);
		
		addVal.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Добавление нового поля для ввода валюты
				TextField tmpField = new TextField();
				tmpField.setWidth("180px");
				tmpField.addStyleName("valuta-field");
				// Добавление поля в список полей
				//listFileds.add(tmpField);
				// Размещение созданного поля на форме
				countValut = countValut + 1;
				grid.addComponent(new Label("Валюта " + String.valueOf(countValut)), 0, countValut - 1);
				grid.addComponent(tmpField, 1, countValut - 1);
			}
		});	
		
		//grid.setColumnExpandRatio(1, 1);
		
		mainLayout.addComponent(grid);
		buildButtonForm();
		setContent(mainLayout);
	}
	
	private void buildButtonForm() {
		
		btnLayout = new GridLayout(2, 1);
		btnLayout.setWidth("100%");	
		
		Button ok = new Button("Сохранить");
		Button cancel = new Button("Отмена");
		
		btnLayout.addComponent(cancel, 0, 0);
		btnLayout.addComponent(ok, 1, 0);
        
		ok.addClickListener(new ClickListener() {
      
			@Override
			public void buttonClick(ClickEvent event) {
			
				try {	
					
					PaySystem pay = new PaySystem();
					if (!paySystem.getValue().toString().equals("")) {
						pay.setPay(paySystem.getValue().toString());
						pay.setList_val(getValField());
						// Добавление новой платежной системы в БД
						BaseRW.addPaySystem(DB.getConnection(), pay);
					} else {
						MessageBox.showPlain(Icon.ERROR, "Ошибка ввода", 
								"Введены не все значения", ButtonId.OK);
					}					
					
				}  catch(Exception e) {
					e.printStackTrace();
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
		mainLayout.addComponent(btnLayout);
	}

	/**
	 * Получение значений из полей для ввода
	 * наименования валюты
	 * Если поле пустое, то значение не заносится
	 * 
	 * @return
	 */
	private String getValField() {
		String strVal = "";
		// Проходим по все полям формы
		for (int i = 1; i < 6; i ++) {
			Object obj = grid.getComponent(1, i);
			
			if (obj instanceof TextField) {
				
				String val = ((TextField) obj).getValue().toString();
				
				if (!val.equals("")) {
					strVal = strVal + val +"/";
				}				 
			}	
		}
		
		strVal = strVal.substring(0, strVal.length()- 1);
		
		return strVal;
	}
	
}
