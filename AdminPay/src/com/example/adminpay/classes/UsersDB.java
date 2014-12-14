package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class UsersDB {
	
	private final Connection conn;
	
	private final String SQL_ALL_USERS = "select * from users";
	// Запрос для создание нового пользователя
	private final String SQL_ADD_USERS = "INSERT INTO users("
						+ "login, password, type, fio, telephone) "
						+ "VALUES(:login, :pass, :type, :fio, :tele)";
	// Запрос для обновление пользователя
	private final String SQL_UPDATE_USERS = "UPDATE users set "
			+ "login = :login, password = :pass, type = :type, "
			+ "fio = :fio, telephone = :tele WHERE id = :id";			
	
	public UsersDB(Connection con) {
		this.conn = con;
	}
	
	/**
	 * @return - получение всех пользователей
	 */
	public List<Users> getAllUsers() {
	     List<Users> result = new ArrayList<Users>();
	     try
	     {
	       result = conn.createQuery(SQL_ALL_USERS)
	    		   .executeAndFetch(Users.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/**
	 * @param user - 
	 */
	public void writeUser(Users user) {
		
		try {
			conn.createQuery(SQL_ADD_USERS)
			.addParameter("login", user.getLogin())
	        .addParameter("pass", user.getPassword())
	        .addParameter("type", user.getType())    	      
	        .addParameter("fio", user.getFio())
	        .addParameter("tele", user.getTelephone())	        
	        .executeUpdate();
		} catch (Exception e) {
	    	 e.printStackTrace();
	     }
	}
	
	/**
	 * Обновление информации о пользователе
	 * @param user
	 */
	public void updateUser(Users user) {
		
		try {
    	    this.conn.createQuery(SQL_UPDATE_USERS)
    	        .addParameter("id", user.getId())
    	        .addParameter("login", user.getLogin())
    	        .addParameter("pass", user.getPassword())    	      
    	        .addParameter("type", user.getType())
    	        .addParameter("fio", user.getFio())
    	        .addParameter("tele", user.getTelephone())  
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

}
