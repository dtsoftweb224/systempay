package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.classes.DB;
import com.example.classes.Zayvki;
import com.example.classes.ZayvkiDB;

@Path("/")
public class ZayvkiService {

	@POST
	@Path("/create")
	@Produces("application/json")
	public Zayvki createZayvka(Zayvki zayvki) {
		
		Zayvki res = new Zayvki();
		// Добавление заявки в БД
		ZayvkiDB zayvkaDB = new ZayvkiDB(DB.getConnection());
		zayvkaDB.InsertNewZayvka(zayvki);
				
		return res;
	}
	
	@POST
	@Path("/getInfo")
	@Produces("application/json")
	public Zayvki getZayvkaToNum(Zayvki zayvki) {
		
		Zayvki res = new Zayvki();
		ZayvkiDB zayvkaDB = new ZayvkiDB(DB.getConnection());
		// Получение информации о заявке по ее номеру
		res = zayvkaDB.GetZayvkaToNumberPay(zayvki.getNumberPay());
		
		return res;
	}
}
