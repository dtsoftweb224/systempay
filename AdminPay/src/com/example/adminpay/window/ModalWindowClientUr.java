package com.example.adminpay.window;

import java.sql.SQLException;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.Bik;
import com.example.adminpay.classes.ClientUr;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.Users;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Класс для создания окна добавления
 * нового юридического лица
 * 
 * @author HP-PK
 *
 */
public class ModalWindowClientUr extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	
	//private BeanItemContainer<ClientUr> beansClientUr = new BeanItemContainer<ClientUr>(ClientUr.class);
	private BeanFieldGroup<ClientUr> binder = new BeanFieldGroup<ClientUr>(ClientUr.class);
	private ClientUr bean = null;
	
	/* Поля таблицы - Клиенты */
	private Object[] tableClientUrFields = new Object[] {"ogrn", 
			"inn", "kpp", "fioDirector", "mail", "telephone"};
	/* Заголовки таблицы - Клиенты */
	private String[] tableClientUrFieldsTitle = new String[] {"ОГРН", 
			"ИНН", "КПП", "ФИО директора", "Mail", "Телефон"};
	
	public ModalWindowClientUr(BeanItem<ClientUr> tmpClientUr) {
		// Создание окна Редактирования клиенты
		super("Окно создания клиента");
				
		setPositionX(200);
		setPositionY(150);
				
		setWidth("400px");
		setHeight("450px");
		setModal(true);
		
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		if (tmpClientUr == null) {
			
			ClientUr bean = new ClientUr();			
			binder.setItemDataSource(bean);
		} else {
			binder.setItemDataSource(tmpClientUr);
		}
		
		// Настройки формы
		for (int i = 0; i < tableClientUrFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableClientUrFieldsTitle[i], tableClientUrFields[i]));		
			binder.getField(tableClientUrFields[i]).setWidth("250px");	
			
		}
				
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
			
				/* Добавление нового клиента в БД */
				try {
					binder.commit();
					
					BeanItem<ClientUr> item = binder.getItemDataSource();
					bean = item.getBean();
					binder.setItemDataSource(bean);
					
					try {
						BaseRW.addClientUr(DB.getConnection(), bean);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
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

        mainForm.addComponent(gridLayout);
	}	

}
