package com.example.adminpay.classes;

public class ClientUr {
	
	private int id;
	private String ogrn;
	private String inn;
	private String kpp;
	private String fioDirector;
	private String mail;
	private String telephone;
	private String address;
	
	public ClientUr() {
		
		this.ogrn = "";
		this.inn = "";
		this.address = "";
		this.fioDirector = "";
		this.mail = "";
		this.telephone = "";
		this.kpp = "";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOgrn() {
		return ogrn;
	}
	
	public void setOgrn(String ogrn) {
		this.ogrn = ogrn;
	}
	
	public String getInn() {
		return inn;
	}
	
	public void setInn(String inn) {
		this.inn = inn;
	}
	
	public String getKpp() {
		return kpp;
	}
	
	public void setKpp(String kpp) {
		this.kpp = kpp;
	}
	
	public String getFioDirector() {
		return fioDirector;
	}
	
	public void setFioDirector(String fioDirector) {
		this.fioDirector = fioDirector;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

}

