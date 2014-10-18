package com.example.adminpay.classes;

public class Kommis {

	private int id;
	private String paySystem;
	private double komSystem;
	private double kommis;
	private int minKommis;
	private int maxKommis;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPaySystem() {
		return paySystem;
	}
	
	public void setPaySystem(String paySystem) {
		this.paySystem = paySystem;
	}
	
	public double getKomSystem() {
		return komSystem;
	}
	
	public void setKomSystem(double komSystem) {
		this.komSystem = komSystem;
	}
	
	public double getKommis() {
		return kommis;
	}
	
	public void setKommis(double kommis) {
		this.kommis = kommis;
	}
	
	public int getMinKommis() {
		return minKommis;
	}
	
	public void setMinKommis(int minKommis) {
		this.minKommis = minKommis;
	}
	
	public int getMaxKommis() {
		return maxKommis;
	}
	
	public void setMaxKommis(int maxKommis) {
		this.maxKommis = maxKommis;
	}
	
}
