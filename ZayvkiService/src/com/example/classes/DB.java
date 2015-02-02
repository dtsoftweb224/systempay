package com.example.classes;

import javax.sql.DataSource;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DB {

	/**
	 * Установка соединения с БД
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		
		DataSource ds = null;
		ds = MyDataSource.getMySQLDataSource();
		
		Connection conn = null;	
		
		Sql2o sql2o = new Sql2o(ds);
		
		conn = sql2o.open();
		
		return conn;
		
	}

}
