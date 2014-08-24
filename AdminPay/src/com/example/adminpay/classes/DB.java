package com.example.adminpay.classes;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

public class DB {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/pay_system";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "1";
	
	
	public static Connection getConnection() {
		
		Sql2o sql2o = new Sql2o(DB_URL, DB_USER, DB_PASS);
		
		Connection conn = sql2o.open();
		
		return conn;		
	}
   	
}
