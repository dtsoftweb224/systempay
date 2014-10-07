

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ZayvkiDB {
	
	private static Connection conn;
	
	
	/* Запись заявки на безналичный расчет */
	static final String SQL_INSERT_CARD = "INSERT INTO zayvki(payOut, date,"
			+ "kommis, numberPay, status, summaCard, summaPay, payIn,"
			+ "valuta, wmid, mail, numSchet, fName, lName, otch, type) "
			+ "VALUES";
	/* Редактирование заявки на безналичный расчет */
	private final String SQL_UPDATE_CARD = "UPDATE zayvki SET"
			+ " payOut = :bank, date = :date, kommis = :kommis, numberPay = :nPay,"
			+ " status = :status, summaCard = :sCard, summaPay = :sPay, payIn = :type,"
			+ " valuta = :val, wmid = :wmid, telephone=:tele, numSchet=:schet WHERE id = :id";

	
	public ZayvkiDB(Connection conn) {
		this.conn = conn;
	}
	
	public static void InsertZayvki(String values) throws Exception {
		
		Statement stmt = null;
		int rs = 0;	
		String sql = SQL_INSERT_CARD + values;
		conn = DB.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, null);
		}
		
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
