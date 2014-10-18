package com.example.managerpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class HandlingDB {
	
	private final String SQL_HANDILG_ALL = "SELECT * FROM handlings WHERE status <>'Закрыто'";
	private final String SQL_INSERT_HANDILG = "INSERT INTO handlings(idZayvki, "
			+ "idZayvki_rod, date, status, komment, idClient) "
			+ "VALUES(:idZayvki, :idZayvki_rod, :date, :status, :kommenr, :client)";
	private String SQL_ZAYVKA_ID = "SELECT * FROM handling_zayvki WHERE id = ";
	
	/* Выбирает из таблицы обращения 
	 * Класс - Handling
	 */
	public List<Handling> getAllHandling(Connection conn) {
	     
		List<Handling> result = new ArrayList<Handling>();
	     try
	     {
	       result = conn.createQuery(SQL_HANDILG_ALL)
	    		   .executeAndFetch(Handling.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/* Добавление нового обращения */
    @SuppressWarnings("unused")
	private void WriteHandling(Handling handl, Connection conn) {
    	
    	try {
    	    conn.createQuery(SQL_INSERT_HANDILG)   
    	        .addParameter("idZayvki", handl.getIdZayvki())
    	        .addParameter("idZayvki_rod", handl.getIdZayvki_rod())    	       
    	        .addParameter("date", handl.getDate())
    	        .addParameter("status", handl.getStatus())
    	        .addParameter("komment", handl.getKomment())
    	        .addParameter("client", handl.getIdClient())   	      
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public Zayvki getZayvkaID(Connection conn, Integer idZayvka) {
	    
		SQL_ZAYVKA_ID = SQL_ZAYVKA_ID + String.valueOf(idZayvka);
		List<Zayvki> result = new ArrayList<Zayvki>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKA_ID)
	    		   .executeAndFetch(Zayvki.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result.get(0);
	}
    
    /**
     * Обновление статуса обращения
     * 
     * @param conn     - connection
     * @param handling - экземпляр обращения
     * @param status   - присваиваемый статус
     */
    public void UpdateHandlingStatus(Connection conn, Handling handling, String status) {
    	
		
		String sql = "UPDATE handlings SET status = :status WHERE id = " + handling.getId();
	
    	try {
    		conn.createQuery(sql)
    	   		.addParameter("status", status)    	        	        
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * Обновление комментариев к обращению
     * 
     * @param conn
     * @param handling
     * @param komment
     */
    public void UpdateHandlingKomment(Connection conn, Handling handling, String komment) {    	
		
		String sql = "UPDATE handlings SET komment = :komment WHERE id = " + handling.getId();
	
    	try {
    		conn.createQuery(sql)
    	   		.addParameter("komment", komment)    	        	        
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
