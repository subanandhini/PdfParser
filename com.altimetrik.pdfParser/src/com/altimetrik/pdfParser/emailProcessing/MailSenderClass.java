package com.altimetrik.pdfParser.emailProcessing;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

public class MailSenderClass {
	public static void sendMail(String recipient) throws Exception {

		System.out.println("Preparing to send email");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.user", "nsuba066@gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		final String myAccountEmail = "nsuba066@gmail.com";
		final String password = "12345678p=";

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		Message message = prepareMessage(session, myAccountEmail, recipient);
		try {
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message sent");
	}

	static String subject = "Status Update regarding invoice";
	static String text = "Dear Vendor your invoice has been approved";

	private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(text);
			return message;
		} catch (MessagingException ex) {
			Logger.getLogger(MailSenderClass.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static String getHeaderValue() {
		return subject;
	}

	public static String getText() {
		return text;
	}

}
