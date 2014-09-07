package com.example.clientpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class ZayvkiDB {
	
	private final Connection conn;
	
	private final String SQL_ZAYVKI_ALL = "SELECT * FROM zayvki WHERE status <>'Исполнено'";
	private final String SQL_ZAYVKI_ARSHIVE = "SELECT * FROM zayvki WHERE status = 'Исполнено'";	
	/* Запись заявки на безналичный расчет */
	private final String SQL_INSERT_CARD = "INSERT INTO zayvki(payOut, date,"
			+ "kommis, numberPay, status, summaCard, summaPay, payIn,"
			+ "valuta, wmid, mail, telephone, numSchet, fName, lName, otch, type) "
			+ "VALUES(:out, :data, :kom, :num, :status, :sumC, :sumP, :in, "
			+ ":val, :wmid, :mail, :tele, :schet, :fName, :lName, :otch, :type)";
	/* Редактирование заявки на безналичный расчет */
	private final String SQL_UPDATE_CARD = "UPDATE zayvki SET"
			+ " payOut = :bank, date = :date, kommis = :kommis, numberPay = :nPay,"
			+ " status = :status, summaCard = :sCard, summaPay = :sPay, payIn = :type,"
			+ " valuta = :val, wmid = :wmid, telephone=:tele, numSchet=:schet WHERE id = :id";

	
	public ZayvkiDB(Connection conn) {
		this.conn = conn;
	}
	
	/* Выбирает из таблицы заявок, те
	 * заявки, которые не были исполнены 
	 * Класс - Zayvki
	 */
	public List<Zayvki> getAllZayvki() {
	     List<Zayvki> result = new ArrayList<Zayvki>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ALL)
	    		   .executeAndFetch(Zayvki.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/* Выбирает из таблицы заявок, те
	 * заявки, которые были исполнены 
	 * Класс - Zayvki
	 */
	public List<Zayvki> getArshiveZayvki() {
	     List<Zayvki> result = new ArrayList<Zayvki>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ARSHIVE)
	    		   .executeAndFetch(Zayvki.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/* Редактирование заявки в БД */
	public void UpdateZayvka(Zayvki zayvka) {
		
		if (zayvka.getType().equals("На карту")) {
    		UpdateZayvkaCard(zayvka);
    	}
	}
	
	/* Редактирование заявки вывода средств на карту */
    private void UpdateZayvkaCard(Zayvki zayvka) {
    	
    	try {
    	    this.conn.createQuery(SQL_UPDATE_CARD)
    	        .addParameter("id", zayvka.getId())
    	        .addParameter("payOut", zayvka.getPayOut())
    	        .addParameter("date", zayvka.getDate())    	      
    	        .addParameter("kommis", zayvka.getKommis())
    	        .addParameter("nPay", zayvka.getNumberPay())
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sCard", zayvka.getSummaCard())
    	        .addParameter("sPay", zayvka.getSummaPay())
    	        .addParameter("payIn", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())    
    	        .addParameter("schet", zayvka.getNumSchet())
    	        .addParameter("tele", zayvka.getTelephone())
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
	 /* Добавление новой заявки в базу данных */
    public void WriteZayvka(Zayvki zayvka) {
    	
    	if (zayvka.getType().equals("На карту")) {
    		WriteZayvkaCard(zayvka);
    	}    	
    }
    
    /* Добавление заявки на безналичный расчет */
    private void WriteZayvkaCard(Zayvki zayvka) {
    	
    	try {
    	    this.conn.createQuery(SQL_INSERT_CARD)   
    	        .addParameter("out", zayvka.getPayOut())
    	        .addParameter("data", zayvka.getDate())    	       
    	        .addParameter("kom", zayvka.getKommis())
    	        .addParameter("num", zayvka.getNumberPay())
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sumC", zayvka.getSummaCard())
    	        .addParameter("sumP", zayvka.getSummaPay())
    	        .addParameter("in", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())
    	        .addParameter("mail", zayvka.getMail())
    	        .addParameter("tele", zayvka.getTelephone())
    	        .addParameter("schet", zayvka.getNumSchet())
    	        .addParameter("fName", zayvka.getfName())
    	        .addParameter("lName", zayvka.getlName())
    	        .addParameter("otch", zayvka.getOtch())
    	        .addParameter("type", zayvka.getType())
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /* Обновление статуса заявок 
     * @param status - новый статус, который присваивается заявке
     * @param listId - список идентификаторов заявок, у которых
     * необходимо изменить статус
     */
    public void UpdateZayvkaStatus(List<Integer> listId, String status) {
    	
		String list = "";
		
		for (int i = 0; i < listId.size(); i++) {
			
			if (i == listId.size() - 1) {
				list = list + String.valueOf(listId.get(i));
			} else {
				list = list + String.valueOf(listId.get(i)) + ",";
			}			
		}
	
		String sql = "UPDATE zayvki SET status = :status WHERE id in ("+list+")";
	
    	try {
    	    this.conn.createQuery(sql)
    	   		.addParameter("status", status)    	        	        
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
