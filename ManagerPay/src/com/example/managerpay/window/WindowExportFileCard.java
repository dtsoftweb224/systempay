package com.example.managerpay.window;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.managerpay.classes.CreateFileCard;
import com.example.managerpay.classes.Zayvki;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;

/**
 * @author Roman Makarov
 * Класс отображающий сформированный файл
 *
 */
public class WindowExportFileCard extends Window  {
	
	private TextArea viewFile = null;
	private List<String> list = null;
	private Zayvki zayvka = new Zayvki();
	private File f = null;
	
	/**
	 * @param idZayvki
	 */
	@SuppressWarnings("unchecked")
	public WindowExportFileCard(List<Integer> idZayvki) {
		
		super("Сформированный файл");
		
		setPositionX(200);
		setPositionY(150);
		
		setWidth("50%");
		setHeight("65%");
		setModal(true);
		
		// Формирование файла
		CreateFileCard fileCard = new CreateFileCard(idZayvki);
		list = fileCard.getListFile();
		// Создание тестового поля для отображения файла
		buildTextArea();
		saveFileClient();
	}
	
	/**
	 * Сохранение файла на компьютер клиента во временную папку
	 * C:\Users\prizrak\AppData\Local\Temp\
	 */
	private void saveFileClient() {
		
		 try {			
			f = File.createTempFile("tmp", ".txt");
			String str = f.getAbsolutePath();
			PrintWriter pw=new PrintWriter(new FileOutputStream(f));	
			
			pw.println(viewFile.getValue().substring(1,viewFile.getValue().length()-1));
			pw.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private void buildTextArea() {
		
		viewFile = new TextArea();		
        
        if (list != null) {
        	viewFile.setRows(list.size());
        	String str = "";
        	for (int i = 0; i < list.size(); i++) {
        		str = str + list.get(i) + "\n";
        	}
        	viewFile.setValue(str);
        	viewFile.setImmediate(true);
            viewFile.setSizeFull();
        } else {
        	
        	viewFile.setValue("The quick brown fox jumps over the lazy dog.");
    		viewFile.setRows(10);
    		viewFile.setImmediate(true);
            viewFile.setSizeFull();
        }
        
        setContent(viewFile);
	}

}
