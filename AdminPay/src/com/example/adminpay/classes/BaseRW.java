package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

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
	 * Получение всех записей из таблицы Valuta
	 * 
	 * @param conn
	 * @return
	 */
	public static List<Valuta> getAllValuta(Connection conn) {
		
		List<Valuta> result = new ArrayList<Valuta>();
		String sql = "SELECT * FROM valuta";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(Valuta.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	@SuppressWarnings("unused")
	public static void updateBik(Connection conn, Bik_old bik) {
		
		String sql = "UPDATE bik SET nal = :nal, beznal=:beznal WHERE bik=:bik";
		
		try
	     {	    
		Connection a = conn.createQuery(sql)
	    		   .addParameter("nal", bik.getNal())
	    		   .addParameter("beznal", bik.getBeznal())
	    		   .addParameter("bik", bik.getBik())
	    		   .executeUpdate();
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	}
	
	/**
	 * Функция добавления нового юридического лица
	 * 
	 * @param conn
	 * @param client
	 */
	public static void addClientUr(Connection conn, ClientUr client) {
		
		String sql = "INSERT INTO client_ur(ogrn, inn, kpp, mail, telephone"
				+ ",address) VALUES(:ogrn, :inn, :kpp, :mail, :tele, :address)";
		
		try {
			@SuppressWarnings("unused")
			Connection a = conn.createQuery(sql)
					.addParameter("ogrn", client.getOgrn())
					.addParameter("inn", client.getInn())
					.addParameter("kpp", client.getKpp())
					.addParameter("mail", client.getMail())
					.addParameter("tele", client.getTelephone())
					.addParameter("address", client.getAddress())
					.executeUpdate();
		} catch (Exception e) {
	    	 e.printStackTrace();
	     }
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
	
	/**
	 * Получение клиентов - юридических лиц
	 * 
	 * @param conn
	 * @return
	 */
	public static List<ClientUr> getAllClientUr(Connection conn) {
	     
		List<ClientUr> result = new ArrayList<ClientUr>();
		String sql = "SELECT * FROM client_ur";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(ClientUr.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	/**
	 * Считываем из БД сведения об отправленных SMS
	 * @param conn подключение к БД
	 * @return
	 */
	public static List<SMS> getAllRegSMS(Connection conn) {
	     
		List<SMS> result = new ArrayList<SMS>();
		String sql = "SELECT * FROM regsms";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(SMS.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	/**
	 * Функция считывания логов из БД
	 * 
	 * @param conn
	 * @return
	 */
	public static List<Logs> getAllLogs(Connection conn) {
	     
		List<Logs> result = new ArrayList<Logs>();
		String sql = "SELECT * FROM logs";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(Logs.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	/**
	 * Получение тарифов по платежных системам
	 * 
	 * @param conn
	 * @return
	 */
	public static List<Rate> getAllRate(Connection conn) {
	     
		List<Rate> result = new ArrayList<Rate>();
		String sql = "SELECT * FROM rate";
		
	     try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(Rate.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     return result;
	}
	
	/**
	 * Загрузка платежных систем из БД
	 * 
	 * @param conn
	 * @return
	 */
	public static List<PaySystem> getPaySystem(Connection conn) {
		
		List<PaySystem> result = new ArrayList<PaySystem>();
		String sql = "SELECT * FROM pay_system";
		
		try
	     {
	       result = conn.createQuery(sql)
	    		   .executeAndFetch(PaySystem.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		
		return result;
	}
	
	/**
	 * Добавление новой платежной системы в БД
	 * 
	 * @param conn
	 * @param pay
	 */
	public static void addPaySystem(Connection conn, PaySystem pay) {
		
		String sql = "INSERT INTO pay_system(pay, list_val)"
				+ " VALUES(:pay, :list)";
		
		try {
			@SuppressWarnings("unused")
			Connection a = conn.createQuery(sql)
					.addParameter("pay", pay.getPay())
					.addParameter("list", pay.getList_val())					
					.executeUpdate();
		} catch (Exception e) {
	    	 e.printStackTrace();
	     }
	}
	
	/**
	 * Добавление нового тарифа в БД
	 * 
	 * @param conn
	 * @param rate
	 */
	public static void addRate(Connection conn, Rate rate) {
		
		String sql = "INSERT INTO rate(operation, type_paschet, point, val_point,"
				+ "min_kom, max_kom, state, pay_system) VALUES(:oper, :type, :point,"
				+ ":val_point, :min, :max, :state, :pay)";
		
		try {
			@SuppressWarnings("unused")
			Connection a = conn.createQuery(sql)
					.addParameter("oper", rate.getOperation())
					.addParameter("type", rate.getType_paschet())
					.addParameter("point", rate.getPoint())
					.addParameter("val_point", rate.getVal_point())
					.addParameter("min", rate.getMin_kom())
					.addParameter("max", rate.getMax_kom())
					.addParameter("state", rate.getState())
					.addParameter("pay", rate.getPay_system())
					.executeUpdate();
		} catch (Exception e) {
	    	 e.printStackTrace();
	     }
	}	
	
	/**
	 * Получение списка пл. систем
	 * 
	 * @return
	 */
	public static List<String> getPaySystemList(Connection conn){
	    
		String sql = "SELECT pay FROM pay_system";
	    
	    return conn.createQuery(sql).executeScalarList(String.class);
	      
	}
	
	/**
	 * Получение списка валют для пл. систем
	 * 
	 * @return
	 */
	public static String getPaySystem(Connection conn, String pay){
	    
		String sql = "SELECT list_val FROM pay_system WHERE pay='" + pay + "'";
	    
	    return conn.createQuery(sql).executeScalar(String.class);
	      
	}
	
	/**
	 * Получение значений комиссий 
	 * 
	 * @return
	 */
	public static String getPaySystemVal(Connection conn, String pay){
	    
		String sql = "SELECT val_point,min_kom,"
				+ "max_kom FROM rate WHERE pay='" + pay + "'";
	    
	    return conn.createQuery(sql).executeScalar(String.class);
	      
	}
	
}
