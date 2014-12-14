package com.example.adminpay.classes;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

public class ClientDB {
	
	private final Connection conn;
	
	private final String SQL_ALL_CLIENT = "select * from client";
	private final String SQL_ALL_CLIENT_UR = "select * from client_ur";
	
	public ClientDB(Connection con) {
		this.conn = con;
	}
	
	public List<Client> getAllClient() {
	     List<Client> result = new ArrayList<Client>();
	     try
	     {
	       result = conn.createQuery(SQL_ALL_CLIENT)
	    		   .executeAndFetch(Client.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	public List<ClientUr> getAllClientUr() {
		
	     List<ClientUr> result = new ArrayList<ClientUr>();
	     try
	     {
	       result = conn.createQuery(SQL_ALL_CLIENT_UR)
	    		   .executeAndFetch(ClientUr.class);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
	     return result;
	}
	
	public void updateClient(Client client) {
		
	}

}
