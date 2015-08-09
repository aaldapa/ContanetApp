package com.tutorial.struts.service.apuntepordefecto;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.ApuntePorDefecto;
import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;

public class ApuntePorDefectoImpl implements IApuntePorDefectoService {

	private static final  String GET_APUNTEPORDEFECTO_QUERY =
			" from ApuntePorDefecto apd " +
			" inner join fetch apd.usuario as usuario " +
			//" inner join fetch apd.contabilidadPorDefecto as contabilidad " + 
			//" inner join fetch apd.cuentaPorDefecto as cuenta " +
			//" inner join fetch apd.grupoPorDefecto as grupo " +
			//" inner join fetch apd.clasePorDefecto as clase " +
			" where apd.usuario.idUsuario=:idUsuario";
	
	
	/**
	 * Singleton Instance of ApuntePorDefectoImpl
	 */
	private static ApuntePorDefectoImpl apuntePorDefectoServiceImpl = new ApuntePorDefectoImpl();
	
	/**
	 * Creates Instance of {@link ApuntePorDefectoImpl}
	 */
	private ApuntePorDefectoImpl(){		
	}
	
	/***
	 * Gets Instance of ApuntePorDefectoImpl
	 * @return ApuntePorDefectoImpl Implementation
	 */
	public static ApuntePorDefectoImpl getInstance(){	
		return apuntePorDefectoServiceImpl;
	}

	
	@Override
	public ApuntePorDefectoForm getApuntePorDefecto(Integer idUsuario) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		ApuntePorDefecto apuntePorDefecto=new ApuntePorDefecto();
		ApuntePorDefectoForm apuntePorDefectoForm=new ApuntePorDefectoForm();
		apuntePorDefectoForm.setIdUsuario(idUsuario);
		
		try{
			transaction=session.beginTransaction();
			
			Query query=session.createQuery(GET_APUNTEPORDEFECTO_QUERY);
			query.setParameter("idUsuario", new Integer(idUsuario), Hibernate.INTEGER);
			
			apuntePorDefecto=(ApuntePorDefecto)query.uniqueResult();
			apuntePorDefectoForm.setId(apuntePorDefecto.getIdApuntePorDefecto());	
			apuntePorDefectoForm.setIdContabilidad(apuntePorDefecto.getContabilidadPorDefecto()!=null ? apuntePorDefecto.getContabilidadPorDefecto().getIdContabilidad() : new Integer("0"));
			apuntePorDefectoForm.setIdCuenta(apuntePorDefecto.getCuentaPorDefecto()!=null ? apuntePorDefecto.getCuentaPorDefecto().getIdCuenta() : new Integer ("0"));
			apuntePorDefectoForm.setIdGrupo(apuntePorDefecto.getGrupoPorDefecto()!=null ? apuntePorDefecto.getGrupoPorDefecto().getIdGrupo() : new Integer ("0"));
			apuntePorDefectoForm.setIdClase(apuntePorDefecto.getClasePorDefecto()!=null ? apuntePorDefecto.getClasePorDefecto().getIdClase() : new Integer ("0"));
			apuntePorDefectoForm.setTipoApunte(apuntePorDefecto.getTipoOperacion());
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		return apuntePorDefectoForm;
	}

	@Override
	public void saveApuntePorDefecto(ApuntePorDefectoForm apuntePorDefectoForm) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
						
		try{
			transaction=session.beginTransaction();
			
			ApuntePorDefecto apuntePorDefecto=new ApuntePorDefecto();
			
			apuntePorDefecto.setIdApuntePorDefecto(apuntePorDefectoForm.getId());
			
			Usuario usuario=(Usuario)session.get(Usuario.class, apuntePorDefectoForm.getIdUsuario());
			apuntePorDefecto.setUsuario(usuario);
			
			Contabilidad contabilidad=(Contabilidad)session.get(Contabilidad.class, apuntePorDefectoForm.getIdContabilidad());
			apuntePorDefecto.setContabilidadPorDefecto(contabilidad);
			
			Cuenta cuenta =(Cuenta)session.get(Cuenta.class, apuntePorDefectoForm.getIdCuenta());
			apuntePorDefecto.setCuentaPorDefecto(cuenta);
			
			Grupo grupo = (Grupo)session.get(Grupo.class, apuntePorDefectoForm.getIdGrupo());
			apuntePorDefecto.setGrupoPorDefecto(grupo);
			
			Clase clase = (Clase)session.get(Clase.class, apuntePorDefectoForm.getIdClase());
			apuntePorDefecto.setClasePorDefecto(clase);
			
			apuntePorDefecto.setTipoOperacion(apuntePorDefectoForm.getTipoApunte());
			
			session.merge(apuntePorDefecto);
			
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
	
	
}
