package com.example.clientpay.classes;

import java.util.Date;

/**
 * @author Makarov Roman
 * @version 0.1 alfa 12.08.2014
 *
 * Класс содержащий данные заявки клиента
 * на вывод денежных средств - безнал
 */
public class ZayvkaCard {

	public ZayvkaCard() {
		
		id = 0;
		wmid = "";
		date = null;
		status = "";
		fioClient = "";		
		valuta = "";
		mail = "";
		payOut = "";
		payIn = "";
	}
	/**
	 * Идентификатор клиента
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
	 * ФИО клиента
	 */
	private String fioClient;
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
	private int numberPay;
	/**
	 * Валюта платежа
	 */
	private String valuta;
	
	/**
	 * Mail клиента (идентификатор клиента)
	 */
	private String mail;
	
	public String getPayOut() {
		return payOut;
	}

	public void setPayOut(String payOut) {
		this.payOut = payOut;
	}

	public String getPayIn() {
		return payIn;
	}

	public void setPayIn(String payIn) {
		this.payIn = payIn;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
	
	public String getFioClient() {
		return fioClient;
	}
	
	public void setFioClient(String fioClient) {
		this.fioClient = fioClient;
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
	
	public double getKommis() {
		return kommis;
	}
	
	public void setKommis(double kommis) {
		this.kommis = kommis;
	}
	
	public int getNumberPay() {
		return numberPay;
	}
	
	public void setNumberPay(int numberPay) {
		this.numberPay = numberPay;
	}
	
	public String getValuta() {
		return valuta;
	}
	
	public void setValuta(String valuta) {
		this.valuta = valuta;
	}
	
}
