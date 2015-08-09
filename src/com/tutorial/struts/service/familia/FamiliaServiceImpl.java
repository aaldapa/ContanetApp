package com.tutorial.struts.service.familia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Familia;
import com.tutorial.struts.forms.familia.FamiliaForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class FamiliaServiceImpl implements IFamiliaService {
	
	private static final  String LISTA_FAMILIAS_QUERY =" from Familia familia where familia.baja='N'";
	
	/**
	 * Singleton Instance of FamiliaServiceImpl 
	 */
	private static FamiliaServiceImpl familiaServiceImpl = new FamiliaServiceImpl();
	
	/**
	 * Creates Instance of {@link FamiliaServiceImpl}
	 */
	private FamiliaServiceImpl(){		
	}
	
	/***
	 * Gets Instance of UsuarioServiceImpl
	 * @return RolServiceImpl Implementation
	 */
	public static FamiliaServiceImpl getInstance(){	
		return familiaServiceImpl;
	}
	
	
	@Override
	public List<Familia> getLstFamilias() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		List<Familia> lstFamilias=null;
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_FAMILIAS_QUERY);
			lstFamilias=(List<Familia>)query.list();
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
		return lstFamilias;
	}

	@Override
	public FamiliaForm getFamilia(Integer id) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Familia familia=null;
		FamiliaForm familiaForm=new FamiliaForm();
		try{
			transaction=session.beginTransaction();
			familia=(Familia)session.get(Familia.class, id);
			
			familiaForm.setId(familia.getIdFamilia());
			familiaForm.setNombre(familia.getNombre());
			familiaForm.setNotas(familia.getNotas());
						
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
		return familiaForm;
		
	}

	@Override
	public void saveFamilia(FamiliaForm familiaForm) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			Familia familia=new Familia();
						
			familia.setIdFamilia(familiaForm.getId());
			familia.setNombre(familiaForm.getNombre());
			familia.setNotas(familiaForm.getNotas());
			familia.setBaja("N");
			
			session.merge(familia);
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
	public void deleteFamilia(Integer id) {
		Familia familia=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			familia=(Familia)session.get(Familia.class, id);
			familia.setBaja("S");
			session.merge(familia);
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
