package com.example.classes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Kommis {
	
	private String pay;
	private String valuta;
	private double summaIn;
	private double summaOut;
	private double kommis;
	private double min_kom;
	
	public double getMin_kom() {
		return min_kom;
	}

	public void setMin_kom(double min_kom) {
		this.min_kom = min_kom;
	}

	public String getPay() {
		return pay;
	}
	
	public void setPay(String pay) {
		this.pay = pay;
	}

	public double getSummaIn() {
		return summaIn;
	}

	public void setSummaIn(double summaIn) {
		summaIn = new BigDecimal(summaIn).setScale(2, RoundingMode.UP).doubleValue();
		this.summaIn = summaIn;
	}

	public double getSummaOut() {
		return summaOut;
	}

	public void setSummaOut(double summaOut) {
		summaOut = new BigDecimal(summaOut).setScale(2, RoundingMode.UP).doubleValue();
		this.summaOut = summaOut;
	}

	public double getKommis() {
		return kommis;
	}

	public void setKommis(double kommis) {
		kommis = new BigDecimal(kommis).setScale(2, RoundingMode.UP).doubleValue();
		this.kommis = kommis;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}
	
	
}
