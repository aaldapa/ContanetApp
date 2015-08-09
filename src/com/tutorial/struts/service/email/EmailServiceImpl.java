package com.tutorial.struts.service.email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.struts.util.MessageResources;

import com.tutorial.struts.service.clase.ClaseServiceImpl;

public class EmailServiceImpl implements IEmailService {

	/**
	 * Singleton Instance of EmailServiceImpl
	 */
	private static EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
	
	/**
	 * Creates Instance of {@link EmailServiceImpl}
	 */
	private EmailServiceImpl(){		
	}
	
	/***
	 * Gets Instance of EmailServiceImpl
	 * @return EmailServiceImpl Implementation
	 */
	public static EmailServiceImpl getInstance(){	
		return emailServiceImpl;
	}
	
	@Override
	public Boolean enviarEmail(MessageResources recursos, String asunto,
			String texto, File file) {
		 try
	        {
	        	
	            // Propiedades de la conexión
	            Properties props = new Properties();
	            props.setProperty("mail.smtp.host", recursos.getMessage("mail.smtp.host"));
	            props.setProperty("mail.smtp.starttls.enable", recursos.getMessage("mail.smtp.starttls.enable"));
	            props.setProperty("mail.smtp.port", recursos.getMessage("mail.smtp.port"));
	            props.setProperty("mail.smtp.user", recursos.getMessage("mail.smtp.user"));
	            props.setProperty("mail.smtp.auth", recursos.getMessage("mail.smtp.auth"));

	            // Preparamos la sesion
	            Session session = Session.getDefaultInstance(props);

	            // Construimos el mensaje
	            MimeMessage message = new MimeMessage(session);
	            
	          //texto del mensaje
	            BodyPart mensaje = new MimeBodyPart();
	            mensaje.setText(texto);
	            //Adjunto del mensaje
	            BodyPart adjunto = new MimeBodyPart();
	            if (file!=null){
	            	adjunto.setDataHandler(new DataHandler(new FileDataSource(file.getPath())));
	            	adjunto.setFileName(file.getName());
	            }
	            //juntamos texto y adjunto
	            MimeMultipart multiParte = new MimeMultipart();

	            multiParte.addBodyPart(mensaje);
	            if (file!=null)
	            	multiParte.addBodyPart(adjunto);
	            
	            message.setContent(multiParte);
	            
	            
	            message.setFrom(new InternetAddress("yo@yo.com"));
	            message.addRecipient(
	                Message.RecipientType.TO,
	                new InternetAddress(recursos.getMessage("mail.smtp.user")));
	            message.setSubject(asunto);
	           // message.setText(
	             //   "Mensajito con Java Mail" + "de los buenos." + "poque si");

	            
	            // Lo enviamos.
	            Transport t = session.getTransport("smtp");
	            t.connect(recursos.getMessage("mail.smtp.user"), recursos.getMessage("mail.smtp.password"));
	            t.sendMessage(message, message.getAllRecipients());

	            // Cierre.
	            t.close();
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	return false;
	        }

	        return true;
	}

}
