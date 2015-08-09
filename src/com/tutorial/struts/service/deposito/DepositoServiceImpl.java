package com.tutorial.struts.service.deposito;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Deposito;
import com.tutorial.domain.entidades.baseDatos.TmaeBanco;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.deposito.DepositoForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.NumeroUtil;

public class DepositoServiceImpl implements IDepositoService {

	/**
	 * Singleton Instance of CuentaServiceImpl
	 */
	private static DepositoServiceImpl depositoServiceImpl = new DepositoServiceImpl();
	
	/**
	 * Creates Instance of {@link DepositoServiceImpl}
	 */
	private DepositoServiceImpl(){		
	}
	
	/***
	 * Gets Instance of DepositoServiceImpl
	 * @return DepositoServiceImpl Implementation
	 */
	public static DepositoServiceImpl getInstance(){	
		return depositoServiceImpl;
	}

	private static final  String LISTA_DEPOSITOS_USUARIO_QUERY = 
			" from Deposito deposito" +
			" where deposito.contabilidad.idContabilidad in " +
			" (select cu.contabilidad.idContabilidad from ContabilidadUsuario cu " +
			" where cu.usuario.idUsuario=:idUsuario " +
			" and cu.usuario.baja='N'" +
			" and cu.baja='N') " +
			" and deposito.contabilidad.baja='N'" +
			" and deposito.baja='N' ";

	private static final  String LISTA_DEPOSITOS_POR_CONTABILIDAD_QUERY =
			" from Deposito deposito" +
			" inner join fetch deposito.contabilidad as contabilidad " +
			" inner join fetch deposito.idCreador " +
			" inner join fetch deposito.banco" +
			" where deposito.baja='N' " +
			" and deposito.contabilidad.idContabilidad=:idContabilidad"
			+" and deposito.fechaVencimiento>=:fecha"
			;
	
	@Override
	public List<Deposito> getLstDepositos(Integer idUsuario) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Deposito> lstDepositos=null;
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
						
			query=session.createQuery(LISTA_DEPOSITOS_USUARIO_QUERY);
			query.setParameter("idUsuario", idUsuario, Hibernate.INTEGER);
			
			lstDepositos=(List<Deposito>)query.list();
			
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
		
		return lstDepositos;
	}

	@Override
	public DepositoForm getDeposito(Integer id) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		DepositoForm depositoForm=new DepositoForm();
		
		try{
			transaction=session.beginTransaction();
			Deposito deposito=(Deposito)session.load(Deposito.class, id);
			
			depositoForm.setId(id);
			depositoForm.setIdUsuario(deposito.getIdCreador().getIdUsuario());
			depositoForm.setIdBanco(deposito.getBanco().getidBanco());
			depositoForm.setIdContabilidad(deposito.getContabilidad().getIdContabilidad());
			depositoForm.setFechaAperturaStr(FechaUtil.formatear(deposito.getFechaApertura(), new StringBuffer(), new FieldPosition(0)).toString());
			depositoForm.setFechaVencimientoStr(FechaUtil.formatear(deposito.getFechaVencimiento(), new StringBuffer(), new FieldPosition(0)).toString());
			depositoForm.setNumeroDeposito(deposito.getNumeroDeposito());
			depositoForm.setNombreDeposito(deposito.getNombreDeposito());
			depositoForm.setTitular(deposito.getTitular());
			depositoForm.setCotitular(deposito.getCotitular());
			depositoForm.setImporte(deposito.getImporte());
			depositoForm.setRentabilidad(deposito.getRentabilidad());
			depositoForm.setRentabilidadCancelacion(deposito.getRentabilidadAnticipada());
			depositoForm.setDescripcion(deposito.getDescripcion());
			
			session.flush();
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
		return depositoForm;
	}

	@Override
	public void saveDeposito(DepositoForm depositoForm) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		try{
			transaction=session.beginTransaction();
			
			Deposito deposito=new Deposito();
			
			Contabilidad contabilidad =(Contabilidad)session.get(Contabilidad.class, depositoForm.getIdContabilidad());
			if (depositoForm.getId()!=null && depositoForm.getId()>0)
				deposito.setIdDeposito(depositoForm.getId());
			
			deposito.setIdCreador((Usuario)session.get(Usuario.class, depositoForm.getIdUsuario()));
			deposito.setBanco((TmaeBanco)session.get(TmaeBanco.class, depositoForm.getIdBanco()));
			deposito.setContabilidad(contabilidad);
			
			deposito.setFechaApertura(FechaUtil.formatearADate(depositoForm.getFechaAperturaStr()));
			deposito.setFechaVencimiento(FechaUtil.formatearADate(depositoForm.getFechaVencimientoStr()));
			deposito.setTitular(depositoForm.getTitular());
			deposito.setCotitular(depositoForm.getCotitular());
			deposito.setNumeroDeposito(depositoForm.getNumeroDeposito());
			deposito.setNombreDeposito(depositoForm.getNombreDeposito());
			
			deposito.setImporte(NumeroUtil.formatearImporteComaAPunto(depositoForm.getImporteStr()));
			deposito.setRentabilidad(NumeroUtil.formatearImporteComaAPunto(depositoForm.getRentabilidadStr()));
			deposito.setRentabilidadAnticipada(NumeroUtil.formatearImporteComaAPunto(depositoForm.getRentabilidadCancelacionStr()));
			
			deposito.setDescripcion(depositoForm.getDescripcion());
			deposito.setBaja("N");
			
			session.saveOrUpdate(deposito);
			
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
	public void deleteDeposito(Integer id) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			Deposito deposito=(Deposito)session.get(Cuenta.class, id);
			deposito.setBaja("S");
							
			session.merge(deposito);
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
	
	/**
	 *Devuelve todos los depositos relacionados con una contabilidad 
	 */
	@Override
	public List<Deposito> getLstDepositosPorContabilidad(Integer idContabilidad) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Deposito> lstDepositos=new ArrayList<Deposito>();
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_DEPOSITOS_POR_CONTABILIDAD_QUERY);
			query.setParameter("idContabilidad", new Integer(idContabilidad), Hibernate.INTEGER);
			query.setParameter("fecha", FechaUtil.getDate(), Hibernate.DATE);
			lstDepositos=(List<Deposito>)query.list();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println(e);
		}		
		return lstDepositos;
	}
	
}
