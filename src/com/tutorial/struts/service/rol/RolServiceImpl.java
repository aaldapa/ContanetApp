package com.tutorial.struts.service.rol;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.struts.forms.rol.RolForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class RolServiceImpl implements IRolService {

	private static final  String LISTA_ROLES_QUERY =" from Perfil rol where rol.baja='N'";
	
	
	/**
	 * Singleton Instance of RolServiceImpl
	 */
	private static RolServiceImpl rolServiceImpl = new RolServiceImpl();
	
	/**
	 * Creates Instance of {@link RolServiceImpl}
	 */
	private RolServiceImpl(){		
	}
	
	/***
	 * Gets Instance of UsuarioServiceImpl
	 * @return RolServiceImpl Implementation
	 */
	public static RolServiceImpl getInstance(){	
		return rolServiceImpl;
	}
	
	
	@Override
	public List<Perfil> getLstRoles() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		List<Perfil> lstRoles=null;
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_ROLES_QUERY);
			lstRoles=(List<Perfil>)query.list();
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
		return lstRoles;
	}

	@Override
	public RolForm getRoles(Integer id) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Perfil rol=null;
		RolForm rolForm=new RolForm();
		try{
			transaction=session.beginTransaction();
			rol=(Perfil)session.get(Perfil.class, id);
			
			rolForm.setId(rol.getIdPerfil());
			rolForm.setNombre(rol.getNombre());
			rolForm.setDescripcion(rol.getDescripcion());
						
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
		return rolForm;
		
	}

	@Override
	public void saveRol(RolForm rolForm) {

		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			Perfil rol=new Perfil();
						
			rol.setIdPerfil(rolForm.getId());
			rol.setNombre(rolForm.getNombre());
			rol.setDescripcion(rolForm.getDescripcion());
			rol.setBaja("N");
			
			session.merge(rol);
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
	public void deleteRol(Integer id) {
		Perfil rol=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			rol=(Perfil)session.get(Perfil.class, id);
			rol.setBaja("S");
			session.merge(rol);
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
