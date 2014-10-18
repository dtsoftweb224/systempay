package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class ZayvkiDB {

	// Запрос на получение заявок клиента
	private String SQL_ZAYVKI_CLIENT = "SELECT * FROM zayvki WHERE mail = '";
	
	private final Connection conn;
	
	public ZayvkiDB(Connection con) {
		this.conn = con;
	}
	
	
	/* Выбирает из таблицы заявок, те
	 * заявки, принадлежат определенному клиенту
	 * по его e-mail адресу 
	 * Класс - Zayvki
	 */
	public List<Zayvki> getAllZayvki(String mail) {
		
		SQL_ZAYVKI_CLIENT = SQL_ZAYVKI_CLIENT + mail + "'";
		
	     List<Zayvki> result = new ArrayList<Zayvki>();
	     try
	     {
	       result = this.conn.createQuery(SQL_ZAYVKI_CLIENT)
	    		   .executeAndFetch(Zayvki.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}

}
