package com.example.adminpay.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SendSMS {
	
	String login = "";
	String pass = "";
	String name = "";
	
	public SendSMS() {
		
		// Формирование пароля MD5
		this.pass = "kjrjvjnbd";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			this.pass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// Логин
		this.login = "dtsoftweb@yandex.ru";
		// Подпись сообщения
		this.name = "EkaPay";
	}
	
	/**
	 * Функция проверяет статус отправленного сообщения
	 * @param id - Идентификатор сообщения
	 * @return   - статус сообщения
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String verifyState(String id) throws MalformedURLException, IOException {
		
		String res = "";
		// устанавливаем соединение
		URLConnection conn = new URL("https://gate.smsaero.ru/status/?").openConnection();
		// мы будем писать POST данные в out stream
		conn.setDoOutput(true);
		
		String query = "user="+this.login+"&password="+this.pass+"&id="+id;
		OutputStreamWriter out =
				new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		out.write(query);	
		out.flush();
		out.close();

		// 	читаем то, что отдал нам сервер
		String html = readStreamToString(conn.getInputStream(), "UTF-8");
		if (html.indexOf("success") > 0) {
			res = "Доставлено";
		}
		return res;
	}
	
	/* Обработка результатов ответа от сервиса */
	private String readStreamToString(InputStream in, String encoding)
	        throws IOException {
	    StringBuffer b = new StringBuffer();
	    InputStreamReader r = new InputStreamReader(in, encoding);
	    int c;
	    while ((c = r.read()) != -1) {
	        b.append((char)c);
	    }
	    return b.toString();
	}


}
