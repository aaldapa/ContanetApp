package com.tutorial.struts.service.grupo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.struts.forms.grupo.GrupoForm;
import com.tutorial.struts.forms.rol.RolForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class GrupoServiceImpl implements IGrupoService {

private static final  String LISTA_GRUPOS_QUERY =
	" from Grupo grupo where grupo.baja='N' order by grupo.nombre";
	
	
	/**
	 * Singleton Instance of GrupoServiceImpl
	 */
	private static GrupoServiceImpl grupoServiceImpl = new GrupoServiceImpl();
	
	/**
	 * Creates Instance of {@link GrupoServiceImpl}
	 */
	private GrupoServiceImpl(){		
	}
	
	/***
	 * Gets Instance of GrupoServiceImpl
	 * @return GrupoServiceImpl Implementation
	 */
	public static GrupoServiceImpl getInstance(){	
		return grupoServiceImpl;
	}
	
	
	@Override
	public List<Grupo> getLstGrupos() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction=null;
		List<Grupo> lstGrupos=null;
		try{
			transaction=session.beginTransaction();
			Query query= session.createQuery(LISTA_GRUPOS_QUERY);
			lstGrupos=(List<Grupo>)query.list();
			session.getTransaction().commit();
			
		}catch (Exception e) {
			if (transaction!=null){
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();
		}
		return lstGrupos;
	}

	@Override
	public GrupoForm getGrupo(Integer idGrupo) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Grupo grupo=null;
		GrupoForm grupoForm=new GrupoForm();
		try{
			transaction=session.beginTransaction();
			grupo=(Grupo)session.get(Grupo.class, idGrupo);
			
			grupoForm.setId(grupo.getIdGrupo());
			grupoForm.setNombre(grupo.getNombre());
			grupoForm.setDescripcion(grupo.getNotas());
						
			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return grupoForm;
	}

	@Override
	public void saveGrupo(GrupoForm grupoForm) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			Grupo grupo=new Grupo();
						
			grupo.setIdGrupo(grupoForm.getId());
			grupo.setNombre(grupoForm.getNombre());
			grupo.setNotas(grupoForm.getDescripcion());
			grupo.setBaja("N");
			
			session.merge(grupo);
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
	}

	@Override
	public void deleteGrupo(Integer idGrupo) {
		Grupo grupo=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			grupo=(Grupo)session.get(Grupo.class, idGrupo);
			grupo.setBaja("S");
			session.merge(grupo);
			transaction.commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}

	}

}
