package com.example.adminpay.window;

import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.adminpay.classes.BaseRW;
import com.example.adminpay.classes.DB;
import com.example.adminpay.classes.PaySystem;
import com.example.adminpay.classes.Rate;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

public class ModalWindowRate extends Window {
	
	private VerticalLayout mainLayout = null;
	private GridLayout gridForm = null; // Общий grid формы
	private GridLayout btnLayout = null;
	private GridLayout grid = null;
	
	private ComboBox operation = null;   // Выбор типа операции ввод/вывод
	private ComboBox typeRaschet = null; // Выбор типа расчета нал/безнал
	private ComboBox paySystem = null;   // Выбор платежной системы
	private ComboBox objectPay = null;   // Выбор объекта совершения операции
	private ComboBox stateRate = null;   // Выбор состояния тарифа вкл/выкл
	
	private BeanFieldGroup<Rate> binder = new BeanFieldGroup<Rate>(Rate.class);
	private Rate tmpRate = null;
	private int countVal = 0; // Количество типов валют для пл. системы
	private String operationValue = ""; // Текущее значение списка тип операции
	private String typeRaschetValue = "";
	private String objectPayValue = "";
	private boolean stateSave = false;
	private boolean edit = false; // Определяет редактирование или новая
	
	private TextField[] listFieldsVal;
	private TextField[] listFieldsMin;
	private TextField[] listFieldsMax;
	
	private String[] valKommis;
	private String[] valKommisMin;
	private String[] valKommisMax;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModalWindowRate(BeanItem<Rate> tmp) {
		
		super("Редактирование комисий");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("550px");
		setHeight("450px");
		setModal(true);
		
		if (tmp == null) {
			tmpRate = new Rate();
		} else {
			tmpRate= tmp.getBean();
			edit = true;
		}
		
		// Формирование текста шаблона к БД
		//sql = "SELECT * FROM rate WHERE operation"
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		setContent(mainLayout);
		
		buildFormFields();
		/* Если режим редактирования то
		 * делаем недоступными для редактирования
		 * поля выбора параметров 
		 */		
		//buildValFields();
	}	
	
