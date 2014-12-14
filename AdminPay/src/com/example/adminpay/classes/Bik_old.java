package com.example.adminpay.classes;

public class Bik_old {
	
	private int bik;
	private String swift;
	private String bank;
	//private boolean form_nal;
	//private boolean form_beznal;
	private String nal;
	private String beznal;
	private String inn;
	private String korr_schet;
	
	public String getInn() {
		
		if (inn == null) {
			return "";
		} else {
			return inn;
		}		
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getKorr_schet() {
		if (korr_schet == null) {
			return "";
		} else {
			return korr_schet;
		}		
	}

	public void setKorr_schet(String korr_schet) {
		this.korr_schet = korr_schet;
	}	
	
	public String getNal() {
			
		return nal;
	}

	public void setNal(String nal) {
		
		this.nal = nal;
	}

	public String getBeznal() {
				
		return beznal;
	}

	public void setBeznal(String beznal) {
		this.beznal = beznal;
	}

	public int getBik() {
		return bik;
	}
	
	public void setBik(int bik) {
		this.bik = bik;
	}
	
	public String getSwift() {
		if ((swift == null) || (swift == "")) {
			return "";
		} else {
			return swift;
		}		
	}
	
	public void setSwift(String swift) {
		this.swift = swift;
	}
	
	public String getBank() {
		if ((bank == null) || (bank == "")) {
			return "";
		} else {
			return bank;
		}		
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
}
