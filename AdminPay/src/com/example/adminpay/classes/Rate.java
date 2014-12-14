package com.example.adminpay.classes;

public class Rate {
	
	private int id;
	private String operation;
	private String type_paschet;
	private String pay_system;
	private String point;
	private String val_point;
	private String min_kom;
	private String max_kom;
	private boolean state;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getType_paschet() {
		return type_paschet;
	}
	
	public void setType_paschet(String type_paschet) {
		this.type_paschet = type_paschet;
	}
	
	public String getPay_system() {
		return pay_system;
	}

	public void setPay_system(String pay_system) {
		this.pay_system = pay_system;
	}

	public String getPoint() {
		return point;
	}
	
	public void setPoint(String point) {
		this.point = point;
	}
	
	public String getVal_point() {
		return val_point;
	}
	
	public void setVal_point(String val_point) {
		this.val_point = val_point;
	}
	
	public String getMin_kom() {
		return min_kom;
	}
	
	public void setMin_kom(String min_kom) {
		this.min_kom = min_kom;
	}
	
	public String getMax_kom() {
		return max_kom;
	}
	
	public void setMax_kom(String max_kom) {
		this.max_kom = max_kom;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
}
