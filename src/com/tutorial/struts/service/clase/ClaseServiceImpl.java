package com.tutorial.struts.service.clase;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.clase.ClaseForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class ClaseServiceImpl implements IClaseService {

	private static final  String LISTA_ALL_CLASES_QUERY =
		" from Clase clase " +
		" inner join fetch clase.grupo as grupo" +
		" where clase.baja='N' order by grupo.nombre";

	private static final  String LISTA_CLASES_GRUPO =
		" from Clase clase " +
		" inner join fetch clase.grupo as grupo" +
		" where clase.baja='N' " +
		" and clase.grupo.idGrupo=:idGrupo" +
		" order by clase.nombre";
	
	/**
	 * Singleton Instance of ClaseServiceImpl
	 */
	private static ClaseServiceImpl claseServiceImpl = new ClaseServiceImpl();
	
	/**
	 * Creates Instance of {@link ClaseServiceImpl}
	 */
	private ClaseServiceImpl(){		
	}
	
	/***
	 * Gets Instance of ClaseServiceImpl
	 * @return ClaseServiceImpl Implementation
	 */
	public static ClaseServiceImpl getInstance(){	
		return claseServiceImpl;
	}
	@Override
	public List<Clase>getLstClases(Integer idGrupo){
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Clase> lstClases=null;
		
		try{
			transaction=session.beginTransaction();
			Query query=null;
			if (idGrupo==null || idGrupo==0)
				query=session.createQuery(LISTA_ALL_CLASES_QUERY);
			else{
				query=session.createQuery(LISTA_CLASES_GRUPO);
				query.setParameter("idGrupo", idGrupo, Hibernate.INTEGER);
			}
			lstClases=(List<Clase>)query.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		return lstClases;
	}

	@Override
	public ClaseForm getClase(Integer id) {
				
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Clase clase=null;
		ClaseForm claseForm=new ClaseForm();
		
		try{
			
			transaction=session.beginTransaction();
								
			clase=(Clase)session.get(Clase.class, id);
			
			claseForm.setId(clase.getIdClase());
			claseForm.setNombre(clase.getNombre());
			claseForm.setDescripcion(clase.getNotas());
			claseForm.setIdGrupo(clase.getGrupo().getIdGrupo());
			
			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		
		return claseForm;
	}

	@Override
	public void saveClase(ClaseForm claseForm) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		try{
			transaction=session.beginTransaction();
			
			Clase clase=new Clase();
			Grupo grupo=(Grupo)session.load(Grupo.class, claseForm.getIdGrupo());
			
			if (claseForm.getId()!=null && claseForm.getId()>0){
				clase.setIdClase(claseForm.getId());
			}
			clase.setNombre(claseForm.getNombre());
			clase.setNotas(claseForm.getDescripcion());
			clase.setGrupo(grupo);
			clase.setBaja("N");
			
			session.saveOrUpdate(clase);

			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
	}

	@Override
	public void deleteClase(Integer id) {
		Clase clase=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			clase=(Clase)session.get(Clase.class, id);
			clase.setBaja("S");
				
				
			session.merge(clase);
			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
	}

	@Override
	public Grupo cargarGrupo(Integer id) {
		
		Grupo grupo=new Grupo ();
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			grupo=(Grupo)session.get(Grupo.class, id);
		}
		catch (Exception e) {
			System.out.println("error: "+e);
		}
		finally{
			HibernateUtil.closeSession();	
		}
			
		return grupo;
	}
}
