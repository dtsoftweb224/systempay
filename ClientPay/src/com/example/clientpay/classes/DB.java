package com.example.clientpay.classes;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DB {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pay_system?characterEncoding=UTF-8&amp;characterSetResults=utf8&amp";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	public static Connection getConnection() {
		
		Connection conn = null;	
		
		Sql2o sql2o = new Sql2o(DB_URL, DB_USER, DB_PASS);
		
		conn = sql2o.open();
		
		return conn;
		
	}

}
