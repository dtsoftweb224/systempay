package com.example.managerpay.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Roman Makarov
 * Класс создани файла для
 * осуществление денежного перевода с карты на карту 
 *
 */
public class CreateFileCard {
	
	private List<String> list = new ArrayList<String>();
	List<Zayvki> listZayvki = new ArrayList<Zayvki>();
	private Date dt = new Date();
	
	public CreateFileCard(List<Integer> idZayvki) {
		
		if (idZayvki.size() > 1) {
			// Создание выгрузки по нескольким заявкам 
			buildExportMulti(idZayvki);
		} else {
			// Создание выгрузки по одной заявке
			buildExportOne(idZayvki);
		}

	}
	
	/**
	 * Формирование выгрузки по нескольким заявкам 
	 * @param idZayvki - идентификаторы выгружаемых заявок
	 */
	private void buildExportMulti(List<Integer> idZayvki) {
		
	}
	
	/**
	 * Формирование выгрузки по одной заявке
	 * @param idZayvki - идентификатор выгружаемой заявки
	 */
	private void buildExportOne(List<Integer> idZayvki) {
		
		list.clear();
		
		generateHeadFile();
		addPayOrder();
		addPayerInfo();
		
		ZayvkiDB zayvki = new ZayvkiDB(DB.getConnection());
		listZayvki = (List<Zayvki>) zayvki.getZayvkiID(idZayvki.get(0));				
		addDataZayvka(listZayvki.get(0));
	}
	
	/**
	 * Формирование заголовка файла
	 */
	public void generateHeadFile() {
		
		list.add("1CClientBankExchange");
		list.add("ВерсияФормата=1.02");
		list.add("Кодировка=Windows");
		list.add("Отправитель=Бухгалтерия предприятия (базовая), редакция 3.0");
		list.add("Получатель=АРМ 'Клиент' АС 'Клиент-Сбербанк' Сбербанка России");
		// Получение текущей даты и времени		
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss");
		list.add("ДатаСоздания=" + formatDate.format(dt));
		list.add("ВремяСоздания=" + formatTime.format(dt));
		// Расчетный счет
		list.add("РасчСчет=40702810300261004318");
		
	}
	
	/**
	 * Информация о платежном поручении
	 */
	private void addPayOrder() {
		
		// Платежное поручение
		list.add("Документ=Платежное поручение");
		list.add("Документ=Платежное требование");
		list.add("СекцияДокумент=Платежное поручение");
		list.add("Номер=6");
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		list.add("Дата=" + formatDate.format(dt));		
	}
	
	/**
	 * Добавление реквизитов плательщика
	 */
	private void addPayerInfo() {
		
		list.add("ПлательщикСчет=40702810300261004318");
		list.add("Плательщик=ИНН 6679016315 ООО \"Ека Пэй\"");
		list.add("ПлательщикИНН=6679016315");
		list.add("Плательщик1=ООО \"Ека Пэй\"");
		list.add("ПлательщикРасчСчет=40702810300261004318");
		list.add("ПлательщикБанк1=Ф-Л ГПБ (ОАО) В Г. ЕКАТЕРИНБУРГЕ");
		list.add("ПлательщикБанк2=Г. ЕКАТЕРИНБУРГ");
		list.add("ПлательщикБИК=046568945");
		list.add("ПлательщикКорсчет=30101810800000000945");
	}
	
	/**
	 * Данные из заявки,
	 * которые необходимы для осуществления платежа
	 */
	private void addDataZayvka(Zayvki zayvka) {
		
		// Счет получателя
		list.add("ПолучательСчет=" + zayvka.getNumSchet());
		// ИНН получателя
		String fio = "";
		fio = zayvka.getFio();
		
		list.add("Получатель=ИНН " + fio);
		// ФИО получателя
		list.add("Получатель1=" + fio);		
		// Расчетный счет получателя
		list.add("ПолучательРасчСчет=" + zayvka.getNumSchet());
		// Получатель банк1
		list.add("Получатель1=" + fio);
		// Получатель банк2
		list.add("Получатель1=" + fio);
	}
	
	public List<String> getListFile() {
				
		return list;
	}

}
