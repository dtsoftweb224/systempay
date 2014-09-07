package com.example.managerpay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.ZayvkaCard;
import com.example.managerpay.classes.Zayvki;
import com.example.managerpay.classes.ZayvkiCardDB;
import com.example.managerpay.classes.ZayvkiDB;
import com.example.managerpay.window.WindowHistoryClient;
import com.example.managerpay.window.WindowZayvkaBindObr;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

import org.sql2o.Connection;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class ManagerpayUI extends UI {
	
	private VerticalLayout mainLayout;
	private MenuBar mainMenu = null;   //основное меню программы
	private Table zayvkiCardTable = null;
	
	private MenuItem zayvki   = null;  // Заявки
	private MenuItem money   = null;   // Электронные деньги
	private MenuItem tech  = null;     // Техническая поддержка	
	
	private GridLayout setLayout = null;
	private PopupDateField data = null; 
	private TextField fioClient = null;
	private ComboBox statusBox = null;
	private TextField numZayvki = null;
	private TextField numSearchZayvki = null;
	private Button btnFilterAdd = null;
	private Button btnFilterCancel = null;
	private Button btnSearchAdd = null;
	
	private BeanItemContainer<ZayvkaCard> beansZayvka = new BeanItemContainer<ZayvkaCard>(ZayvkaCard.class);
	private BeanItem<Zayvki> tmpZayvka = null;
	
	private BeanItemContainer<Zayvki> beansZayvka1 = new BeanItemContainer<Zayvki>(Zayvki.class);
	
	private String state;	
	private List<Integer> masIdZayvki =  new ArrayList<Integer>();
	private boolean archive = false; 
	
	private String[] tableFields = new String[] {"id", "wmid", "date", "payOut",
			"fioClient", "payIn", "valuta", "summaPay", "kommis", "summaCard", 
			"status","mail","Action"};
	/* Названия полей для отображения*/
	private String[] tableFieldsTitle = new String[] {"Идентификатор","Кошелек", "Дата", "Банк",
			"ФИО клиента", "Платежная система", "Валюта", "Сумма списания", 
			"Комиссия", "Сумма зачисления", "Статус", "Mail", "1"};
	
	private String[] tableFields1 = new String[] {"numberPay", "wmid", "date", "payOut",
			"payIn", "valuta", "summaPay", "kommis", "summaCard", "numSchet",
			"status","mail", "telephone", "fName","lName", "otch", "Action"};
	/* Названия полей для отображения*/
	private String[] tableFieldsTitle1 = new String[] {"Номер заявки","Кошелек", "Дата", "Банк",
			"Платежная система", "Валюта", "Списание","Комиссия", "Зачисление", "Счет",
			"Статус", "Mail", "Телефон", "Имя", "Фамилия", "Отчество", "1"};
	
	private final Action ACTION_GEN_FILE = new Action("Сформировать файл");
	private final Action ACTION_HISTORY = new Action("История операций");
	private final Action ACTION_SUCCESSFUL = new Action("Выполнено");
	private final Action ACTION_EX_ARSHIVE = new Action("Извлечь из архива");
	private final Action[] ACTIONS_MARKED = new Action[] { ACTION_GEN_FILE, 
											ACTION_HISTORY, ACTION_SUCCESSFUL};
	/* Архив */
	private final Action[] ACTIONS_ARSHIVE = new Action[] { ACTION_EX_ARSHIVE};
	
	/* Инициализация приложения */
	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		setContent(mainLayout);		
		buildMainContent();		
	}
	
	private void buildMainContent() {
		
		buildMainMenu();
		buildFilterPanel();
		buildSearchPanel();
		//buildTableZayvkiCard();
		updateZayvkiTable(true);
		mainLayout.addComponent(zayvkiCardTable);
	}
	@SuppressWarnings("unused")
	private void buildMainMenu() {
		
		mainMenu = new MenuBar();
		
		MenuBar.Command myCommand = new MenuBar.Command() {
			
    		private Window win;		
			
			@Override
    		public void menuSelected(MenuItem selectedItem) {
				state = selectedItem.getText();
				if (state.equals("На карту")) {		
					archive = false;
					updateZayvkiTable(false);
				}
				
				if (state.equals("Архив")) {
					archive = true;
					updateZayvkiTable(false);
				}				
			}
			
		};
		zayvki = mainMenu.addItem("Заявки", null, null);
		money = mainMenu.addItem("Платежные системы", null, null);
		tech = mainMenu.addItem("Тех поддержка", null, null);
		
		// Формирование меню Заявки
		zayvki.addItem("На карту", myCommand);
		zayvki.addItem("Обналичивание", myCommand);
		zayvki.addItem("Архив", myCommand);
		
		// Формирование меню Электронные деньги
		money.addItem("Яндекс-деньги", myCommand);
		money.addItem("Webmoney", myCommand);
		
		// Формирование меню Техническая поддержка
		tech.addItem("Обращения", myCommand);
		
		mainLayout.addComponent(mainMenu);		
	}
	
	private void updateZayvkiTable(boolean one) {
		
		if (!one) {
			zayvkiCardTable.removeAllItems();
		}	
		
		ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
		
		try {			
			List<Zayvki> list = new ArrayList<Zayvki>(); 
			if (archive) {
				/* Заявки из архива */				
				list = zayvkiCard.getArshiveZayvki();
			} else {
				/* Обычные заявки */	
				list = zayvkiCard.getAllZayvki();
			}			
			for (int i = 0; i < list.size(); i++) {				
				beansZayvka1.addBean(list.get(i));				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}				
		
		zayvkiCardTable = new Table("",beansZayvka1);
		zayvkiCardTable.setWidth("100%");
		zayvkiCardTable.setHeight("100%");
		zayvkiCardTable.setSelectable(true);
		zayvkiCardTable.setMultiSelect(true);
		
		zayvkiCardTable.addGeneratedColumn("Action", new ColumnGenerator() { // or instead of "Action" you can add ""
		    @Override
		    public Object generateCell(final Table source, final Object itemId, Object columnId) {
		        
		    	CheckBox ch = new CheckBox();
		        return ch;
		    }
		});
		
		zayvkiCardTable.setVisibleColumns(tableFields1);		
		for (int i = 0; i < tableFields1.length; i++) {
			zayvkiCardTable.setColumnHeader(tableFields1[i], tableFieldsTitle1[i]);
    	}	
		
		/* Обработка клика по строке таблицы */
		zayvkiCardTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					// Очистка списка идентификаторов
					masIdZayvki.clear();
					tmpZayvka = beansZayvka1.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new WindowZayvkiCard(tmpZayvka);					
					UI.getCurrent().addWindow(win);
				} else if (event.getButtonName().equals("left")) {
					
					if (event.isCtrlKey()) {
						// Созадние списка идентификаторов заявки
						tmpZayvka = beansZayvka1.getItem(event.getItemId());
						masIdZayvki.add(tmpZayvka.getBean().getId());
					} else {
						masIdZayvki.clear();
						tmpZayvka = beansZayvka1.getItem(event.getItemId());
						masIdZayvki.add(tmpZayvka.getBean().getId());
					}					
					// Поиск дублируемых значений
				}
			
			}
		});
		
		/* При клике правой кнопкой мыши по таблице появляется контекстное меню */
		zayvkiCardTable.addActionHandler(new Action.Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				
				/* Формирование файлов для банков */
				if (ACTION_GEN_FILE == action) {
					
					if (masIdZayvki.size() > 0) {						
						// Изменение статуса заявок на "На рассмотрении"
						ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
						zayvkiCard.UpdateZayvkaStatus(masIdZayvki, "На рассмотрении");								
						MessageBox.showPlain(Icon.INFO, "Фомрирование файла", 
								"Номера заявок "+masIdZayvki.toString(), ButtonId.OK);
						masIdZayvki.clear();
						// Обновление таблицы заявок
						updateZayvkiTable(false);
					} else {
						MessageBox.showPlain(Icon.ERROR, "Изменение статуса", 
								"Не выбрано ни одной заявки", ButtonId.OK);
					}
				}
				
				/* Создание окна "История операций" */
				if (ACTION_HISTORY == action) {
					UI.getCurrent().addWindow(new WindowHistoryClient(tmpZayvka.getBean().getMail()));
				}
				
				/* Измнение статуса на "Выполнено" */
				if (ACTION_SUCCESSFUL == action) {		
					
					if (masIdZayvki.size() > 0) {
						ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
						zayvkiCard.UpdateZayvkaStatus(masIdZayvki, "Выполнено");
						masIdZayvki.clear();	
						// Обновление таблицы заявок
						updateZayvkiTable(false);
					} else {
						MessageBox.showPlain(Icon.ERROR, "Изменение статуса", 
								"Не выбрано ни одной заявки", ButtonId.OK);
					}
				}
				
				/* Извлечение заявки из архива - смена статуса на "На рассмотрении" */
				if (ACTION_EX_ARSHIVE == action) {
					if (masIdZayvki.size() > 0) {
						ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
						zayvkiCard.UpdateZayvkaStatus(masIdZayvki, "На рассмотрении");	
						masIdZayvki.clear();
						// Обновление таблицы заявок
						updateZayvkiTable(false);
						UI.getCurrent().addWindow(new WindowZayvkaBindObr(tmpZayvka.getBean().getNumberPay()));
					} else {
						MessageBox.showPlain(Icon.ERROR, "Изменение статуса", 
								"Не выбрано ни одной заявки", ButtonId.OK);
					}
				}
			}			
			@Override
			public Action[] getActions(Object target, Object sender) {
				
				if (archive == false) {
					return ACTIONS_MARKED;	
				} else {
					return ACTIONS_ARSHIVE;	
				}
				
								
			}
		});
	}
	
	private void buildFilterPanel() {
		
		/* Создание сетки */
		setLayout = new GridLayout(6, 5);
		setLayout.setWidth("100%");	
		//setLayout.addStyleName("myslot");
		
		/* Дата создания заявки */
		Label dataLabel = new Label("Дата");	
		dataLabel.setWidth(null);
		setLayout.addComponent(dataLabel, 0, 0);	
		data = new PopupDateField();		
		data.setDateFormat("yyyy-MM-dd");
		data.setValue(new Date());
		data.setImmediate(true);
		data.setWidth("240px");			
		setLayout.addComponent(data, 1, 0);
		
		/* ФИО клиента */
		Label fioLabel = new Label("ФИО клиента");
		fioLabel.setWidth(null);
		setLayout.addComponent(fioLabel, 2, 0);
		fioClient = new TextField();
		fioClient.setWidth("240px");
		fioClient.setImmediate(true);
		//fioClient.addStyleName("lpad10");
		setLayout.addComponent(fioClient, 3, 0);		
		
		/* Статус заявки */
		Label stateLabel = new Label("Статус заявки");
		stateLabel.setWidth(null);
		setLayout.addComponent(stateLabel, 2, 1);
		statusBox = new ComboBox();
		statusBox.addItem("Принято");
		statusBox.addItem("На рассмотрении");
		statusBox.addItem("Исполнено");
		statusBox.addItem("Возвращено");
		statusBox.setWidth("240px");	
		statusBox.setImmediate(true);
		setLayout.addComponent(statusBox, 3, 1);
		
		/* Номер заявки */
		Label numLabel = new Label("Номер заявки");
		numLabel.setWidth(null);
		setLayout.addComponent(numLabel, 0, 1);
		numZayvki = new TextField();
		numZayvki.setWidth("240px");
		numZayvki.setImmediate(true);
		setLayout.addComponent(numZayvki, 1, 1);	
		
		buildBtnFilterPanel();
		
		mainLayout.addComponent(setLayout);
	}
	
	/* Формирование панели фильтрации */
	private void buildBtnFilterPanel() {
		 
		btnFilterAdd = new Button("Фильтрация");
		setLayout.addComponent(btnFilterAdd, 0, 2);
		
		btnFilterAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
					
				beansZayvka.removeAllContainerFilters();
				String strStatus = (String) (statusBox.getValue() == null ? "" : statusBox.getValue());
				String strClient = (String) (fioClient.getValue() == null ? "" : fioClient.getValue());
				
				if (!strStatus.equals("")) {
					// Фильтр по статусу
					Filter filterStatus = new SimpleStringFilter("status", strStatus,
							true, false);
					beansZayvka.addContainerFilter(filterStatus);
				} /*else {
					beansZayvka.removeAllContainerFilters();
				}*/
				if (!strClient.equals("")) {
					// Фильтр по ФИО клиента
					Filter filterClient = new SimpleStringFilter("fioClient", strClient,
							true, false);
					beansZayvka.addContainerFilter(filterClient);
				}
			}
		});		
		
		btnFilterCancel = new Button("Очистить");
		setLayout.addComponent(btnFilterCancel, 1, 2);
		
		btnFilterCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				// Удаление всех фильтров
				beansZayvka.removeAllContainerFilters();
			}
		});
		
	}
	
	/* Формирование панели поиска по номеру заявки */
	private void buildSearchPanel() {

		/* Поиск по номеру заявки */
		Label searchLabel = new Label("Поиск");
		searchLabel.setWidth(null);
		setLayout.addComponent(searchLabel, 0, 3);
		
		numSearchZayvki = new TextField();
		numSearchZayvki.setWidth("240px");
		numSearchZayvki.setImmediate(true);
		setLayout.addComponent(numSearchZayvki, 1, 3);
		
		btnSearchAdd = new Button("Поиск");
		setLayout.addComponent(btnSearchAdd, 2, 3);
	}
}