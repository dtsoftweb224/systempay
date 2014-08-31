package com.example.clientpay.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

public class RegZayvkiDB {

	private final Connection conn;
	
	//private final String SQL_ZAYVKI_ALL = "SELECT * FROM registry_zayvki";
	private final String SQL_ZAYVKI_WRITE = "INSERT INTO registry_zayvki("
			+ "id_zayvki, data, summa, payIn, fioClient, payOut, mailCLient)"
			+ " VALUES(LAST_INSERT_ID(), :data, :summa, :in, :fio, :out, :mail)";
	
	public RegZayvkiDB(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Запись информации о заявке в БД
	 * @param zayvka - класс регистрации заявки
	 */
	public void WriteRegZayvka(RegZayvki zayvka) {
		
		//Logger.getLogger(RegZayvkiDB.class.getName()).info("Выполнение запроса");
		try {
    	    this.conn.createQuery(SQL_ZAYVKI_WRITE)
    	        //.addParameter("id", zayvka.getId_zayvki())
    	        .addParameter("data", zayvka.getData())
    	        .addParameter("summa", zayvka.getSumma())
    	        .addParameter("in", zayvka.getPayIn())
    	        .addParameter("fio", zayvka.getFioClient())
    	        .addParameter("out", zayvka.getPayOut())
    	        .addParameter("mail", zayvka.getMailClient())    	        
    	        .executeUpdate();    	    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
}
