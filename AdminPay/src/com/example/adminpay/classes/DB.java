package com.example.adminpay.classes;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

public class DB {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/pay_system";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	//private static final String DB_PASS = "ghbphfr";
	
	
	public static Connection getConnection() throws SQLException {
		
		DataSource ds = null;
		ds = MyDataSource.getMySQLDataSource();
		
		//Sql2o sql2o = new Sql2o(DB_URL, DB_USER, DB_PASS);
		Sql2o sql2o = new Sql2o(ds);
		
		Connection conn = sql2o.open();
		
		return conn;		
	}
   	
}
