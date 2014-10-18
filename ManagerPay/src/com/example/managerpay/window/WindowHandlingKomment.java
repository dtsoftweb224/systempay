package com.example.managerpay.window;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.Handling;
import com.example.managerpay.classes.HandlingDB;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Окно, которое служит для ввода
 * комментариев для обращения
 * 
 * @author prizrak
 *
 */
@SuppressWarnings("serial")
public class WindowHandlingKomment extends Window  {
	
	private VerticalLayout mainLayout = null;
	private HorizontalLayout btnLayout = null;
	private TextArea kommentText = null;
	private Button btnExit = null;
	private Button btnAdd = null;
	
	private Handling handling = null;
	
	public WindowHandlingKomment(Handling handl) {
		
		super("Комментарии для обращения");	
		
		setPositionX(300);
		setPositionY(200);
		setHeight("300px");
		setWidth("450px");
		setModal(true);
		
		handling = handl;
		
		mainLayout = new VerticalLayout();
		setContent(mainLayout);
		
		buildTextArea();
		buildBtn();
	}
	
	/**
	 * Создание текстового поля для ввода комментариев
	 */
	private void buildTextArea() {
		
		kommentText = new TextArea();		
		kommentText.setRows(10);
		kommentText.setImmediate(true);
		kommentText.setSizeFull();
		
		mainLayout.addComponent(kommentText);
	}
	
	/**
	 * Создание кнопок - Выход, Добавить
	 */
	private void buildBtn() {
		
		btnLayout = new HorizontalLayout();
		
		btnExit = new Button("Выход");
		btnExit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
		
				close();
			}
		});
		btnLayout.addComponent(btnExit);
		
		btnAdd = new Button("Добавить");
		btnAdd.addStyleName("btn-handl-form");
		btnAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				// Добавление комментариев к обращению
				HandlingDB handlingDB = new HandlingDB();
				handlingDB.UpdateHandlingKomment(DB.getConnection(), handling, kommentText.getValue());
				close();
			}
		});
		btnLayout.addComponent(btnAdd);
		
		mainLayout.addComponent(btnLayout);
	}

}
