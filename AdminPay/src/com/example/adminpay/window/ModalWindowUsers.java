package com.example.adminpay.window;

import java.sql.SQLException;

import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.Users;
import com.example.adminpay.classes.UsersDB;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class ModalWindowUsers extends Window  {
	
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	private PasswordField pass = null;
	private Users bean = null;
	
	private BeanFieldGroup<Users> binder = new BeanFieldGroup<Users>(Users.class);
	
	/* Поля таблицы - Пользователи */
	private Object[] tableUsersFields = new Object[] { 
			"fio", "type", "telephone", "login"};
	/* Заголовки таблицы - Пользователи */
	private String[] tableUsersFieldsTitle = new String[] { 
			"ФИО пользователя", "Роль", "Телефон","Логин"};
	private boolean newUser = true;

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
			
			bean = new Users();			
			binder.setItemDataSource(bean);
		} else {
			bean = tmpUsers.getBean();
			newUser = false;
			binder.setItemDataSource(tmpUsers);
		}
		
		for (int i = 0; i < tableUsersFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableUsersFieldsTitle[i], tableUsersFields[i]));		
			binder.getField(tableUsersFields[i]).setWidth("250px");		
		}	
		
		binder.getField("login").setRequired(true);
		
		buildPasswordField();
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
					
					BeanItem<Users> item = binder.getItemDataSource();
					bean = item.getBean();
					bean.setPassword(pass.getValue());
					
					UsersDB userDB;
					userDB = new UsersDB(DB.getConnection());					
					//userDB.updateUser(bean);
					userDB.writeUser(bean);
					
				} catch (CommitException e1) {					
					e1.printStackTrace();
				} catch(SQLException e) {
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
        mainForm.addComponent(gridLayout);
	}
	
	/**
	 * Создание поля для ввода пароля
	 */
	private void buildPasswordField() {
		
		pass = new PasswordField("Пароль");
		pass.setRequired(true);
		pass.setWidth("250px");
		
		if (newUser != true) {
			// Редактирование пользователя
			pass.setValue(bean.getPassword());
		}
		mainForm.addComponent(pass);
	}
}
