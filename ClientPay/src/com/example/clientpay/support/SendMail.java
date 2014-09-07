package com.example.clientpay.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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
        props.put("mail.debug", "false");
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
            msg.setSubject("Формирование заявки");
            msg.setSentDate(new Date());
 
            // Установка тела сообщения
           /* String text = "<h1>Уважаемы клиент.</h1>/n"+
            			"<h2>Ваша заявка на вывод средств номер "+ textMail +" сформирована</h2>";	
            msg.setText(text);*/
            
            setHTMLContent(msg, textMail);
 
            // Отправка сообщения
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            // Печать информации об исключении в случае его возникновения
            mex.printStackTrace();
        }
	}
	
	private void setHTMLContent(Message msg, String textMail) throws MessagingException {
		 
        String html = "<html><head><title>" +
                        msg.getSubject() +
                        "</title></head><body><h1>" +
                        "Уважаемый клиент." +
                        "</h1>"
                        + "<h3><p>Ваша заявка на вывод средств номер "+ 
                        textMail +" сформирована</p></h3></body></html>";
 
        // HTMLDataSource является внутренним классом
        msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
    }
	
	static class HTMLDataSource implements DataSource {
        private String html;
 
        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }
 
        // Возвращаем html строку в InputStream.
        // Каждый раз возвращается новый поток
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("Null HTML");
            return new ByteArrayInputStream(html.getBytes());
        }
 
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }
 
        public String getContentType() {
            return "text/html";
        }
 
        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }
}
