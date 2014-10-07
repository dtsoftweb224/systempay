package com.example.managerpay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
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
@Theme("managertheme")
@SuppressWarnings("serial")
public class ManagerpayUI extends UI {
	
	private VerticalLayout mainLayout;
	private MenuBar mainMenu = null;   // основное меню программы
	private Table zayvkiCardTable = null;
	private Table handlingTable = null;
	// Определяет текущую отображаемую таблицу
	private Table current; 
	private Refresher refresher = null;
	
	private MenuItem zayvki   = null;  // Заявки
	private MenuItem money   = null;   // Электронные деньги
	private MenuItem tech  = null;     // Техническая поддержка	
	
	private GridLayout setLayout = null;
	private GridLayout searchLayout = null;
	
	private PopupDateField data = null; 
	private TextField fioClient = null;
	private ComboBox statusBox = null;
	private TextField numZayvki = null;
	private TextField numSearchZayvki = null;
	private Button btnFilterAdd = null;
	private Button btnFilterCancel = null;
	private Button btnSearchAdd = null;
	private Button btnSearchHide = null;
	private Button btnUpdate = null;
	private Label lbLastId = null;
	
	private BeanItemContainer<ZayvkaCard> beansZayvka = new BeanItemContainer<ZayvkaCard>(ZayvkaCard.class);
	private BeanItem<Zayvki> tmpZayvka = null;
	Filter filterClientNow = null;
	
	private BeanItemContainer<Zayvki> beansZayvka1 = new BeanItemContainer<Zayvki>(Zayvki.class);
	private BeanItemContainer<Handling> beansHandling = new BeanItemContainer<Handling>(Handling.class);
	
	private String state;	
	private List<Integer> masIdZayvki =  new ArrayList<Integer>();
	private boolean archive = false; 
	private int lastLoadId = 0; // Идентификатор последней загруженной заявки
	
	private String[] tableFields = new String[] {"numberPay", "wmid", "date", "payOut",
			"payIn", "valuta", "summaPay", "kommis", "summaCard", "numSchet",
			"status","mail", "telephone", "fName","lName", "otch", "Action"};
	/* Названия полей для отображения*/
	private String[] tableFieldsTitle = new String[] {"Номер заявки","Кошелек", "Дата", "Банк",
			"Платежная система", "Валюта", "Списание","Комиссия", "Зачисление", "Счет",
			"Статус", "Mail", "Телефон", "Имя", "Фамилия", "Отчество", "1"};
	/* Поля для таблицы обращения */
	private String[] handlingFields = new String[] {"idZayvki", "idZayvki_rod", 
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
		
		refresher = new Refresher(); // создаем рефрешер	
		// Устанавливаем интервал работы рефрешера
		refresher.setRefreshInterval(500); 
		refresher.addListener(new RefreshListener() {
			
			@Override
			public void refresh(Refresher source) {
				
				MessageBox.showPlain(Icon.INFO, "Фомрирование файла", 
						"Номера заявок ", ButtonId.OK);
				lastLoadId = lastLoadId + 1;
				lbLastId.setValue(String.valueOf(lastLoadId));
			}
		});	
		
		addExtension(refresher);
	}
	
	private void buildMainContent() {
		
		buildMainMenu();
		buildFilterPanel();
		buildSearchPanel();
		/* Создание таблицы заявок */
		updateZayvkiTable(true, false);
		// Установка текущей таблицы
		current = zayvkiCardTable;
		buildHandlingTable();
		//buildRefresher();
		mainLayout.addComponent(zayvkiCardTable);
	}
	
	/* Создаем рефрешер для обновления таблиц сумок и пользователей */
	private void buildRefresher() {
		
		// создаем рефрешер и устанавливаем портлетные листенеры		
		
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
					updateZayvkiTable(false, false);
					setCurrentLayout(zayvkiCardTable);
				}
				
				if (state.equals("Архив")) {
					archive = true;
					updateZayvkiTable(false, false);
					setCurrentLayout(zayvkiCardTable);
				}				
				/* Вызов панели поиска по
				 * номеру заявки 
				 */
				if (state.equals("Поиск")) {
					// Если панель видна скрываем ее
					if (searchLayout.isVisible()) {
						searchLayout.setVisible(false);
					} else {
						searchLayout.setVisible(true);
					}					
				}
				
