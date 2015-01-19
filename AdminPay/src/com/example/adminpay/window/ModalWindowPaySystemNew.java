package com.example.adminpay.window;

import java.util.Collection;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.PaySystem;
import com.vaadin.ui.Window;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

public class ModalWindowPaySystemNew extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout = null;
	private HorizontalLayout valLayout = null;
	private GridLayout gridLayout = null;
	private Button addBtn = null;
	private Button delBtn = null;
	private TextField payName = null;
	private TextField valName = null;
	private ListSelect listVal= null;
	
	private PaySystem payEdit = null;
	private String valueChangeString = "";
	private boolean edit = false; // Режим редактирования
	
	public ModalWindowPaySystemNew(PaySystem tmpPaySystem) {
		
		super("Добавление платежной системы");	
		
		setPositionX(200);
		setPositionY(100);
		setHeight("600px");
		setWidth("550px");
		setModal(true);
		
		if (tmpPaySystem != null) {
			payEdit = tmpPaySystem;
			edit = true;
		}
	
		mainLayout = new VerticalLayout();
		setContent(mainLayout);
		
		buildPaySystem();
		buildButtonForm();
	}
	
	private void buildPaySystem() {
		
		payName = new TextField("Платежная система");
		payName.setWidth("180px");		
		mainLayout.addComponent(payName);
		
		valLayout = new HorizontalLayout();
		valLayout.setWidth("100%");
		
		valName = new TextField();
		valName.setWidth("180px");
		valLayout.addComponent(valName);
		
		addBtn = new Button("Добавить");
		addBtn.setWidth("75px");
		addBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Добавление нового наименования валюты
				if (!valName.getValue().toString().equals("")) {
					listVal.addItem(valName.getValue().toString());
					valName.setValue("");
				}
				
			}
		});
		valLayout.addComponent(addBtn);
		
		delBtn = new Button("Удалить");
		delBtn.setWidth("75px");
		delBtn.setEnabled(false);
		delBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (!valueChangeString.equals("")) {
            		listVal.removeItem(valueChangeString);
            	}
			}
		});
		valLayout.addComponent(delBtn);
		
		mainLayout.addComponent(valLayout);
		
		listVal = new ListSelect("Наименования валют");
		listVal.setWidth("250px");
		//listVal.setRows(0);
		if (edit) {
			payName.setValue(payEdit.getPay());
			String[] valList = payEdit.getList_val().split("/");
			for (int i = 0; i < valList.length; i++) {
				listVal.addItem(valList[i]);
			}
		}
		listVal.removeItem(valueChangeString);
		listVal.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                
            	valueChangeString = String.valueOf(event.getProperty()
                        .getValue());
            	if (!valueChangeString.equals("")) {
            		delBtn.setEnabled(true);
            	} else {
            		delBtn.setEnabled(false);
            	}
               
            }
        });
		mainLayout.addComponent(listVal);
		
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
					PaySystem pay = new PaySystem();
					if (!payName.getValue().toString().equals("")) {
						pay.setPay(payName.getValue().toString());
						pay.setList_val(getStrVal());						
						if (edit) {
							// Редактирование
							pay.setId(payEdit.getId());
							BaseRW.updatePaySystem(DB.getConnection(), pay);
						} else {
							// Добавление новой платежной системы в БД
							BaseRW.addPaySystem(DB.getConnection(), pay);
						}
						
					} else {
						MessageBox.showPlain(Icon.ERROR, "Ошибка ввода", 
								"Введены не все значения", ButtonId.OK);
					}	
					close();
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
		
        //mainForm.addComponent(ok);
        mainLayout.addComponent(gridLayout);
	}	
	
	/**
	 * Формирование строки со списком валют
	 * 
	 * @return
	 */
	private String getStrVal() {
		
		String strVal = "";
		
		Collection<String> colVal =  (Collection<String>) listVal.getItemIds();
		Object[] arrVal = colVal.toArray();
		
		for (int i = 0;  i < arrVal.length; i++) {
			strVal = strVal + arrVal[i].toString() + "/";
		}		
		
		return strVal;
	}
		
	

}
