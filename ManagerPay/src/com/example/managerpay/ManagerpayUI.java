package com.example.managerpay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.managerpay.support.Auth;
import com.example.managerpay.classes.DB;
import com.example.managerpay.classes.Handling;
import com.example.managerpay.classes.HandlingDB;
import com.example.managerpay.classes.ZayvkaCard;
import com.example.managerpay.classes.Zayvki;
import com.example.managerpay.classes.ZayvkiDB;
import com.example.managerpay.window.WindowExportFileCard;
import com.example.managerpay.window.WindowHistoryClient;
import com.example.managerpay.window.WindowZayvkaBindObr;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.UIEvents;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PasswordField;
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
@Theme("managertheme")
@SuppressWarnings("serial")
public class ManagerpayUI extends UI {
	
	private VerticalLayout mainLayout;
	private MenuBar mainMenu = null;         // основное меню программы
	private Table zayvkiCardTable = null;    // таблица для отображения заявок безнал
	private Table zayvkiArshiveTable = null; // таблица для хранения архивных данных
	private Table zayvkiNalTable = null;     // таблица для отображения заявок наличные
	private Table handlingTable = null;
	// Определяет текущую отображаемую таблицу
	private Table current; 
	private Refresher refresher = null;
	
	private MenuItem zayvki   = null;  // Заявки
	private MenuItem money   = null;   // Электронные деньги
	private MenuItem tech  = null;     // Техническая поддержка	
	private MenuItem search  = null;     // Техническая поддержка
	
	private GridLayout setLayout = null;
	private GridLayout setArshiveLayout = null;
	private GridLayout searchLayout = null;
	
	private PopupDateField data1 = null;
	private PopupDateField data2 = null; 
	private PopupDateField dataArshive1 = null;
	private PopupDateField dataArshive2 = null; 
	private TextField fioClient = null;
	private TextField fioArshiveClient = null;
	private ComboBox statusBox = null;
	private ComboBox operationBox = null;
	private ComboBox raschetBox = null;
	private ComboBox typeOperBox = null;   		  // Тип операции - фильтр
	private ComboBox typePayBox = null;   		  // Тип оплаты - фильтр
	private TextField numZayvki = null;
	private TextField numSearchZayvki = null;
	private TextField numZayvkiFiltr = null;
	private Button btnFilterAdd = null;
	private Button btnFilterCancel = null;
	private Button btnFilterArshiveAdd = null;
	private Button btnFilterArshiveCancel = null;
	private Button btnSearchAdd = null;
	private Button btnSearchHide = null;
	private Button btnUpdate = null;
	private Label lbLastId = null;
	
	// Панель авторизации пользователя
	private GridLayout gl = null;
	private PasswordField passPf = null;
	private TextField userName = null;
	
	//private BeanItemContainer<ZayvkaCard> beansZayvka = new BeanItemContainer<ZayvkaCard>(ZayvkaCard.class);
	private BeanItem<Zayvki> tmpZayvka = null;
	private BeanItem<Handling> tmpHandling = null;
	Filter filterClientNow = null;
	
	private BeanItemContainer<Zayvki> beansZayvka = new BeanItemContainer<Zayvki>(Zayvki.class);
	private BeanItemContainer<Zayvki> beansArshive = new BeanItemContainer<Zayvki>(Zayvki.class);
	private BeanItemContainer<Handling> beansHandling = new BeanItemContainer<Handling>(Handling.class);
	
	private String state;	
	private List<Integer> masIdZayvki =  new ArrayList<Integer>();
	private boolean archive = false; // Изначально заявки не архивные
	private int lastLoadId = 0;      // Идентификатор последней загруженной заявки
	private int timeRefresh = 0;
	
	private Object[] tableBeznalFields = new Object[] {"numberPay", "wmid", "typeOper", "typeRaschet",
			"date", "payOut", "payIn", "valuta", "summaPay", "summaCard", "kommis",
			"status","mail", "telephone"};
	/* Названия полей для отображения*/
	private String[] tableBeznalFieldsTitle = new String[] {"Номер заявки","Кошелек", "Тип операции", "Тип расчета",
			"Дата", "Банк", "Платежная система", "Валюта", "Списано", "Зачислено", "Комиссия",
			"Статус", "Mail", "Телефон"};
	
