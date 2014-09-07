package com.example.clientpay.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.clientpay.classes.ZayvkaCard;
import com.example.clientpay.classes.Zayvki;
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
	public static void WriteRegZayvka(Zayvki zayvka) throws Exception {
		
		Connection conn;
		PreparedStatement pstmt;
		Statement st = null;
		ResultSet rs = null;
		
		String SQL_ZAYVKI_WRITE = "INSERT INTO registry_zayvki("
			+ "id_zayvki, data, summa, payIn, fioClient, payOut, mailCLient)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?)";		
		
		conn = getConnection();
		// Формирование ФИО клиента
		String fioClient = zayvka.getlName().trim() + " " +
				zayvka.getfName().trim() + " " + zayvka.getOtch().trim();
						
		try {	
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
			pstmt = conn.prepareStatement(SQL_ZAYVKI_WRITE);
			pstmt.setString(1, zayvka.getNumberPay());
			pstmt.setDate(2, sqlDate);
			pstmt.setDouble(3, zayvka.getSummaPay());
			pstmt.setString(4, zayvka.getPayIn());
			pstmt.setString(5, fioClient);
			pstmt.setString(6, zayvka.getPayOut());
			pstmt.setString(7, zayvka.getMail());
			pstmt.executeUpdate();
		} catch (SQLException e) {		
			e.printStackTrace();
		} finally {
			close(conn, st, rs);
		}
	}
	
	/* Запись информации о регистрации заявки */
	public static void WriteRegSMS(String tele, String numPay) throws Exception {
		
		Connection conn;
		PreparedStatement pstmt;
		Statement st = null;
		ResultSet rs = null;
		
		String SQL_SMS_WRITE = "INSERT INTO regsms("
			+ "data, telephone, status, numPay)"
			+ " VALUES(?, ?, ?, ?)";		
		
		conn = getConnection();		
						
		try {	
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
			pstmt = conn.prepareStatement(SQL_SMS_WRITE);
			pstmt.setDate(1, sqlDate);
			pstmt.setString(2, tele);
			pstmt.setString(3, "Отправка");
			pstmt.setString(4, numPay);
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
			SQL_BIK_NAME = SQL_BIK_NAME + "form_beznal=true";
		} else {
			SQL_BIK_NAME = SQL_BIK_NAME + "form_nal=true";
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
	
	
	/**
	 * Получение номер заявки
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
}