	/**
	 * Формирование полей формы
	 */
	private void buildFormFields() {
		
		/* Создание основной сетки формы */
		gridForm = new GridLayout(3, 10);
		gridForm.addStyleName("grid-field-layout2");
		gridForm.setWidth("100%");
		
		/* Выпадающий список для выбора типа операции */
		operation = new ComboBox("");
		operation.setWidth("250px");
		operation.addItem("ВВОД");
		operation.addItem("ВЫВОД");
		operation.setValue("ВЫВОД");
		operationValue = "ВЫВОД";
		operation.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if (!operationValue.equals(operation.getValue().toString())) {
					// Очистка или загрузка комиссий
					clearTextField();
					operationValue = operation.getValue().toString();
				}
			}
		});
		// Добавление на форму
		gridForm.addComponent(new Label("Тип операции"), 0, 0);
		gridForm.addComponent(operation, 1, 0);
		
		/* Выпадающий список для выбора типа расчета */
		typeRaschet = new ComboBox("");
		typeRaschet.setWidth("250px");
		typeRaschet.addItem("НАЛИЧНЫЙ");
		typeRaschet.addItem("БЕЗНАЛИЧНЫЙ");
		typeRaschet.setValue("БЕЗНАЛИЧНЫЙ");
		typeRaschetValue = "БЕЗНАЛИЧНЫЙ";
		typeRaschet.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if (!typeRaschetValue.equals(typeRaschet.getValue().toString())) {
					// Очистка или загрузка комиссий
					clearTextField();
					typeRaschetValue = typeRaschet.getValue().toString();
				}
			}
		});
		// Добавление на форму
		gridForm.addComponent(new Label("Тип расчета"), 0, 1);
		gridForm.addComponent(typeRaschet, 1, 1);
		
		/* Выпадающий список для выбора платежной системы */
		paySystem = new ComboBox("");
		paySystem.setWidth("250px");
		List<String> payList = null;
		
		try {
			payList = BaseRW.getPaySystemList(DB.getConnection());
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
		
		/* Заполнение списка из БД */
		for (String str : payList) {
			
			paySystem.addItem(str);
		}
		if (edit) {
			paySystem.setValue(tmpRate.getPay_system());
		}
		// Обработка выбора платежной системы
		paySystem.addValueChangeListener(new ValueChangeListener() {
					
			@Override
			public void valueChange(ValueChangeEvent event) {
						
				// При выборе платежной системы 
				String strPay = paySystem.getValue().toString();
				// Передаем имя выбранной платежной системы
				buildValFields(strPay);
				buildButtonForm();
			}
		});
		// Добавление на форму
		gridForm.addComponent(new Label("Платежная система"), 0, 2);
		gridForm.addComponent(paySystem, 1, 2);
		
		/* Выпадающий список выбора объекта */
		objectPay = new ComboBox("");
		objectPay.setWidth("250px");
		objectPay.addItem("БАНКИ");
		objectPay.addItem("ТЕРМИНАЛЫ");
		objectPay.addItem("КАССЫ");
		objectPay.setValue("БАНКИ");
		objectPayValue = "БАНКИ";
		objectPay.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if (!objectPayValue.equals(objectPay.getValue().toString())) {
					// Очистка или загрузка комиссий
					clearTextField();
					objectPayValue = objectPay.getValue().toString();
				}
			}
		});
		// Добавление на форму
		gridForm.addComponent(new Label("Объект"), 0, 3);
		gridForm.addComponent(objectPay, 1, 3);
		
		// Создание объекта 
		stateRate = new ComboBox();
		stateRate.setWidth("250px");
		stateRate.addItem("ВКЛЮЧЕНО");
		stateRate.addItem("ВЫКЛЮЧЕНО");		
		stateRate.setValue("ВКЛЮЧЕНО");
		// Добавление на форму
		gridForm.addComponent(new Label("Состояние"), 0, 4);
		gridForm.addComponent(stateRate, 1, 4);		
		
		//setContent(gridForm);
		mainLayout.addComponent(gridForm);
		
		if (edit) {
			operation.setEnabled(false);
			typeRaschet.setEnabled(false);
			paySystem.setEnabled(false);
			objectPay.setEnabled(false);
			// Установка значений
			operation.setValue(tmpRate.getOperation());
			typeRaschet.setValue(tmpRate.getType_paschet());
			stateRate.setValue(tmpRate.getState());
			objectPay.setValue(tmpRate.getPoint());
			//paySystem.setValue(tmpRate.getPay_system());
			// Формирование и заполнение
			buildValFields(tmpRate.getPay_system().toString());
			buildButtonForm();
		}
	}
	
	/**
	 * @param pay - название платежной системы
	 */
	private void buildValFields(String pay) {
		
		String valuts = "";
		try {
			valuts = BaseRW.getPaySystem(DB.getConnection(), pay);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		// Получаем список наименований валют
		String[] listVal = valuts.split("/");
		countVal = listVal.length;
		/* Занчение комиссий */
		listFieldsVal = new TextField[countVal];
		/* Значение минимальной комиссии */
		listFieldsMin = new TextField[countVal];
		/* Значение максимальной комиссии */
		listFieldsMax = new TextField[countVal];
					
		// Создание поле для ввода
		for (int i = 0; i < countVal; i++) {
			listFieldsVal[i] = new TextField();
			listFieldsVal[i].addStyleName("valuta-field");
			//
			listFieldsMin[i] = new TextField();
			listFieldsMin[i].addStyleName("valuta-field");
			//
			listFieldsMax[i] = new TextField();
			listFieldsMax[i].addStyleName("valuta-field");
		}
		
		// Получение значений
		if (edit) {
			valKommis = tmpRate.getVal_point().split("/");
			valKommisMin = tmpRate.getMin_kom().split("/");
			valKommisMax = tmpRate.getMax_kom().split("/");
			// Запись значений в поля
			for (int i = 0; i < countVal; i++) {
				listFieldsVal[i].setValue(valKommis[i]);				
				listFieldsMin[i].setValue(valKommisMin[i]);
				listFieldsMax[i].setValue(valKommisMax[i]);
			}
		}
		
		grid = new GridLayout(6, countVal + 1);
		grid.addStyleName("grid-field-layout");
		grid.setVisible(true);
		//grid.setWidth("100%");
		
		/* Создание названий столбцов */
		Label lbValuta = new Label("Валюта");
		Label lbKommis = new Label("Комиссия");
		Label lbKomMin = new Label("Мин. комиссия");
		Label lbKomMax = new Label("Макс. комиссия");
		// Добавление названий столбцов 
		grid.addComponent(lbValuta, 0, 0);
		grid.addComponent(lbKommis, 1, 0);
		grid.addComponent(lbKomMin, 2, 0);
		grid.addComponent(lbKomMax, 3, 0);
		// Установка выравнивания компонентов
		grid.setComponentAlignment(lbValuta, Alignment.MIDDLE_RIGHT);
		grid.setComponentAlignment(lbKommis, Alignment.MIDDLE_RIGHT);
		grid.setComponentAlignment(lbKomMin, Alignment.MIDDLE_RIGHT);
		grid.setComponentAlignment(lbKomMax, Alignment.MIDDLE_RIGHT);
		
		for (int j = 0; j < countVal; j++) {
			
			grid.addComponent(new Label(listVal[j]), 0, j+1);
			grid.addComponent(listFieldsVal[j], 1, j+1);
			grid.addComponent(listFieldsMin[j], 2, j+1);
			grid.addComponent(listFieldsMax[j], 3, j+1);
		}
		
		mainLayout.addComponent(grid);		
		
	}
	
	/**
	 * Функция формирования кнопок
	 */
	private void buildButtonForm() {
		
		btnLayout = new GridLayout(2, 1);
		btnLayout.setWidth("100%");	
		btnLayout.addStyleName("grid-btn-layout");
		
		Button ok = new Button("Сохранить");
		Button cancel = new Button("Отмена");
		
		btnLayout.addComponent(cancel, 0, 0);
		btnLayout.addComponent(ok, 1, 0);
        
		ok.addClickListener(new ClickListener() {
      
			@Override
			public void buttonClick(ClickEvent event) {
			
				try {	
					stateSave = true;
					// Сохранение значений тарифа в БД
					if (!operationValue.equals("") && !typeRaschetValue.equals("")
						&& !objectPayValue.equals("") && !stateRate.equals("")) {
						Rate rate = new Rate();
						rate.setOperation(operationValue);
						rate.setType_paschet(typeRaschetValue);
						rate.setPoint(objectPayValue);
						rate.setPay_system(paySystem.getValue().toString());
						// Запись значений в объект
						List<String> list = getAllKommis();
						// Комиссии
						rate.setVal_point(list.get(0));
						rate.setMin_kom(list.get(1));
						rate.setMax_kom(list.get(2));
						if(stateRate.equals("ВКЛЮЧЕНО")) {
							rate.setState(true);
						} else {
							rate.setState(false);
						}
						// Добавление в БД
						BaseRW.addRate(DB.getConnection(), rate);
						close();
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
	 * Функция очиски значений 
	 */
	private void clearTextField() {
		
		for (int i = 0; i < countVal; i++) {
			listFieldsVal[i].setValue("");
			listFieldsMin[i].setValue("");
			listFieldsMax[i].setValue("");			
		}
	}
	
	/**
	 * Получаем значение комиссий в виде списка строк
	 * 
	 * @return
	 */
	private List<String> getAllKommis() {
		
		String strKommis = "";
		String strKommisMin = "";
		String strKommisMax = "";
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < countVal; i++) {
			strKommis = strKommis + listFieldsVal[i].getValue().toString() + "/";
			strKommisMin = strKommisMin + listFieldsMin[i].getValue().toString() + "/";
			strKommisMax = strKommisMax + listFieldsMax[i].getValue().toString() + "/";			
		}
		
		strKommis = strKommis.substring(0, strKommis.length()-1);
		strKommisMin = strKommisMin.substring(0, strKommisMin.length()-1);
		strKommisMax = strKommisMax.substring(0, strKommisMax.length()-1);
		// Запись строковых значений в список
		list.add(strKommis);
		list.add(strKommisMin);
		list.add(strKommisMax);
		
		return list;
	}
	
	/**
	 * Получаем количество валют
	 * 
	 * @return
	 */
	private String[] getValList() {
		
		String[] list;

		list = tmpRate.getVal_point().split("/");
		
		return list;
	}
}