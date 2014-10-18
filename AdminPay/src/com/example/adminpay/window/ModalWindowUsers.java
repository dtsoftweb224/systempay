package com.example.adminpay.window;

import com.example.adminpay.classes.Users;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class ModalWindowUsers extends Window  {
	
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	
	private BeanFieldGroup<Users> binder = new BeanFieldGroup<Users>(Users.class);
	
	/* Поля таблицы - Пользователи */
	private Object[] tableUsersFields = new Object[] {"login", 
			"fio", "type", "telephone"};
	/* Заголовки таблицы - Пользователи */
	private String[] tableUsersFieldsTitle = new String[] {"Логин", "ФИО пользователя", 
			"Роль", "Телефон"};

	public ModalWindowUsers(BeanItem<Users> tmpUsers) {
		
		super("Окно редактирования пользователя");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("400px");
		setHeight("300px");
		setModal(true);	
		
		// Создание формы
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		if (tmpUsers == null) {
			
			Users bean = new Users();			
			binder.setItemDataSource(bean);
		} else {
			binder.setItemDataSource(tmpUsers);
		}
		
		for (int i = 0; i < tableUsersFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableUsersFieldsTitle[i], tableUsersFields[i]));		
			binder.getField(tableUsersFields[i]).setWidth("250px");	
		
		}
		
		buildButtonForm();
		setContent(mainForm);
	}
	
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
}
