package com.example.projectmail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	private String supportMail = "prizrak309@mail.ru"; // Адрес электронной почты отправителя
	private String host = "smtp.mail.ru"; // Адрес SMTP сервера
	
	public SendMail(String mailClient, String textMail) {
		
		 // Создание свойств, получение сессии
        Properties props = new Properties();
 
        // При использовании статического метода Transport.send()
        // необходимо указать через какой хост будет передано сообщение
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "465");
        // Включение debug-режима
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("prizrak309", "kjrjvjnbd39");
            }
        });
        
        try {
            // Создание объекта сообщения
            Message msg = new MimeMessage(session);
 
            // Установка атрибутов сообщения
            msg.setFrom(new InternetAddress(supportMail));
            //InternetAddress[] address = {new InternetAddress(supportMail)};
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailClient));
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new Date());
 
            // Установка тела сообщения
            msg.setText(textMail);
 
            // Отправка сообщения
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            // Печать информации об исключении в случае его возникновения
            mex.printStackTrace();
        }
	}    
}
