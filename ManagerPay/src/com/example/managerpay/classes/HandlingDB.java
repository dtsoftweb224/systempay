package com.example.managerpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class HandlingDB {
	
	private final String SQL_HANDILG_ALL = "SELECT * FROM haldings";
	private final String SQL_INSERT_HANDILG = "INSERT INTO haldings(idZayvki, "
			+ "idZayvki_rod, date, status, komment, idClient) "
			+ "VALUES(:idZayvki, :idZayvki_rod, :date, :status, :kommenr, :client)";
	
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

}
