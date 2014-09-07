package com.example.managerpay.classes;

import java.util.List;

/* Класс для создания файла выполнения заявки */
public class CreateZayvkaFile {
	
	public CreateZayvkaFile(List<Integer> masIdZayvki) {
		
		// Получаем список заявок по которым необходимо сформировать файл
		ZayvkiDB zayvkiDB = new ZayvkiDB(DB.getConnection());
		List<Zayvki> zayvki = zayvkiDB.getZayvkiID(masIdZayvki);
	}
	
}
