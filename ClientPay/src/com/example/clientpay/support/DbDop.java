package com.example.clientpay.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.clientpay.classes.ZayvkaCard;
import com.vaadin.ui.ComboBox;

public class DbDop {
	
	public static Connection getConnection() throws Exception {
		
		Connection connect = null;		
		
		try {
			// Подключение JDBC
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost:3306/pay_system?characterEncoding=UTF-8&"
			              + "user=root&password=root");			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
		
		return connect;
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
	
	/* Запись информации о регистрации заявки */
	public static void WriteRegZayvka(ZayvkaCard zayvka) throws Exception {
		
		Connection conn;
		PreparedStatement pstmt;
		Statement st = null;
		ResultSet rs = null;
		
		String SQL_ZAYVKI_WRITE = "INSERT INTO registry_zayvki("
			+ "id_zayvki, data, summa, payIn, fioClient, payOut, mailCLient)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?)";		
		String SQL_CONUT_ZAYVKI = "SELECT count(*) as con FROM zayvkicard";
		
		int id_zayvki = 0;
		
		conn = getConnection();
		// Получение идентификатора заявки
		try {			
			st = conn.createStatement();
			rs = st.executeQuery(SQL_CONUT_ZAYVKI);			
			if (rs.next() != false) {
				id_zayvki = rs.getInt("con");				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {	
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
			pstmt = conn.prepareStatement(SQL_ZAYVKI_WRITE);
			pstmt.setInt(1, id_zayvki);
			pstmt.setDate(2, sqlDate);
			pstmt.setDouble(3, zayvka.getSummaPay());
			pstmt.setString(4, zayvka.getPayIn());
			pstmt.setString(5, zayvka.getFioClient());
			pstmt.setString(6, zayvka.getPayOut());
			pstmt.setString(7, zayvka.getMail());
			pstmt.executeUpdate();
		} catch (SQLException e) {		
			e.printStackTrace();
		} finally {
			close(conn, st, rs);
		}
	}
	
	/**
	 * @param combo - выпадающий список в который будут загружены данные
	 * @param type  - тип формы, для которой заполняется список
	 * @throws Exception
	 */
	public static void GetBikLoadCombo(ComboBox combo, boolean type) throws Exception {
		
		String SQL_BIK_NAME = "SELECT bank FROM bik WHERE ";
		
		Connection conn;
		Statement st = null;
		ResultSet rs = null;
		
		/* В зависимости от формы отображения 
		 *  выбирается тип фильтрации записей 
		 */
		if (type) {
			SQL_BIK_NAME = SQL_BIK_NAME + "from_beznal=true";
		} else {
			SQL_BIK_NAME = SQL_BIK_NAME + "from_nal=true";
		}
		
		conn = getConnection();
		// Получение идентификатора заявки
		try {			
			st = conn.createStatement();
			rs = st.executeQuery(SQL_BIK_NAME);			
			while(rs.next() != false) {
				combo.addItem(rs.getString("bank"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, rs);
		}
	}
}