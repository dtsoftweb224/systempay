package com.example.managerpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.*;

import com.vaadin.data.util.BeanItem;


public class ZayvkiCardDB {

	private final Connection conn;
	
	private final String SQL_ZAYVKI_ALL = "SELECT * FROM zayvkiCard WHERE status <>'Исполнено'";
	private final String SQL_ZAYVKI_ARSHIVE = "SELECT * FROM zayvkiCard WHERE status = 'Исполнено'";	
	private final String SQL_ZAYVKI_UPDATE = "UPDATE zayvkiCard SET"
			+ " payOut = :bank, date = :date, fioClient = :fio,"
			+ " kommis = :kommis, numberPay = :nPay, status = :status,"
			+ " summaCard = :sCard, summaPay = :sPay, payIn = :type,"
			+ " valuta = :val, wmid = :wmid WHERE id = :id";
	/*private final String SQL_ZAYVKI_STATUS_UP = "UPDATE zayvkiCard "
			+ "SET status = :status WHERE id = :id";*/
	
	
	public ZayvkiCardDB(Connection conn) {
		this.conn = conn;
	}
	
	/* Выбирает из таблицы заявок, те
	 * заявки, которые не были исполнены 
	 */
	public List<ZayvkaCard> getAllZayvki() {
	     List<ZayvkaCard> result = new ArrayList<ZayvkaCard>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ALL)
	    		   .executeAndFetch(ZayvkaCard.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/* Выбирает из таблицы заявок, те
	 * заявки, которые были исполнены 
	 */
	public List<ZayvkaCard> getArshiveZayvki() {
	     List<ZayvkaCard> result = new ArrayList<ZayvkaCard>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ARSHIVE)
	    		   .executeAndFetch(ZayvkaCard.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	
	/* Редактирование заявки в БД */
    public void UpdateZayvkaDB(ZayvkaCard zayvka) {
    	
    	try {
    	    this.conn.createQuery(SQL_ZAYVKI_UPDATE)
    	        .addParameter("id", zayvka.getId())
    	        .addParameter("payOut", zayvka.getPayOut())
    	        .addParameter("date", zayvka.getDate())
    	        .addParameter("fio", zayvka.getFioClient())
    	        .addParameter("kommis", zayvka.getKommis())
    	        .addParameter("nPay", zayvka.getNumberPay())
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sCard", zayvka.getSummaCard())
    	        .addParameter("sPay", zayvka.getSummaPay())
    	        .addParameter("payIn", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())
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
	
		String sql = "UPDATE zayvkiCard SET status = :status WHERE id in ("+list+")";
	
    	try {
    	    this.conn.createQuery(sql)
    	   		.addParameter("status", status)    	        	        
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
