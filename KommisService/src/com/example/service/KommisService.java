package com.example.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.service.classes.DB;
import com.example.service.classes.Kommis;

@Path("/")
public class KommisService {
	
	@POST
	@Path("/raschet")
	@Produces(MediaType.APPLICATION_JSON)
	public Kommis raschet(Kommis kommisIn) {
		
		double summa = 0.0;
		double kom = 0.0;
		double komItog = 0.0;
		
		Kommis kommis = new Kommis();
		
		try {
            /* Получаем значение комиссии для выбранной
            * платежной системы и валюты
            */
            kommis = DB.getKomis(kommisIn.getPay(), kommisIn.getValuta());            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		kom = kommis.getKommis();
		
		// Расчет суммы
        if (kommisIn.getSummaIn() > 0.0) {
        	// Расчет от введенной суммы списания
        	summa = kommisIn.getSummaIn();
        	
        	kommis.setSummaIn(summa);
        	komItog = summa * kom / 100;
        	// Единоразовая комиссия
        	komItog += kommis.getDop_kommis();
        	kommis.setSummaOut(summa - komItog);
        } else {
        	// Расчет от суммы зачисления
        	summa = kommisIn.getSummaOut(); 
        	
        	kommis.setSummaOut(summa);
        	summa =(summa * 100) / (100 - kom);
        	// Добавление единоразовой комиссии
        	summa += kommis.getDop_kommis();
        	kommis.setSummaIn(summa);
        	komItog = kommis.getSummaIn() - kommis.getSummaOut();
        }
        
        kommis.setPay(kommisIn.getPay());
        kommis.setValuta(kommisIn.getValuta());
        kommis.setKommis(komItog);
		
		return kommis;
	}
	
	@GET
	@Path("/get/kommis/{pay}/{summa}")
	@Produces(MediaType.APPLICATION_JSON)
	public Kommis getKommisIn(@PathParam("pay") String pay, @PathParam("summa") double summa) {
 
		Kommis kommis = new Kommis();
		kommis.setPay(pay);
		kommis.setSummaIn(summa);
 
		return kommis;
 
	}

}
