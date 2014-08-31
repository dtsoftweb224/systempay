package com.example.clientpay.classes;

/**
 * @author Makarov Roman
 * Класс для расчета процентов
 */
public class CalcProcent {
	
	private double summaIn;
	private double summaOut;
	private double kommis;
	private String typeSystem;
	private String valuta;
	
	private double MIN_KOMMIS;
	private double MAX_KOMMIS;
	private double PROCENT_KOMMIS;
	private double EKA_KOMMIS;
	
	public CalcProcent(String system, String valuta, double summa) {
		
		this.summaIn = summa;
		this.typeSystem = system;
		this.valuta = valuta;
		this.MIN_KOMMIS = 0.01;
		this.MAX_KOMMIS = 50;
		this.EKA_KOMMIS = 3.3;
		this.PROCENT_KOMMIS = 0.8;
	}
	
	
	/**
	 * @param typeCalc - определяет тип расчета
	 * если true  - расчет процентов осуществляется от введденой суммы и вычитается из нее
	 * если false - расчет процентов осуществляется к введденой сумме
	 * @return
	 */
	public double calculation(boolean typeCalc) {
		
		double res = 0;
		
		// Загрузка минимальных и максимальных порогов из БД
		
		// Если валюта USD, то перевод в рубли по курсу ЦБ
		
		if (typeCalc) {
			res = calcSumIn();
		} else {
			res = calcSumOut();
		}
		
		return res;
	}
	
	/**
	 * @return
	 * Расчет процентов осуществляется от введденой суммы
	 */
	private double calcSumIn() {
		
		double res = 0;
		
		this.kommis = this.summaIn * EKA_KOMMIS;
		// Проверка комиссии на минимальные и максимальные значения
		
		this.summaOut = this.summaIn - this.kommis;
		res = this.summaOut;
		
		return res;
	}
	
	/**
	 * @return
	 * Расчет процентов осуществляется к введденой сумме
	 */
	private double calcSumOut() {
		
		double res = 0;
		
		this.kommis = this.summaIn * EKA_KOMMIS;
		// Проверка комиссии на минимальные и максимальные значения
		
		this.summaOut = this.summaIn + this.kommis;
		res = this.summaOut;
		
		return res;
	}

}
