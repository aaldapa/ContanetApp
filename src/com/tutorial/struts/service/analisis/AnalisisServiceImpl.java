package com.tutorial.struts.service.analisis;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.VistaApunteClases;
import com.tutorial.domain.entidades.baseDatos.VistaApunteGrupos;
import com.tutorial.domain.entidades.baseDatos.VistaApunteMes;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class AnalisisServiceImpl implements IAnalisisService {

	/**
	 * Singleton Instance of AnalisisServiceImpl
	 */
	private static AnalisisServiceImpl analisisServiceImpl = new AnalisisServiceImpl();
	
	/**
	 * Creates Instance of {@link AnalisisServiceImpl}
	 */
	private AnalisisServiceImpl(){		
	}
	
	/***
	 * Gets Instance of AnalisisServiceImpl
	 * @return AnalisisServiceImpl Implementation
	 */
	public static AnalisisServiceImpl getInstance(){	
		return analisisServiceImpl;
	}

	@Override
	public List<VistaApunteMes> getVistaApuntesMes(Integer anio, Integer id_cuenta) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<VistaApunteMes> vista=null;
		
		try{
			transaction=session.beginTransaction();
			
			Query query=null;
			query=session.createQuery("from VistaApunteMes vista where vista.anio=:anio and vista.idCuenta=:cuenta");
			query.setParameter("anio", anio, Hibernate.INTEGER);
			query.setParameter("cuenta", id_cuenta, Hibernate.INTEGER);
				
			vista=(List<VistaApunteMes>)query.list();
				
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		}
			
		return vista;
			
	}
	
	
	@Override
	public List<VistaApunteGrupos> getVistaApuntesGrupo(Integer anio, Integer id_cuenta) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<VistaApunteGrupos> vista=null;
		
		try{
			transaction=session.beginTransaction();
			
			Query query=null;
			query=session.createQuery("from VistaApunteGrupos vista where vista.anio=:anio and vista.idCuenta=:cuenta");
			query.setParameter("anio", anio, Hibernate.INTEGER);
			query.setParameter("cuenta", id_cuenta, Hibernate.INTEGER);
				
			vista=(List<VistaApunteGrupos>)query.list();
				
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		}
			
		return vista;
			
	}
	
	@Override
	public List<VistaApunteClases> getVistaApuntesClases(Integer anio, Integer id_cuenta,Integer id_grupo) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<VistaApunteClases> vista=null;
		
		try{
			transaction=session.beginTransaction();
			
			Query query=null;
			query=session.createQuery("from VistaApunteClases vista where vista.anio=:anio and vista.idCuenta=:cuenta and vista.idGrupo=:grupo");
			query.setParameter("anio", anio, Hibernate.INTEGER);
			query.setParameter("cuenta", id_cuenta, Hibernate.INTEGER);
			query.setParameter("grupo", id_grupo, Hibernate.INTEGER);
				
			vista=(List<VistaApunteClases>)query.list();
				
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		}
			
		return vista;		
	}
}
