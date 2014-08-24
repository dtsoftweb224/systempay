package com.example.managerpay.classes;

import java.util.Date;

public class ZayvkiClientHist {
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
	private Date data;	
	/**
	 * Банк (платежная система), куда осуществляется
	 * перевод денежных средств
	 */
	private String payOut;
	/**
	 * ФИО клиента
	 */
	private String fio;
	/**
	 * Сумма, которая будет списана со счета
	 * клиента в платежной системе
	 */
	private double summa;
	/**
	 * Платежная система, которая является источником
	 * денежных средств
	 */
	private String payIn;	
	/**
	 * Номер платежа
	 */
	private int id_platezha;	
	/**
	 * Mail клиента (идентификатор клиента)
	 */
	private String mail;	
	/**
	 * Номер телефона клиента
	 */
	private String telephone;
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getPayOut() {
		return payOut;
	}
	
	public void setPayOut(String payOut) {
		this.payOut = payOut;
	}
	
	public String getFio() {
		return fio;
	}
	
	public void setFio(String fio) {
		this.fio = fio;
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
	
	public int getId_platezha() {
		return id_platezha;
	}
	
	public void setId_platezha(int id_platezha) {
		this.id_platezha = id_platezha;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
}
