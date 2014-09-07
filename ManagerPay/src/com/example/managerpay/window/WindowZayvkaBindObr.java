package com.example.managerpay.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Класс, который создает окно
 * для связавания заявки, которая размещена в архиве
 * с обращением клиента
 * @author prizrak
 *
 */
public class WindowZayvkaBindObr extends Window
								 implements Window.CloseListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FormLayout mainForm = null;
	private GridLayout gridLayout = null;
	private TextField numObrch = null;
	//private Label lb = null;
	private Label lbNumPay = null;
	
	/**
	 * @param numPay - номер платежа
	 */
	public WindowZayvkaBindObr(String numPay) {
		
		super("Связывание заявки и обращения");	
		
		setPositionX(200);
		setPositionY(100);
		setHeight("300px");
		setWidth("450px");
		setModal(true);
		
		mainForm = new FormLayout();
		mainForm.setVisible(true);
		
		lbNumPay = new Label("Номер заявки - " + numPay);
		mainForm.addComponent(lbNumPay);
		
		numObrch = new TextField("Номер обращения");
		numObrch.setImmediate(true);
		numObrch.setWidth("200px");
		numObrch.setVisible(true);
	   	mainForm.addComponent(numObrch);
		
		buildButtonForm();
		setContent(mainForm);
	}
	
	
	@SuppressWarnings("serial")
	private void buildButtonForm() {
		
		gridLayout = new GridLayout(2, 1);
		gridLayout.setWidth("100%");
		
		Button ok = new Button("Связать");
		Button cancel = new Button("Отмена");
		
		gridLayout.addComponent(cancel, 0, 0);
		gridLayout.addComponent(ok, 1, 0);
        
		ok.addClickListener(new ClickListener() {
      
			@Override
			public void buttonClick(ClickEvent event) {
			
				close();				
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

	
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