				if (state.equals("Обращения")) {
					
					setCurrentLayout(handlingTable);
					/*mainLayout.removeComponent(zayvkiCardTable);
					mainLayout.addComponent(handlingTable);*/
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
		zayvki.addItem("Поиск", myCommand);
		
		// Формирование меню Электронные деньги
		money.addItem("Яндекс-деньги", myCommand);
		money.addItem("Webmoney", myCommand);
		
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
		
		if (!one) {
			zayvkiCardTable.removeAllItems();
		}	
		
		ZayvkiDB zayvkiCard = new ZayvkiDB(DB.getConnection());
		
		try {			
			List<Zayvki> list = new ArrayList<Zayvki>();
			// Поиск заявки по введенному номеру
			if (search) {
				
			} else {
				if (archive) {
					/* Заявки из архива */				
					list = zayvkiCard.getArshiveZayvki();
				} else {
					/* Обычные заявки */	
					list = zayvkiCard.getAllZayvki();
				}
			}	
			for (int i = 0; i < list.size(); i++) {				
				beansZayvka1.addBean(list.get(i));				
			}
			// Запись id последней добавленной заявки
			
			lbLastId.setCaption(list.get(list.size()-1).getNumberPay());
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
		
		zayvkiCardTable.setVisibleColumns(tableFields);		
		for (int i = 0; i < tableFields.length; i++) {
			zayvkiCardTable.setColumnHeader(tableFields[i], tableFieldsTitle[i]);
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
		handlingTable.setHeight("100%");
		handlingTable.setSelectable(true);
		handlingTable.setMultiSelect(true);
		
		handlingTable.setVisibleColumns(handlingFields);		
		for (int i = 0; i < handlingFields.length; i++) {
			handlingTable.setColumnHeader(handlingFields[i], handlingFieldsTitle[i]);
    	}	
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
		setLayout.addComponent(stateLabel, 0, 1);
		statusBox = new ComboBox();
		statusBox.addItem("Принято");
		statusBox.addItem("На рассмотрении");
		statusBox.addItem("Выполнено");
		statusBox.addItem("Возвращено");
		statusBox.setWidth("240px");	
		statusBox.setImmediate(true);
		setLayout.addComponent(statusBox, 1, 1);
					
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
					
				applyFilterTable();
			}
		});		
		
		/* Создание кнопки Очистить */
		btnFilterCancel = new Button("Очистить");
		setLayout.addComponent(btnFilterCancel, 1, 2);
		// Сброс условий фильтрации
		btnFilterCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				// Удаление всех фильтров
				beansZayvka.removeAllContainerFilters();
			}
		});
		
		buildPanelUpdate();
		
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
		setLayout.addComponent(btnUpdate, 4, 0);
		// Вывод информации
		lbLastId = new Label("");
		setLayout.addComponent(lbLastId, 4, 1);
	}
	
	/* Создание и применение фильтрации к таблице */
	private void applyFilterTable() {
		
		// Сброс все установленных фильтров
		beansZayvka.removeAllContainerFilters();
		String strStatus = (String) (statusBox.getValue() == null ? "" : statusBox.getValue());
		String strClient = (String) (fioClient.getValue() == null ? "" : fioClient.getValue());
		
		/* Проверяем что поле статус не пустое */
		if (!strStatus.equals("")) {
			// Фильтр по статусу
			Filter filterStatus = new SimpleStringFilter("status", strStatus,
					true, false);
			beansZayvka.addContainerFilter(filterStatus);
		} 
		
		if (!strClient.equals("")) {
			// Фильтр по ФИО клиента
			Filter filterClient = new SimpleStringFilter("fioClient", strClient,
					true, false);
			beansZayvka.addContainerFilter(filterClient);
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
			searchLayout.setVisible(false);
		} else {
			setLayout.setVisible(true);
			searchLayout.setVisible(true);
		}
	}
}