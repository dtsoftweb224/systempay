package com.example.managerpay.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDop {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pay_system?characterEncoding=UTF-8&amp;characterSetResults=utf8&amp";
	private static final String DB_USER = "root";
	//private static final String DB_PASS = "root";
	private static final String DB_PASS = "ghbphfr";
	
	/* Соединение с БД */
	public static Connection getConnection() throws ClassNotFoundException {
		
		Connection conn = null;	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			//conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	 
	/* Получение номер заявки
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static int GetNumIndexPay(boolean type) throws Exception {
		
		int num = 1;
		String sqlRead = "";
		String sqlUpdate = "";
		Connection conn;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pstmt;
		/* type - true вывод на карту */
		if (type) {
			sqlRead = "SELECT numC FROM numId WHERE id=1";
			sqlUpdate = "UPDATE numId SET numC=? WHERE id=1";
		} else {
			sqlRead = "SELECT numB FROM numId WHERE id=1";
			sqlUpdate = "UPDATE numId SET numB=? WHERE id=1";
		}
		
		conn = getConnection();
		// Получение идентификатора заявки
		try {			
			st = conn.createStatement();
			rs = st.executeQuery(sqlRead);			
			if (rs.next() != false) {
				num = rs.getInt("numC");
			}
			// Инкрементирование заявки
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setInt(1, num+1);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, rs);
		}
		
		return num;
	}

	/* Закрытие соединения с базой данных */
	public static final void close(Connection pCn, Statement pSt,
			ResultSet rs) throws Exception {
		if (rs != null) {
			rs.close();
		}
		if (pSt != null) {
			pSt.close();
		}
		if (pCn != null) {
			pCn.close();
		}
	}
}
