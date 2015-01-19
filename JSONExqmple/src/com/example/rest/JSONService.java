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
import com.example.classes.Kommis;
import com.sun.jersey.*;

@Path("/")
public class JSONService {
	
	@POST
	@Path("/raschet")
	@Produces("application/json")
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
        	kommis.setSummaOut(summa - komItog);
        } else {
        	// Расчет от суммы зачисления
        	summa = kommisIn.getSummaOut(); 
        	
        	kommis.setSummaOut(summa);
        	summa =(summa * 100) / (100 - kom);
        	kommis.setSummaIn(summa);
        	komItog = kommis.getSummaIn() - kommis.getSummaOut();
        }       
        
        kommis.setPay(kommisIn.getPay());
        kommis.setValuta(kommisIn.getValuta());
        kommis.setKommis(komItog);
        
        return kommis;
    }
	
	@GET
	@Path("/get")
	@Produces("application/json")
	public Track getTrackIn() {
 
		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
		track.setPrice(100);
 
		return track;
 
	}
	
	@GET
	@Path("/get/kommis/{pay}/{summa}")
	@Produces("application/json")
	public Kommis getKommisIn(@PathParam("pay") String pay, @PathParam("summa") double summa) {
 
		Kommis kommis = new Kommis();
		kommis.setPay(pay);
		kommis.setSummaIn(summa);
 
		return kommis;
 
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {
 
		Track track1 = new Track();
		track1.setTitle("Title 1");
		track1.setSinger("Singer 1");
		String result = "Track saved : " + track1.getSinger() 
						 + " " + track1.getTitle();
		
		return Response.status(201).entity(result).build(); 
	}
}
