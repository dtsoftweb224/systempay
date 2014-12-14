package com.example.adminpay.classes;

import java.util.Date;

public class RegZayvki {
	
	/**
	 * Идентификатор регистрации
	 */
	private int id;
	/**
	 * Идентификатор заявки
	 */
	private String id_zayvki;
	/**
	 * Дата регистрации заявки
	 */
	private Date data;
	/**
	 * Сумма заявки
	 */
	private double summa;
	/**
	 * Источник платежа
	 */
	private String payIn;
	/**
	 * Система для зачисления платежа
	 */
	private String payOut;
	/**
	 * ФИО клиента
	 */
	private String fioClient;
	/**
	 * Адрес электронной почты клиента
	 */
	private String mailClient;
	/**
	 * Статус заявки
	 */
	private String status;
	/**
	 * IP адрес клиента
	 */
	private String ip_adress;
	
	
	public String getIp_adress() {
		return ip_adress;
	}

	public void setIp_adress(String ip_adress) {
		this.ip_adress = ip_adress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getId_zayvki() {
		return id_zayvki;
	}
	
	public void setId_zayvki(String id_zayvki) {
		this.id_zayvki = id_zayvki;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public double getSumma() {
		return summa;
	}
	
	public void setSumma(double summa) {
		this.summa = summa;
	}
	
	public String getPayIn() {
		return payIn;
	}
	
	public void setPayIn(String payIn) {
		this.payIn = payIn;
	}
	
	public String getPayOut() {
		return payOut;
	}
	
	public void setPayOut(String payOut) {
		this.payOut = payOut;
	}
	
	public String getFioClient() {
		return fioClient;
	}
	
	public void setFioClient(String fioClient) {
		this.fioClient = fioClient;
	}
	
	public String getMailClient() {
		return mailClient;
	}
	
	public void setMailClient(String mailClient) {
		this.mailClient = mailClient;
	}

}
