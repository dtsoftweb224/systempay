package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class KommisDB {
	
	private final Connection conn;
	
	private final String SQL_ALL_KOMMIS = "select * from kommis";
	private final String SQL_ADD_KOMMIS = "INSERT INTO kommis("
						+ "paySystem, komSystem, kommis, minKommis,"
						+ "maxKommis) VALUES(:pay, :komSys, :kom, :min, :max)";
	private final String SQL_UPDATE_KOMMIS = "UPDATE kommis "
			+ "SET paySystem=:system, komSystem=:komSys, kommis=:kommis, "
			+ "minKommis=:min, maxKommis=:max where id=:id";
	
	public KommisDB(Connection con) {
		this.conn = con;
	}
	
	/**
	 * @return - получение коммисий всех платежных
	 * систем
	 */
	public List<Kommis> getAllKommis() {
	     List<Kommis> result = new ArrayList<Kommis>();
	     try
	     {
	       result = conn.createQuery(SQL_ALL_KOMMIS)
	    		   .executeAndFetch(Kommis.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	/**
	* @param kommis - 
	*/
	public void writeUser(Kommis kommis) {
		
		try {
			conn.createQuery(SQL_ADD_KOMMIS)
			.addParameter("pay", kommis.getPaySystem())
	        .addParameter("komSys", kommis.getKomSystem())
	        .addParameter("kom", kommis.getKommis())    	      
	        .addParameter("min", kommis.getMinKommis())
	        .addParameter("max", kommis.getMaxKommis())	        
	        .executeUpdate();
		} catch (Exception e) {
	    	 e.printStackTrace();
	     }
	}

}
