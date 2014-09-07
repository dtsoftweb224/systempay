package com.example.adminpay.classes;

import java.util.Date;

public class SMS {

	/**
	 * Идентификатор SMS
	 */
	private int id_sms;
	/**
	 * Дата отправки SMS
	 */
	private Date data;
	/**
	 * Номер телефона, на который отправлено SMS
	 */
	private String telephone;
	/**
	 * Статус сообщения
	 */
	private String status;
	/**
	 * Номер платежа
	 */
	private String numPay;
	
	public int getId_sms() {
		return id_sms;
	}
	
	public void setId_sms(int id_sms) {
		this.id_sms = id_sms;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getNumPay() {
		return numPay;
	}
	
	public void setNumPay(String numPay) {
		this.numPay = numPay;
	}
}
