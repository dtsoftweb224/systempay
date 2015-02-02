package com.example.classes;

import java.util.List;

import org.sql2o.Connection;

import com.exaple.support.GenerateNum;

public class ZayvkiDB {
	
	private final Connection conn;
	
	/* Запись заявки на безналичный расчет */
	private final String SQL_INSERT_CARD = "INSERT INTO zayvki(payOut, date,"
			+ "kommis, numberPay, status, summaCard, summaPay, payIn,"
			+ "valuta, wmid, mail, telephone, numSchet, fio, typeOper, typeRaschet) "
			+ "VALUES(:out, :data, :kom, :num, :status, :sumC, :sumP, :in, "
			+ ":val, :wmid, :mail, :tele, :schet, :fio, :oper, :raschet)";
	/* Редактирование заявки на безналичный расчет */
	private final String SQL_UPDATE_CARD = "UPDATE zayvki SET"
			+ " payOut = :bank, date = :date, kommis = :kommis, numberPay = :nPay,"
			+ " status = :status, summaCard = :sCard, summaPay = :sPay, payIn = :type,"
			+ " valuta = :val, wmid = :wmid, telephone=:tele, numSchet=:schet WHERE id = :id";
	/* Получение информации по ее номеру */
	private final String SQL_GET_ZAYVKA = "SELECT payOut, date,"
			+ "kommis, numberPay, status, summaCard, summaPay, payIn,"
			+ "valuta, wmid, mail, telephone, numSchet, fio, typeOper, typeRaschet"
			+ " FROM zayvki WHERE numberPay = :num";

	
	public ZayvkiDB(Connection conn) {
		this.conn = conn;
	}

	/* Редактирование заявки вывода средств на карту */
    @SuppressWarnings("unused")
	private void UpdateZayvka(Zayvki zayvka) {
    	
    	try {
    	    this.conn.createQuery(SQL_UPDATE_CARD)
    	        .addParameter("id", zayvka.getId())
    	        .addParameter("bank", zayvka.getPayOut())
    	        .addParameter("date", zayvka.getDate())    	      
    	        .addParameter("kommis", zayvka.getKommis())
    	        .addParameter("nPay", zayvka.getNumberPay())
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sCard", zayvka.getSummaCard())
    	        .addParameter("sPay", zayvka.getSummaPay())
    	        .addParameter("type", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())    
    	        .addParameter("schet", zayvka.getNumSchet())
    	        .addParameter("tele", zayvka.getTelephone())
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /* Добавление заявки на безналичный расчет */
    @SuppressWarnings("unused")
	private void WriteZayvka(Zayvki zayvka) {
    	
    	try {
    	    this.conn.createQuery(SQL_INSERT_CARD)   
    	        .addParameter("out", zayvka.getPayOut())
    	        .addParameter("data", zayvka.getDate())    	       
    	        .addParameter("kom", zayvka.getKommis())
    	        .addParameter("num", zayvka.getNumberPay())
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sumC", zayvka.getSummaCard())
    	        .addParameter("sumP", zayvka.getSummaPay())
    	        .addParameter("in", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())
    	        .addParameter("mail", zayvka.getMail())
    	        .addParameter("tele", zayvka.getTelephone())
    	        .addParameter("schet", zayvka.getNumSchet())
    	        .addParameter("fio", zayvka.getFio())    	       
    	        .addParameter("oper", zayvka.getTypeOper())
    	        .addParameter("raschet", zayvka.getTypeRaschet())
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void InsertNewZayvka(Zayvki zayvka) {

    	// Генерация номера создаваемой заявки
    	String num = GenerateNum.generateNumberPay();
    	
    	try {
    	    // Добавление заявки
    		this.conn.createQuery(SQL_INSERT_CARD)    	        
    	        .addParameter("out", zayvka.getPayOut())
    	        .addParameter("data", zayvka.getDate())    	       
    	        .addParameter("kom", zayvka.getKommis())
    	        .addParameter("num", num)
    	        .addParameter("status", zayvka.getStatus())
    	        .addParameter("sumC", zayvka.getSummaCard())
    	        .addParameter("sumP", zayvka.getSummaPay())
    	        .addParameter("in", zayvka.getPayIn())
    	        .addParameter("val", zayvka.getValuta())
    	        .addParameter("wmid", zayvka.getWmid())
    	        .addParameter("mail", zayvka.getMail())
    	        .addParameter("tele", zayvka.getTelephone())
    	        .addParameter("schet", zayvka.getNumSchet())
    	        .addParameter("fio", zayvka.getFio())    	        
    	        .addParameter("type", zayvka.getTypeOper())
    	        .executeUpdate();
    		
    		// Запись в таблицу регистрации заявок
    		//InsertRegZayvka(zayvka);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Добавление данных о регистрации заявки
     * 
     * @param zayvka
     */
    @SuppressWarnings("unused")
	private void InsertRegZayvka(Zayvki zayvka) {
    	// Добавление в таблицу регистрации заявок
    	String SQL_ZAYVKI_REG_WRITE = "INSERT INTO registry_zayvki("
    			+ "id_zayvki, data, summa, payIn, fioClient, payOut, mailCLient)"
    			+ " VALUES(:num, :data, :summa, :in, :fio, :out, :mail)";
    	try {
    	    this.conn.createQuery(SQL_ZAYVKI_REG_WRITE)
    	        .addParameter("num", zayvka.getNumSchet())
    	        .addParameter("data", zayvka.getDate())
    	        .addParameter("summa", zayvka.getSummaPay())
    	        .addParameter("in", zayvka.getPayIn())
    	        .addParameter("fio", zayvka.getFio())
    	        .addParameter("out", zayvka.getPayOut())
    	        .addParameter("mail", zayvka.getMail())    	        
    	        .executeUpdate();    	    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
 
    /**
     * Выборка заявки по ее номеру
     * 
     * @param num - номер заявки
     * @return
     */
    public Zayvki GetZayvkaToNumberPay(String num) {
    	
    	Zayvki result = new Zayvki();
    	
    	try {
    	    this.conn.createQuery(SQL_GET_ZAYVKA)
    	        .addParameter("num", num)    	           	        
    	        .executeAndFetchFirst(Zayvki.class);    	    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return result;
    }
    
    /** Обновление статуса заявок 
     * @param status - новый статус, который присваивается заявке
     * @param listId - список идентификаторов заявок, у которых
     * необходимо изменить статус
     */
    public void UpdateZayvkaStatus(List<Integer> listId, String status) {
    	
		String list = "";
		
		for (int i = 0; i < listId.size(); i++) {
			
			if (i == listId.size() - 1) {
				list = list + String.valueOf(listId.get(i));
			} else {
				list = list + String.valueOf(listId.get(i)) + ",";
			}			
		}
	
		String sql = "UPDATE zayvki SET status = :status WHERE id in ("+list+")";
	
    	try {
    	    this.conn.createQuery(sql)
    	   		.addParameter("status", status)    	        	        
    	        .executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /* Преобразование списка ID в строку */
    @SuppressWarnings("unused")
	private String ListIDToString(List<Integer> listId) {
    	
    	String list = "";
		
		for (int i = 0; i < listId.size(); i++) {
			
			if (i == listId.size() - 1) {
				list = list + String.valueOf(listId.get(i));
			} else {
				list = list + String.valueOf(listId.get(i)) + ",";
			}			
		}
		
		return list;
    }
}
