package com.example.service.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP-PK
 */
public class DB {
    
    /**
     * ��������� ���������� � ��
     * 
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
		
	Connection connect = null;		
		
	try {
            // ����������� ����� JDBC
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pay_system?"
			              + "user=root&password=ghbphfr&characterEncoding=utf8");			
	} catch (ClassNotFoundException e) {			
            e.printStackTrace();
	}
		
	return connect;
    }
    
   /**
    *  ��������� �������� �������� ��� ��������� �������
    *	
    *  @param pay     - ��� ��������� �������
    *  @param valuta  - ������������ ������
    *  @param typePay - ��� ������� ��������/�����������	
    *  @retrun      
    */
    @SuppressWarnings("resource")
	public static Kommis getKomis(String pay, String valuta) throws Exception {
		
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
		
	String sql = "SELECT kommis, min_kom FROM rate_raschet WHERE pay_system='"+pay
			+"' AND valuta='"+valuta+"' AND operation='�����'" 
			+ " AND type_paschet='�����������' AND point='�����'";				
		
	//double kommis = 0.0;
	Kommis kommis = new Kommis();	
	try {
        	conn = getConnection();
            /*if (!valuta.equals("WMR") || !valuta.equals("RUB")) {
			// ���������� ��������� ����� ������ �� ������� ����
			
            }*/
            
        	st = conn.createStatement();
        	rs = null;		
        	rs = st.executeQuery(sql);
        	rs.next();			
        	kommis.setKommis(rs.getDouble("kommis"));
        	kommis.setMin_kom(rs.getDouble("min_kom"));
        	
        	// ��������� �������������� ��������
        	sql = "select value from params where='PARAMS_ONE_BEZNAL'";
        	rs = st.executeQuery(sql);
        	rs.next();
        	String dop_kommis = rs.getString("value");
        	kommis.setDop_kommis(Double.valueOf(dop_kommis));
        	
		} catch (SQLException e) {
            e.printStackTrace();
		} finally {
            close(conn, st, rs);
		}		
		
	return kommis;
    }    
    
    /**
    *   �������� ���������� � ��
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

