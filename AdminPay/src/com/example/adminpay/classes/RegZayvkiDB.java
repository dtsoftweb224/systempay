package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

public class RegZayvkiDB {

	private final Connection conn;
	
	private final String SQL_ZAYVKI_ALL = "SELECT * FROM registry_zayvki";
	
	public RegZayvkiDB(Connection conn) {
		this.conn = conn;
	}
	
	public List<RegZayvki> getAllZayvki() {
	     List<RegZayvki> result = new ArrayList<RegZayvki>();
	     try
	     {
	       result = conn.createQuery(SQL_ZAYVKI_ALL)
	    		   .executeAndFetch(RegZayvki.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
}
