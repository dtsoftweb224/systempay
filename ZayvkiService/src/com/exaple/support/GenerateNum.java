package com.exaple.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.classes.DBDop;

/**
 * Класс генерирования номера заявки
 * 
 * @author Roman Makarov
 *
 */
public class GenerateNum {
	
	/* Функция генерации номера платежа */
	public static String generateNumberPay() {
		
		String numPay = "";
		int num = 1;
		
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("MMddyy");
		numPay = df.format(now);
		
		//if (type.getValue().equals("На карту")) {
			numPay = numPay + "C";
			try {
				num = DBDop.GetNumIndexPay(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			numPay = numPay + String.valueOf(num);
		//}
		
		return numPay;
	}

}
