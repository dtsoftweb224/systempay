package com.example.adminpay.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Valuta {

	private Date data;
	private String numCode;
	private String charCode;
	private int nominal;
	private String naz;
	private double val;
	
	public String getNaz() {
		return naz;
	}

	public void setNaz(String naz) {
		this.naz = naz;
	}

	public String getData() {
		
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		
		return formatDate.format(data);
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNumCode() {
		return numCode;
	}
	
	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}
	
	public String getCharCode() {
		return charCode;
	}
	
	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}
	
	public int getNominal() {
		return nominal;
	}
	
	public void setNominal(int nominal) {
		this.nominal = nominal;
	}	
	
	public double getVal() {
		return val;
	}
	
	public void setVal(double val) {
		this.val = val;
	}	
}

