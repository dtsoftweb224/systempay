package com.example.managerpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class ZayvkiClientHistDB {
	
	private final Connection conn;
	
	private final String SQL_ZAYVKI_ALL = "SELECT * FROM client_history"
											+ " WHERE mail = :mail";
	
	public ZayvkiClientHistDB(Connection conn) {
		this.conn = conn;
	}
	
	/* 
	 */
	public List<ZayvkiClientHist> getAllZayvki(String mail) {
	     List<ZayvkiClientHist> result = new ArrayList<ZayvkiClientHist>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ALL)
	    		   .addParameter("mail", mail)
	    		   .executeAndFetch(ZayvkiClientHist.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}

}
