package com.example.managerpay.classes;

import java.util.Date;

/**
 * Класс описывающий обращения
 * @author Makarov Roman
 *
 */
public class Handling {
	
	private int id;
	/**
	 * Идентификатор создаваемой заявки
	 */
	private int idZayvki;
	/**
	 * Идентификатор родительской заявки
	 */
	private int idZayvki_rod;
	/**
	 * Дата регистрации обращения
	 */
	private Date date;
	/**
	 * Статус обращения
	 */
	private String status;
	/**
	 * Комментарии к обращению
	 */
	private String komment;
	/**
	 * Идентификтаор клиента,
	 * создавшего обращение
	 */
	private int idClient;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdZayvki() {
		return idZayvki;
	}
	
	public void setIdZayvki(int idZayvki) {
		this.idZayvki = idZayvki;
	}
	
	public int getIdZayvki_rod() {
		return idZayvki_rod;
	}
	
	public void setIdZayvki_rod(int idZayvki_rod) {
		this.idZayvki_rod = idZayvki_rod;
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
	
	public String getKomment() {
		return komment;
	}
	public void setKomment(String komment) {
		this.komment = komment;
	}
	
	public int getIdClient() {
		return idClient;
	}
	
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
}
