package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

public class BaseRW {

	
	/**
	 * Получение биков из БД
	 * @param conn 
	 * @return список объектов класса БИК
	 */
	public static List<Bik> getAllBik(Connection conn) {
	     
		List<Bik> result = new ArrayList<Bik>();
		String sql = "SELECT * FROM bik";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(Bik.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	/**
	 * Получение всех клиентов из БД
	 * @param conn
	 * @return список объектов класса Client
	 */
	public static List<Client> getAllClient(Connection conn) {
	     
		List<Client> result = new ArrayList<Client>();
		String sql = "SELECT * FROM client";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(Client.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
}
