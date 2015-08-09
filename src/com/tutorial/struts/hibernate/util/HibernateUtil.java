package com.tutorial.struts.hibernate.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
   
    private static Log log = LogFactory.getLog(HibernateUtil.class);   
    private static final SessionFactory sessionFactory;
    private Configuration cfg=new Configuration();
  //ruta al directorio de ficheros
	private static final String casa = "D:/eclipse Helios/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/ContaNetApp/WEB-INF/config/hibernate.cfg.xml";
	
	
	static {
        try {
            // Create the SessionFactory
            sessionFactory = 
            /*new Configuration()
            .configure(
            		//CURRO:new File("D:/desarrollos_2011/Galea-touroperadores/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/strutsHibernateApplication/WEB-INF/config/hibernate.cfg.xml") 
            		//CASA: 
            		new File(casa)
            		)
            		.buildSessionFactory();  
            */
            new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }    
    
    public static final ThreadLocal<Session> session = new ThreadLocal<Session>();    
    
    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }
    
    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);
        if (s != null)
            s.close();
    }    
}