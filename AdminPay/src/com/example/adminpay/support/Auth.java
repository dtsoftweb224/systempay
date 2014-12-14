package com.example.adminpay.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Auth {
	
	/**
	 * Установка соединения с БД
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		
		Connection connect = null;		
		
		try {
			// Подключение через JDBC
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost:3306/pay_system?"
			              + "user=root&password=ghbphfr");			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
		
		return connect;
	}		
	
	/**
	 * Проверка на правильность введенного логина и пароля поль-ля
	 * 
	 * @param login - логин проверяемого пользователя
	 * @param pass  - пароль проверяемого пользователя
	 * @return      - true если данные пользователя верны
	 * @throws Exception
	 */
	public static boolean authUser(String login, String pass) throws Exception {
		
		boolean res = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM users WHERE login='"+login+"' and password='"+pass+"'";
		String user = "";
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = null;		
			rs = st.executeQuery(sql);
			rs.next();
			user = rs.getString("login");
			
			if (!user.equals("")) {
				
				sql = "INSERT INTO logs(data, login, program) "
						+ "VALUES(now(), '" + login + "','ADMIN')";
				int rp = st.executeUpdate(sql);
				
				res = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, rs);
		}			
		
		return res;
	}
	
	/**
	 * Закрытие соединения с БД
	 * 
	 * @param pCn
	 * @param pSt
	 * @param rs
	 * @throws Exception
	 */
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
