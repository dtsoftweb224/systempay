package com.example.managerpay.classes;

import javax.sql.DataSource;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DB {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pay_system";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	public static Connection getConnection() {
		
		DataSource ds = null;
		ds = MyDataSource.getMySQLDataSource();
		
		Connection conn = null;	
		
		//Sql2o sql2o = new Sql2o(DB_URL, DB_USER, DB_PASS);
		Sql2o sql2o = new Sql2o(ds);
		
		conn = sql2o.open();
		
		return conn;
		
	}

}
