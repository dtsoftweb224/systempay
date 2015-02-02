package com.example.classes;

import java.util.Date;
import java.util.HashMap;

public class Zayvki {
	
	public Zayvki() {
		
		id = 0;
		wmid = "";
		date = null;
		status = "";		
		valuta = "";
		mail = "";
		payOut = "";
		payIn = "";
	}

	/**
	 * �?дентификатор клиента
	 */
	private int id;
	/**
	 * Номер кошелька клиента (wmid)
	 */
	private String wmid;
	/**
	 * Дата формирования заявки
	 */
	private Date date;
	/**
	 * Статус заявки
	 */
	private String status;
	/**
	 * Банк (платежная система), куда осуществляется
	 * перевод денежных средств
	 */
	private String payOut;
	/**
	 * Сумма, которая будет списана со счета
	 * клиента в платежной системе
	 */
	private double summaPay;
	/**
	 * Сумма, которая будет зачислена на счет клиента в банке
	 */
	private double summaCard;
	/**
	 * Платежная система, которая является источником
	 * денежных средств
	 */
	private String payIn;
	/**
	 * Комиссия на перевод
	 */
	private double kommis;
	/**
	 * Номер платежа
	 */
	private String numberPay;
	/**
	 * Валюта платежа
	 */
	private String valuta;
	
	/**
	 * Mail клиента (идентификатор клиента)
	 */
	private String mail;
	/**
	 * Ф�?О клиента
	 */
	private String fio;	
	/**
	 * Дата рождения
	 */
	private Date date_born; 
	/**
	 * Серия паспорта
	 */
	private int pSerial;
	/**
	 * Номер паспорта
	 */
	private int pNumber;
	/**
	 * Код подразделения
	 */
	private int pKod;
	/**
	 * Дата выдачи паспорта
	 */
	private Date pDate;
	/**
	 * Номер счета клиента
	 */
	private String numSchet;
	/**
	 * Номер телефона клиента
	 */
	private String telephone;
	/**
	 * Тип операции - Ввод, Вывод
	 */
	private String typeOper;	
	/**
	 * Тип оплаты - Списано, Начислено
	 */
	private String typeRaschet;
	
		
	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}	

	public String getTypeOper() {
		return typeOper;
	}

	public void setTypeOper(String typeOper) {
		this.typeOper = typeOper;
	}

	public String getTypeRaschet() {
		return typeRaschet;
	}

	public void setTypeRaschet(String typeRaschet) {
		this.typeRaschet = typeRaschet;
	}

	public String getNumSchet() {
		return numSchet;
	}

	public void setNumSchet(String numSchet) {
		this.numSchet = numSchet;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getWmid() {
		return wmid;
	}
	
	public void setWmid(String wmid) {
		this.wmid = wmid;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPayOut() {
		return payOut;
	}
	
	public void setPayOut(String payOut) {
		this.payOut = payOut;
	}
	
	public double getSummaPay() {
		return summaPay;
	}
	
	public void setSummaPay(double summaPay) {
		this.summaPay = summaPay;
	}
	
	public double getSummaCard() {
		return summaCard;
	}
	
	public void setSummaCard(double summaCard) {
		this.summaCard = summaCard;
	}
	
	public String getPayIn() {
		return payIn;
	}
	
	public void setPayIn(String payIn) {
		this.payIn = payIn;
	}
	
	public double getKommis() {
		return kommis;
	}
	
	public void setKommis(double kommis) {
		this.kommis = kommis;
	}
	
	public String getNumberPay() {
		return numberPay;
	}
	
	public void setNumberPay(String numberPay) {
		this.numberPay = numberPay;
	}
	
	public String getValuta() {
		return valuta;
	}
	
	public void setValuta(String valuta) {
		this.valuta = valuta;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Date getDate_born() {
		return date_born;
	}
	
	public void setDate_born(Date date_born) {
		this.date_born = date_born;
	}
	
	public int getpSerial() {
		return pSerial;
	}
	
	public void setpSerial(int pSerial) {
		this.pSerial = pSerial;
	}
	
	public int getpNumber() {
		return pNumber;
	}
	
	public void setpNumber(int pNumber) {
		this.pNumber = pNumber;
	}
	
	public int getpKod() {
		return pKod;
	}
	
	public void setpKod(int pKod) {
		this.pKod = pKod;
	}
	
	public Date getpDate() {
		return pDate;
	}
	
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	
	/* Преобразование значений класса в HashMap */
	public HashMap<String, Object> getHash() {
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("wmid", wmid);
		res.put("status", status);
		res.put("mail", mail);
		res.put("payOut", payOut);
		res.put("telephone", telephone);
		res.put("numSchet", numSchet);
		res.put("type", typeOper);
		res.put("fio", fio);
				
		return res;
	}
}