	private Object[] tableArshiveFields = new Object[] {"numberPay", "wmid", "typeOper", "typeRaschet",
			"date", "payOut", "payIn", "valuta", "summaPay", "kommis", "summaCard",
			"status", "mail", "telephone"};
	/* Названия полей для отображения*/
	private String[] tableArshiveFieldsTitle = new String[] {"Номер заявки","Кошелек", "Тип операции", "Тип расчета",
			"Дата", "Банк", "Платежная система", "Валюта", "Списание","Комиссия", "Зачисление", 
			"Статус", "Mail", "Телефон"};
	/* Поля для таблицы обращения */
	private Object[] handlingFields = new Object[] {"idZayvki", "idZayvki_rod", 
			"date", "status", "komment", "idClient"};
	/* Названия полей для отображения*/
	private String[] handlingFieldsTitle = new String[] {"Номер заявки","Родительская заявка", 
			"Дата", "Статус", "Комметарии", "Клиент"};
	
	
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
		
		//setPollInterval(3000);
        addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
            	// Автоматическое обновление таблиц через 18 секунд
            	timeRefresh ++;
            	if (timeRefresh > 4) {
            		
            		timeRefresh = 0;
            		updateZayvkiTable(false, false);
            	} 
            }
        });
	}
	
	/**
	 * Создание основного слоя приложеняи
	 */
	private void buildMainContent() {
		
		buildAuthForm();
		
		/*buildMainMenu();
		buildFilterPanelMain();
		buildFilterArshivePanel();
		//setLayout.setVisible(false);
		setArshiveLayout.setVisible(false);
		
		updateZayvkiTable(true, false);
		// Установка текущей таблицы
		current = zayvkiCardTable;
		buildHandlingTable();
		//buildRefresher();
		mainLayout.addComponent(zayvkiCardTable);*/
	}
		
	@SuppressWarnings("unused")
	private void buildMainMenu() {
		
		mainMenu = new MenuBar();
		
		MenuBar.Command myCommand = new MenuBar.Command() {
			
    		private Window win;		
			
			@Override
    		public void menuSelected(MenuItem selectedItem) {
				state = selectedItem.getText();
				
				if (state.equals("Текущие заявки")) {		
					archive = false;
					updateZayvkiTable(false, false);
					setCurrentLayout(zayvkiCardTable);
					setArshiveLayout.setVisible(false);
					setLayout.setVisible(true);
				}
				
				if (state.equals("Архив")) {
					archive = true;
					buildArshiveTable();
					setCurrentLayout(zayvkiArshiveTable);
					setLayout.setVisible(false);
					setArshiveLayout.setVisible(true);
				}		
				
				/*if (state.equals("Наличные")) {
					archive = false;
					//updateZayvkiTable(false, false);
					setCurrentLayout(zayvkiCardTable);
					setArshiveLayout.setVisible(false);
					setLayout.setVisible(true);
				}*/
				/* Вызов панели поиска по
				 * номеру заявки 
				 */
				if (state.equals("Поиск")) {
					// Если панель видна скрываем ее
					if (searchLayout.isVisible()) {
						searchLayout.setVisible(false);
						search.setChecked(false);
					} else {
						searchLayout.setVisible(true);
						search.setChecked(true);
					}					
				}
				
				if (state.equals("Обращения")) {
					
					setArshiveLayout.setVisible(false);
					setLayout.setVisible(false);
					setCurrentLayout(handlingTable);
					/*mainLayout.removeComponent(zayvkiCardTable);
					mainLayout.addComponent(handlingTable);*/
				}	
			}
			
		};
		zayvki = mainMenu.addItem("Заявки", null, null);
		//money = mainMenu.addItem("Платежные системы", null, null);
		tech = mainMenu.addItem("Тех поддержка", null, null);
		
		// Формирование меню Заявки
		zayvki.addItem("Текущие заявки", myCommand);
		//zayvki.addItem("Наличные", myCommand);
		zayvki.addItem("Архив", myCommand);
		//search = zayvki.addItem("Поиск", myCommand);
			
		// Формирование меню Техническая поддержка
		tech.addItem("Обращения", myCommand);
		
		mainLayout.addComponent(mainMenu);		
	}
	
	/**
	 * @param one    - определяет необходимо ли удаление
	 * существующих записей из таблицы
	 * true - необходима очистка записей
	 * @param search - определяет выполняется ли поиск
	 * по номеру платежа
	 */
	private void updateZayvkiTable(boolean one, boolean search) {
		
		double summa_on = 0;    // Общая сумма зачисления
		double summa_off = 0;   // Общая сумма списания
		double summa_kom = 0;   // Общая сумма комиссий
		
		if (!one) {
			zayvkiCardTable.removeAllItems();
		}	
		
		ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
		
		try {			
			List<Zayvki> list = new ArrayList<Zayvki>();
			list = zayvkiCard.getAllZayvki();
			// Поиск заявки по введенному номеру
			/*if (search) {
				
			} else {
				if (archive) {
							
					list = zayvkiCard.getArshiveZayvki();
				} else {
						
					list = zayvkiCard.getAllZayvki();
				}
			}*/	
			for (int i = 0; i < list.size(); i++) {				
				beansZayvka.addBean(list.get(i));
				// Расчет сумм
				summa_on = summa_on + list.get(i).getSummaCard();
				summa_off = summa_off + list.get(i).getSummaPay();
				summa_kom = summa_kom + list.get(i).getKommis();
			}			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}				
		
		zayvkiCardTable = new Table("",beansZayvka);
		zayvkiCardTable.setWidth("100%");
		zayvkiCardTable.setHeight("700px");
		zayvkiCardTable.setSelectable(true);
		zayvkiCardTable.setMultiSelect(true);
		
		zayvkiCardTable.addGeneratedColumn("Action", new ColumnGenerator() { // or instead of "Action" you can add ""
		    @Override
		    public Object generateCell(final Table source, final Object itemId, Object columnId) {
		        
		    	CheckBox ch = new CheckBox();
		        return ch;
		    }
		});
		
		zayvkiCardTable.setVisibleColumns(tableBeznalFields);		
		for (int i = 0; i < tableBeznalFields.length; i++) {
			zayvkiCardTable.setColumnHeader(tableBeznalFields[i], tableBeznalFieldsTitle[i]);
    	}
		
		// Настройка подвала таблицы
		zayvkiCardTable.setFooterVisible(true);
		zayvkiCardTable.setColumnFooter("numberPay", "ИТОГО"); 
		zayvkiCardTable.setColumnFooter("summaPay", String.valueOf(summa_on)); // Списано
		zayvkiCardTable.setColumnFooter("summaCard", String.valueOf(summa_off)); // Зачислено
		zayvkiCardTable.setColumnFooter("kommis", String.valueOf(summa_kom)); // Комиссия
		
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
					tmpZayvka = beansZayvka.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new WindowZayvkiCard(tmpZayvka, archive);					
					UI.getCurrent().addWindow(win);
				} else if (event.getButtonName().equals("left")) {
					
					if (event.isCtrlKey()) {
						// Созадние списка идентификаторов заявки
						tmpZayvka = beansZayvka.getItem(event.getItemId());
						masIdZayvki.add(tmpZayvka.getBean().getId());
					} else {
						masIdZayvki.clear();
						tmpZayvka = beansZayvka.getItem(event.getItemId());
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
						/*MessageBox.showPlain(Icon.INFO, "Фомрирование файла", 
								"Номера заявок "+masIdZayvki.toString(), ButtonId.OK);*/
						Window win = new WindowExportFileCard(masIdZayvki);
						UI.getCurrent().addWindow(win);
						
						masIdZayvki.clear();
						// Обновление таблицы заявок
						updateZayvkiTable(false, false);
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
						updateZayvkiTable(false, false);
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
						updateZayvkiTable(false, false);
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
	
	
	private void buildArshiveTable() {
		
			double summa_on = 0;    // Общая сумма зачисления
			double summa_off = 0;   // Общая сумма списания
			double summa_kom = 0;   // Общая сумма комиссий
			
			if (zayvkiCardTable != null) {
				zayvkiCardTable.removeAllItems();
			}
			
			ZayvkiDB zayvkiArshive = new ZayvkiDB(DB.getConnection());
			
			try {			
				List<Zayvki> list = new ArrayList<Zayvki>();
				list = zayvkiArshive.getArshiveZayvki();
				
				// Поиск заявки по введенному номеру				
				for (int i = 0; i < list.size(); i++) {				
					beansArshive.addBean(list.get(i));
					// Расчет сумм
					summa_on = summa_on + list.get(i).getSummaCard();
					summa_off = summa_off + list.get(i).getSummaPay();
					summa_kom = summa_kom + list.get(i).getKommis();
				}			
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}				
			
			zayvkiArshiveTable = new Table("",beansArshive);
			zayvkiArshiveTable.setWidth("100%");
			zayvkiArshiveTable.setHeight("700px");
			zayvkiArshiveTable.setSelectable(true);
			zayvkiArshiveTable.setMultiSelect(true);
			
			/*zayvkiCardTable.addGeneratedColumn("Action", new ColumnGenerator() { 
			    @Override
			    public Object generateCell(final Table source, final Object itemId, Object columnId) {
			        
			    	CheckBox ch = new CheckBox();
			        return ch;
			    }
			});*/
			
			zayvkiArshiveTable.setVisibleColumns(tableArshiveFields);		
			for (int i = 0; i < tableArshiveFields.length; i++) {
				zayvkiArshiveTable.setColumnHeader(tableArshiveFields[i], tableArshiveFieldsTitle[i]);
	    	}
			
			// Настройка подвала таблицы
			zayvkiArshiveTable.setFooterVisible(true);
			zayvkiArshiveTable.setColumnFooter("numberPay", "ИТОГО"); 
			zayvkiArshiveTable.setColumnFooter("summaPay", String.valueOf(summa_on)); // Списано
			zayvkiArshiveTable.setColumnFooter("summaCard", String.valueOf(summa_off)); // Зачислено
			zayvkiArshiveTable.setColumnFooter("kommis", String.valueOf(summa_kom)); // Комиссия
	}
	
	/* Создание таблицы обращений */
	private void buildHandlingTable() {
		
		//handlingTable.removeAllItems();
		
		HandlingDB hanling = new HandlingDB();
		
		try {			
			List<Handling> list = new ArrayList<Handling>();
			// Поиск заявки по введенному номеру
			list = hanling.getAllHandling(DB.getConnection());
				
			for (int i = 0; i < list.size(); i++) {				
				beansHandling.addBean(list.get(i));				
			}			
		} catch (Exception e) {
			
			e.printStackTrace();
		}				
		
		handlingTable = new Table("",beansHandling);
		handlingTable.setWidth("100%");
		handlingTable.setHeight("700px");
		handlingTable.setSelectable(true);
		handlingTable.setMultiSelect(true);
		
		handlingTable.setVisibleColumns(handlingFields);		
		for (int i = 0; i < handlingFields.length; i++) {
			handlingTable.setColumnHeader(handlingFields[i], handlingFieldsTitle[i]);
    	}	
		
		/* Обработка клика по строке таблицы */
		handlingTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				
				/* Двойной клик левой кнопкой мыши по записи в таблице
				 * Происходит открытие формы редактирования заявки 
				 */				
				if (event.isDoubleClick()) {
					
					// Очистка списка идентификаторов					
					tmpHandling = beansHandling.getItem(event.getItemId());
					// Создание окна редактирования заявки
					Window win = new WindowHandlingActive(tmpHandling.getBean());					
					UI.getCurrent().addWindow(win);
				} 			
			}
		});
	}	
	
	/**
	 * Формирование панели для фильтрации заявок 
	 * для архива
	 */
	private void buildFilterArshivePanel() {
		
		/* Создание сетки */
		setArshiveLayout = new GridLayout(6, 5);
		setArshiveLayout.setWidth("100%");	
		//setLayout.addStyleName("myslot");
		
		/* Дата создания заявки - С */
		Label dataLabel1 = new Label("Дата с ");	
		dataLabel1.setWidth(null);
		setArshiveLayout.addComponent(dataLabel1, 0, 0);	
		dataArshive1 = new PopupDateField();		
		dataArshive1.setDateFormat("dd.MM.yyyy");
		dataArshive1.setValue(new Date());
		dataArshive1.setImmediate(true);
		dataArshive1.setWidth("240px");			
		setArshiveLayout.addComponent(dataArshive1, 1, 0);
		
		/* Дата создания заявки - ПО */
		Label dataLabel2 = new Label("Дата по ");	
		dataLabel2.setWidth(null);
		setArshiveLayout.addComponent(dataLabel2, 2, 0);	
		dataArshive2 = new PopupDateField();		
		dataArshive2.setDateFormat("dd.MM.yyyy");
		dataArshive2.setValue(new Date());
		dataArshive2.setImmediate(true);
		dataArshive2.setWidth("240px");			
		setArshiveLayout.addComponent(dataArshive2, 3, 0);
		
		//ФИО клиента 
		Label fioArshiveLabel = new Label("ФИО клиента");
		fioArshiveLabel.setWidth(null);
		setArshiveLayout.addComponent(fioArshiveLabel, 0, 1);
		fioArshiveClient = new TextField();
		fioArshiveClient.setWidth("240px");
		fioArshiveClient.setImmediate(true);	
		setArshiveLayout.addComponent(fioArshiveClient, 1, 1);		
		
		/* Тип операции */
		Label operationLabel = new Label("Тип операции");
		operationLabel.setWidth(null);
		setArshiveLayout.addComponent(operationLabel, 2, 1);
		operationBox = new ComboBox();
		operationBox.addItem("ВВОД");
		operationBox.addItem("ВЫВОД");				
		operationBox.setWidth("240px");	
		operationBox.setImmediate(true);
		setArshiveLayout.addComponent(operationBox, 3, 1);
		
		/* Тип операции */
		Label raschetLabel = new Label("Тип оплаты");
		raschetLabel.setWidth(null);
		setArshiveLayout.addComponent(raschetLabel, 0, 2);
		raschetBox = new ComboBox();
		raschetBox.addItem("НАЛИЧНЫЙ");
		raschetBox.addItem("БЕЗНАЛИЧНЫЙ");				
		raschetBox.setWidth("240px");	
		raschetBox.setImmediate(true);
		setArshiveLayout.addComponent(raschetBox, 1, 2);
					
		buildBtnFilterArshivePanel();
		
		mainLayout.addComponent(setArshiveLayout);
	}
	
	/**
	 * Формирование панели для фильтрации заявок
	 */
	private void buildFilterPanelMain() {
		
		/* Создание сетки */
		setLayout = new GridLayout(6, 5);
		setLayout.setWidth("100%");	
		//setLayout.addStyleName("myslot");
		
		/* Дата создания заявки - С */
		Label dataLabel1 = new Label("Дата с ");	
		dataLabel1.setWidth(null);
		setLayout.addComponent(dataLabel1, 0, 0);	
		data1 = new PopupDateField();		
		data1.setDateFormat("dd.MM.yyyy");
		data1.setValue(new Date());
		data1.setImmediate(true);
		data1.setWidth("240px");			
		setLayout.addComponent(data1, 1, 0);
		
		/* Дата создания заявки - ПО */
		Label dataLabel2 = new Label("Дата по ");	
		dataLabel2.setWidth(null);
		setLayout.addComponent(dataLabel2, 2, 0);	
		data2 = new PopupDateField();		
		data2.setDateFormat("dd.MM.yyyy");
		data2.setValue(new Date());
		data2.setImmediate(true);
		data2.setWidth("240px");			
		setLayout.addComponent(data2, 3, 0);
		
		/* ФИО клиента */
		Label fioLabel = new Label("ФИО клиента");
		fioLabel.setWidth(null);
		setLayout.addComponent(fioLabel, 0, 1);
		fioClient = new TextField();
		fioClient.setWidth("240px");
		fioClient.setImmediate(true);	
		setLayout.addComponent(fioClient, 1, 1);
		
		/* Номер заявки */
		Label numberLabel = new Label("Номер заявки");
		numberLabel.setWidth(null);
		setLayout.addComponent(numberLabel, 2, 1);
		numZayvkiFiltr = new TextField();
		numZayvkiFiltr.setWidth("240px");
		numZayvkiFiltr.setImmediate(true);	
		setLayout.addComponent(numZayvkiFiltr, 3, 1);
		
		/* Тип операции */
		Label stateLabel = new Label("Тип операции");
		stateLabel.setWidth(null);
		setLayout.addComponent(stateLabel, 0, 2);
		typeOperBox = new ComboBox();
		typeOperBox.addItem("Ввод");
		typeOperBox.addItem("Вывод");		
		typeOperBox.setWidth("240px");	
		typeOperBox.setImmediate(true);
		setLayout.addComponent(typeOperBox, 1, 2);
		
		/* Тип операции */
		Label payLabel = new Label("Тип оплаты");
		payLabel.setWidth(null);
		setLayout.addComponent(payLabel, 2, 2);
		typePayBox = new ComboBox();
		typePayBox.addItem("Списано");
		typePayBox.addItem("Зачислено");		
		typePayBox.setWidth("240px");	
		typePayBox.setImmediate(true);
		setLayout.addComponent(typePayBox, 3, 2);
					
		buildBtnFilterPanel();
		
		mainLayout.addComponent(setLayout);
	}
	
	/* Формирование панели фильтрации */
	private void buildBtnFilterPanel() {
		 
		btnFilterAdd = new Button("Фильтр");
		setLayout.addComponent(btnFilterAdd, 0, 3);
		
		btnFilterAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
					
				applyFilterTable();
			}
		});		
		
		/* Создание кнопки Очистить */
		btnFilterCancel = new Button("Очистить");
		setLayout.addComponent(btnFilterCancel, 1, 3);
		// Сброс условий фильтрации
		btnFilterCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				// Удаление всех фильтров
				beansZayvka.removeAllContainerFilters();
			}
		});
		
		//buildPanelUpdate();
		
	}
	
	/* Формирование панели фильтрации 
	 * для архивных заявок
	 * */
	private void buildBtnFilterArshivePanel() {
		 
		btnFilterArshiveAdd = new Button("Фильтр");
		setArshiveLayout.addComponent(btnFilterArshiveAdd, 0, 3);
		
		btnFilterArshiveAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
					
				applyFilterArchiveTable();
			}
		});		
		
		/* Создание кнопки Очистить */
		btnFilterArshiveCancel = new Button("Очистить");
		setArshiveLayout.addComponent(btnFilterArshiveCancel, 1, 3);
		// Сброс условий фильтрации
		btnFilterArshiveCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				// Удаление всех фильтров
				//beansZayvka.removeAllContainerFilters();
			}
		});		
		
	}
	
	/* Формирование панели поиска по номеру заявки */
	private void buildSearchPanel() {

		searchLayout = new GridLayout(4,1);
		searchLayout.addStyleName("search-grid");
		/* Поиск по номеру заявки */
		Label searchLabel = new Label("Поиск");
		searchLabel.setWidth(null);
		searchLabel.addStyleName("search-grid-label");
		searchLayout.addComponent(searchLabel, 0, 0);
		
		/* Создание поля ввода для номера заявки */
		numSearchZayvki = new TextField();
		numSearchZayvki.setWidth("240px");
		numSearchZayvki.setImmediate(true);
		numSearchZayvki.addStyleName("search-grid-edit");
		searchLayout.addComponent(numSearchZayvki, 1, 0);
		
		/* Создание кнопки Поиск */
		btnSearchAdd = new Button("Поиск");
		searchLayout.addComponent(btnSearchAdd, 2, 0);
		
		/* Создание кнопки Скрыть */
		btnSearchHide = new Button("Скрыть");
		btnSearchHide.addStyleName("search-grid-btn-hide");
		searchLayout.addComponent(btnSearchHide, 3, 0);
		
		btnSearchHide.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				searchLayout.setVisible(false);
			}
		});
		
		mainLayout.addComponent(searchLayout);
		// Скрытие панели поиска по номеру заявки
		searchLayout.setVisible(false);
	}
	
	/* Формирование панели, содержащую информацию
	 * о поступивщих заявках и кнопке обновить 
	 */
	private void buildPanelUpdate() {
		
		/* Создание кнопки Обновить */
		btnUpdate = new Button("Обновить");
		// Обновление таблицы заявок
		btnUpdate.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				updateZayvkiTable(false, false);
			}
		});
		setLayout.addComponent(btnUpdate, 4, 0);		
		// Вывод информации
		lbLastId = new Label("");
		setLayout.addComponent(lbLastId, 4, 1);
	}
	
	/* Создание и применение фильтрации к таблице */
	private void applyFilterTable() {
		
		// Сброс все установленных фильтров
		beansZayvka.removeAllContainerFilters();
		String strTypeOper = (String) (typeOperBox.getValue() == null ? "" : typeOperBox.getValue());
		String strTypeRaschet = (String) (typePayBox.getValue() == null ? "" : typePayBox.getValue());
		String strNumZayvki = (String) (numZayvkiFiltr.getValue() == null ? "" : numZayvkiFiltr.getValue());
		String strClient = (String) (fioClient.getValue() == null ? "" : fioClient.getValue());
		//String strDate = (String) (data.getValue() == null ? "" : data.getValue());
		
		/* Проверяем что поле Тип операции */
		if (!strTypeOper.equals("")) {
			// Фильтр по статусу
			Filter filterOper = new SimpleStringFilter("typeOper", strTypeOper,
					true, false);
			beansZayvka.addContainerFilter(filterOper);
		} 
		
		/* Проверяем что поле Тип расчета */
		if (!strTypeRaschet.equals("")) {
			// Фильтр по статусу
			Filter filterRaschet = new SimpleStringFilter("typeRaschet", strTypeRaschet,
					true, false);
			beansZayvka.addContainerFilter(filterRaschet);
		}
		
		/* Проверяем что поле Номер заявки */
		if (!strNumZayvki.equals("")) {
			// Фильтр по статусу
			Filter filterNum = new SimpleStringFilter("numberPay", strNumZayvki,
					true, false);
			beansZayvka.addContainerFilter(filterNum);
		}
		
		/*if (!strClient.equals("")) {
			// Фильтр по ФИО клиента
			Filter filterClient = new SimpleStringFilter("fioClient", strClient,
					true, false);
			beansZayvka.addContainerFilter(filterClient);
		}*/
				
	}
	
	
	/* Создание и применение фильтрации к таблице */
	private void applyFilterArchiveTable() {
		
		// Сброс все установленных фильтров
		beansZayvka.removeAllContainerFilters();
		String strTypeOper = (String) (operationBox.getValue() == null ? "" : operationBox.getValue());
		String strTypeRaschet = (String) (raschetBox.getValue() == null ? "" : raschetBox.getValue());
		//String strNumZayvki = (String) (numZayvkiFiltr.getValue() == null ? "" : numZayvkiFiltr.getValue());		
		
		/* Проверяем что поле Тип операции */
		if (!strTypeOper.equals("")) {
			// Фильтр по статусу
			Filter filterOper = new SimpleStringFilter("typeOper", strTypeOper,
					true, false);
			beansZayvka.addContainerFilter(filterOper);
		} 
		
		/* Проверяем что поле Тип расчета */
		if (!strTypeRaschet.equals("")) {
			// Фильтр по статусу
			Filter filterRaschet = new SimpleStringFilter("typeRaschet", strTypeRaschet,
					true, false);
			beansZayvka.addContainerFilter(filterRaschet);
		}
				
	}
	
	/* Функция для установки текущего слоя */
	private void setCurrentLayout(Table newTable) {
		
		/* Проверяем, что устанавливаемая таблица
		 * не явдяется текущей 
		 */
		if (newTable != current) {
			mainLayout.removeComponent(current);
			mainLayout.addComponent(newTable);
			current = newTable;
		}
		
		/* Управление видимостью панели поиска и
		 * панели фильтрации 
		 */
		if (newTable != zayvkiCardTable) {
			setLayout.setVisible(false);
			//searchLayout.setVisible(false);
		} else {
			setLayout.setVisible(true);
			//searchLayout.setVisible(true);
		}
	}
	
	/**
	 * Формирование формы авторизации
	 */
	private void buildAuthForm() {
		
		/* Создание сетки для формы авторизации */
		gl = null; 
		Label userLb = null; //Пользователь
		//Поле для отображения логина пользователя		
		Label passLb = null; //Введите пароль
		Label depLb = null;
		
		Button auBt = null; // кнопка авторизации
		Button exBt = null; // кнопка выхода
		
		/* Создание полей для ввод алогина и пароля */
		userLb = new Label("Пользователь", Label.CONTENT_XHTML);		
		userName = new TextField();
		
		passLb = new Label("Пароль", Label.CONTENT_XHTML);

		/* Поле для ввода пароля */
		passPf = new PasswordField();		
		passPf.setImmediate(true);
		passPf.setRequired(true);
		passPf.setRequiredError("Введите пароль.");
		
		/* Создание кнопки Вход */
		auBt = new Button("Вход");		
		auBt.setImmediate(true);		

		/* Создание кнопки Отмена */
		exBt = new Button("Отмена");
		exBt.setImmediate(true);		
		
		/* Создание сетки */
		gl = new GridLayout(3, 4);
		gl.setSpacing(true);
		
		/* Добавление поля для отображения логина пол-ля */ 
		gl.addComponent(userLb, 0, 0);
		gl.addComponent(userName, 1, 0, 2, 0);
		
		/* Добавление поля для ввода пароля */ 
		gl.addComponent(passLb, 0, 1);
		gl.addComponent(passPf, 1, 1, 2, 1);
		
		/* Выравниваем элементы по левому краю */
		gl.setComponentAlignment(userLb, Alignment.MIDDLE_LEFT);
		gl.setComponentAlignment(userName, Alignment.MIDDLE_LEFT);		
		gl.setComponentAlignment(passLb, Alignment.MIDDLE_LEFT);
		gl.setComponentAlignment(passPf, Alignment.MIDDLE_LEFT);
		
		/* Добавляем кнопки */
		gl.addComponent(auBt, 1, 3);
		gl.addComponent(exBt, 2, 3);
		
		/* Обработчик нажатия кнопки ВХОД */
		auBt.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {		
							
				try {
					goAuth();
				} catch (Exception e) {					
					e.printStackTrace();
				}
			}
		});		
				
		mainLayout.addComponent(gl);
	}	
	
	/**
	 * Функция авторизации
	 * @throws Exception
	 */
	private void goAuth() throws Exception {
		
		String login = userName.getValue().toString();
		String pass = passPf.getValue().toString();
		
		// Проверяем что поля логин и пароль заполнены
		if(login.equals("") || pass.equals("")) {
		//if (false) {
			MessageBox.showPlain(Icon.ERROR, "Авторизация", 
					"Неверно введ логин пользователя или пароль 1", ButtonId.OK);
		} else {
		
			if (Auth.authUser(login, pass)) {
			//if (true) {
				mainLayout.removeComponent(gl);				
				buildMainMenu();
				buildFilterPanelMain();
				buildFilterArshivePanel();
				//setLayout.setVisible(false);
				setArshiveLayout.setVisible(false);
				/* Создание таблицы заявок */
				updateZayvkiTable(true, false);
				// Установка текущей таблицы
				current = zayvkiCardTable;
				buildHandlingTable();
				setPollInterval(5000);
				mainLayout.addComponent(zayvkiCardTable);			
			
			} else {
				MessageBox.showPlain(Icon.ERROR, "Авторизация", 
					"Неверно введ логин пользователя или пароль 2", ButtonId.OK);
				userName.setValue("");
				passPf.setValue("");
			}
		}	
	}
		
}