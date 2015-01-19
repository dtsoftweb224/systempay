package com.example.adminpay.window;

import java.sql.SQLException;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.Bik;
import com.example.adminpay.classes.Bik_old;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.Users;
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

public class ModalWindowBik extends Window {
	
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	private ComboBox comboNal = null;
	private ComboBox comboBezNal = null;
	private Bik bean = null;
	
	private BeanFieldGroup<Bik> binder = new BeanFieldGroup<Bik>(Bik.class);
	
	/* Поля таблицы - Банки БИК */
	private Object[] tableBikFields = new Object[] {"bik",  
			"ks", "name", "ind", "city", "address", "phone",
			"okato", "okpo", "tranzit"};
	/* Заголовки таблицы - Банки БИК */
	private String[] tableBikFieldsTitle = new String[] {"БИК",  
			"Корр. счет", "Название", "Индекс", 
			"Город", "Адрес", "Телефон", "ОКАТО", "ОКПО", "Транзитный счет"};
	
	public ModalWindowBik(BeanItem<Bik> tmpBik) {

		super("Окно редактирования БИК");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("45%");
		setHeight("52%");
		setModal(true);	
		
		// Создание формы
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		buldSettingFields();
		
		if (tmpBik == null) {
			
			Bik bean = new Bik();			
			binder.setItemDataSource(bean);			
		} else {
			
			binder.setItemDataSource(tmpBik);		
		}
		
		for (int i = 0; i < tableBikFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableBikFieldsTitle[i], tableBikFields[i]));		
			binder.getField(tableBikFields[i]).setWidth("250px");	
			binder.getField(tableBikFields[i]).setEnabled(false);	
		}
		binder.getField(tableBikFields[tableBikFields.length - 1]).setEnabled(true);
		
		buildButtonForm();
		setContent(mainForm);
	}
	
	/* Создание полей, которые отвечают за выбор отображения 
	 * данных на разных формах
	 */
	private void buldSettingFields() {
		
		comboNal = new ComboBox("Форма наличный расчет");
		comboNal.addItem("Отображается");
		comboNal.addItem("Не отображается");
		comboNal.setValue("Не отображается");
		//mainForm.addComponent(comboNal);
		
		comboBezNal = new ComboBox("Форма безналичный расчет");
		comboBezNal.addItem("Отображается");
		comboBezNal.addItem("Не отображается");
		comboBezNal.setValue("Не отображается");
		//mainForm.addComponent(comboBezNal);
	}
	
	/* Создание кнопок */
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
					/*binder.commit();
					
					BeanItem<Bik> item = binder.getItemDataSource();
					bean = item.getBean();
					//bean.setBeznal(comboBezNal.getValue().toString());
					//bean.setNal(comboNal.getValue().toString());
					binder.setItemDataSource(bean);					
					close();
					//BaseRW.updateBik(DB.getConnection(), bean);	*/				
					
				} catch (Exception e1) {					
					e1.printStackTrace();
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
