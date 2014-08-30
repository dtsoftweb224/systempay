package com.example.adminpay.classes;

public class Bik {
	
	private int bik;
	private String swift;
	private String bank;
	private boolean form_nal;
	private boolean form_beznal;
	
	public int getBik() {
		return bik;
	}
	
	public void setBik(int bik) {
		this.bik = bik;
	}
	
	public String getSwift() {
		return swift;
	}
	
	public void setSwift(String swift) {
		this.swift = swift;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public boolean isForm_nal() {
		return form_nal;
	}
	
	public void setForm_nal(boolean form_nal) {
		this.form_nal = form_nal;
	}
	
	public boolean isForm_beznal() {
		return form_beznal;
	}
	
	public void setForm_beznal(boolean form_beznal) {
		this.form_beznal = form_beznal;
	}

}
