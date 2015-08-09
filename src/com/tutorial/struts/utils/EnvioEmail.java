package com.tutorial.struts.utils;

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

public class EnvioEmail {

    /**
     * main de prueba
     * @param args Se ignoran.
     */
    public static void main(String[] args)
    {
    	String smtpHost="";
    	String starttlsEnable="";
    	String smtpPort="";
    	String smtpUser="";
    	String smtpAuth="";
    	String smtpPassword="";
    	
        try
        {
        	
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "aaldapa@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            
          //texto del mensaje
            BodyPart texto = new MimeBodyPart();
            texto.setText("Texto del mensaje");
            //Adjunto del mensaje
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource("d:/report1.pdf")));
            adjunto.setFileName("report1.pdf");
            
            //juntamos texto y adjunto
            MimeMultipart multiParte = new MimeMultipart();

            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            
            message.setContent(multiParte);
            
            
            message.setFrom(new InternetAddress("yo@yo.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("aaldapa@gmail.com"));
            message.setSubject("Hola");
           // message.setText(
             //   "Mensajito con Java Mail" + "de los buenos." + "poque si");

            
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("aaldapa@gmail.com", "944965805");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
