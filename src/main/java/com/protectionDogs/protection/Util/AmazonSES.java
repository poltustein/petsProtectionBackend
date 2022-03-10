package com.protectionDogs.protection.Util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmazonSES {

	private static final Logger log = LoggerFactory.getLogger(AmazonSES.class);

	static final String FROM = "info@expediteautomation.com";
	static final String CONFIGSET = "ConfigSet";
	static final String SUBJECT = "Hello from Expedite";
	static final String WELCOMEBODY = "Welcome to Expedite Staff App. Your new user has been created on the app. The password is:$password$. Please login to the app using your emailid and password.";
	static final String FORGOTBODY = "Your Expedite Staff App password has been reset to $password$.";
	static final String HOST = "email-smtp.ap-south-1.amazonaws.com";
	static final int PORT = 587;

	public static Boolean sendMail(String toEmail, String password, EntityTypes emailType) {
		

		Transport transport=null;
		try {
		// Create a Properties object to contain connection configuration information.
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);

			String body=null;
			if(emailType==null)
				return false;
			else if(emailType.equals(EntityTypes.FORGOT))
				body = FORGOTBODY;
			else
				body = WELCOMEBODY;
			
			body = body.replace("$password$", password);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM, "Expedite"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject(SUBJECT);
			msg.setContent(body, "text/html");
			transport = session.getTransport();
			log.info("Sending...");
			transport.connect(HOST, "AKIAW22MKOBF4KQTNEXY", "BAv/RBH9YOVX3YRaZmbjfA2fPZ4jzTJB+v9VHcujXZA8");
			transport.sendMessage(msg, msg.getAllRecipients());
			log.info("Email sent to:"+toEmail);
			return true;
		} catch (Exception ex) {
			log.error("The email was not sent.");
			log.error("Error message: " + ex.getMessage());
		} finally {
			if(transport!=null)
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
		}

		return false;
	}

}
