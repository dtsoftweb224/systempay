package com.example.adminpay.window;

import com.example.adminpay.classes.Bik;
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
	
	private BeanFieldGroup<Bik> binder = new BeanFieldGroup<Bik>(Bik.class);
	
	/* Поля таблицы - Банки БИК */
	private String[] tableBikFields = new String[] {"bik", "swift", 
			"bank"};
	/* Заголовки таблицы - Банки БИК */
	private String[] tableBikFieldsTitle = new String[] {"БИК", "СВИФТ", 
			"Наименование банка"};
	
	public ModalWindowBik(BeanItem<Bik> tmpBik) {

		super("Окно редактирования БИК");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("45%");
		setHeight("35%");
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
			
			if (tmpBik.getBean().isForm_beznal()) {
				comboBezNal.setValue("Отображать");
			}
			
			if (tmpBik.getBean().isForm_nal()) {
				comboNal.setValue("Отображать");
			}
		}
		
		for (int i = 0; i < tableBikFields.length; i++)
		{
			mainForm.addComponent(binder.buildAndBind(tableBikFieldsTitle[i], tableBikFields[i]));		
			binder.getField(tableBikFields[i]).setWidth("250px");		
		}
		
		mainForm.addComponent(comboNal);
		mainForm.addComponent(comboBezNal);
		buildButtonForm();
		setContent(mainForm);
	}
	
	/* Создание полей, которые отвечают за выбор отображения 
	 * данных на разных формах
	 */
	private void buldSettingFields() {
		
		comboNal = new ComboBox("Форма наличный расчет");
		comboNal.addItem("Отображать");
		comboNal.addItem("Не отображать");
		comboNal.setValue("Не отображать");
		//mainForm.addComponent(comboNal);
		
		comboBezNal = new ComboBox("Форма безналичный расчет");
		comboBezNal.addItem("Отображать");
		comboBezNal.addItem("Не отображать");
		comboBezNal.setValue("Не отображать");
		//mainForm.addComponent(comboBezNal);
	}
	
	/* Создание кнопок */
	@SuppressWarnings("serial")
	private void buildButtonForm() {
		
		gridLayout = new GridLayout(2, 1);
		gridLayout.setWidth("100%");	
		
		Button ok = new Button("Сохраненить");
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
				//BeanItem<ZayvkaCard> item = binder.getItemDataSource();
				//ZayvkaCard zayvkaCard = item.getBean();
				
				try {
					//ZayvkiCardDB zayvkaDB = new ZayvkiCardDB(DB.getConnection());
					//zayvkaDB.UpdateZayvkaDB(zayvkaCard);
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
