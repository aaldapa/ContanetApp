package com.tutorial.struts.service.cuenta;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibm.icu.math.BigDecimal;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.TmaeBanco;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.cuenta.CuentaForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.NumeroUtil;

public class CuentaServiceImpl implements ICuentaService {

	private static final  String LISTA_CUENTAS_USUARIO_QUERY = 
		" from Cuenta cuenta" +
		" where cuenta.contabilidad.idContabilidad in " +
		" (select cu.contabilidad.idContabilidad from ContabilidadUsuario cu " +
		" where cu.usuario.idUsuario=:idUsuario " +
		" and cu.usuario.baja='N'" +
		" and cu.baja='N') " +
		" and cuenta.contabilidad.baja='N'" +
		" and cuenta.baja='N' ";
		
	private static final  String LISTA_CUENTAS_POR_CONTABILIDAD_QUERY =
		" from Cuenta cta " +
		" inner join fetch cta.contabilidad as contabilidad " +
		" inner join fetch cta.creador " +
		" where cta.baja='N' " +
		" and cta.contabilidad.idContabilidad=:idContabilidad";
	
	private static final  String BALANCE_POR_CUENTA_QUERY =
	" select SUM(a.importe)+a.cuenta.saldoInicial from Apunte a " +
	" where a.cuenta.idCuenta=:idCuenta " +
	" and a.cuenta.baja='N'" +
	" and a.baja='N'";
	
	/**
	 * Singleton Instance of CuentaServiceImpl
	 */
	private static CuentaServiceImpl cuentaServiceImpl = new CuentaServiceImpl();
	
	/**
	 * Creates Instance of {@link CuentaServiceImpl}
	 */
	private CuentaServiceImpl(){		
	}
	
	/***
	 * Gets Instance of CuentaServiceImpl
	 * @return CuentaServiceImpl Implementation
	 */
	public static CuentaServiceImpl getInstance(){	
		return cuentaServiceImpl;
	}
	/**
	 * Devuelve el listado de cuentas a las que tiene acceso el usuario
	 */
	@Override
	public List<Cuenta> getLstCuentas(Integer idUsuario) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Cuenta> lstCuentas=null;
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
						
			query=session.createQuery(LISTA_CUENTAS_USUARIO_QUERY);
			query.setParameter("idUsuario", idUsuario, Hibernate.INTEGER);
			
			lstCuentas=(List<Cuenta>)query.list();
			
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
		
		return lstCuentas;
	}

	/**
	 * Devuelve un cuentaForm para cargar el formulario de cuenta
	 */
	@Override
	public CuentaForm getCuenta(Integer id) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Cuenta cuenta=null;
		
		CuentaForm cuentaForm=new CuentaForm();
		
		try{
			transaction=session.beginTransaction();					
			cuenta=(Cuenta)session.get(Cuenta.class, id);
			
			cuentaForm.setId(cuenta.getIdCuenta());
			
			cuentaForm.setIdContabilidad(cuenta.getContabilidad().getIdContabilidad());
			cuentaForm.setIdBanco(cuenta.getBanco().getidBanco());
			cuentaForm.setFechaAperturaStr(FechaUtil.formatear(cuenta.getFechaApertura(), new StringBuffer(), new FieldPosition(0)).toString());
			cuentaForm.setTitular(cuenta.getTitular());
			cuentaForm.setDniTitular(cuenta.getDniTitular());
			cuentaForm.setTitular2(cuenta.getTitular2());
			cuentaForm.setDniTitular2(cuenta.getDniTitular2());
			cuentaForm.setSaldoInicial(cuenta.getSaldoInicial());
			
			cuentaForm.setEntidad(cuenta.getEntidad());
			cuentaForm.setSucursal(cuenta.getSucursal());
			cuentaForm.setDc(cuenta.getDc());
			cuentaForm.setNumCuenta(cuenta.getNumCuenta());
			
			cuentaForm.setDescripcion(cuenta.getNotas());
						
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
		
		return cuentaForm;

	}

	@Override
	public void saveCuenta(CuentaForm cuentaForm, Integer idUsuario) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		try{
			transaction=session.beginTransaction();
			
			Cuenta cuenta=new Cuenta();
			Contabilidad contabilidad =(Contabilidad)session.get(Contabilidad.class, cuentaForm.getIdContabilidad());
			if (cuentaForm.getId()!=null && cuentaForm.getId()>0)
				cuenta.setIdCuenta(cuentaForm.getId());
			
			cuenta.setCreador((Usuario)session.get(Usuario.class, idUsuario));
			cuenta.setBanco((TmaeBanco)session.get(TmaeBanco.class, cuentaForm.getIdBanco()));
			
			cuenta.setFechaApertura(FechaUtil.formatearADate(cuentaForm.getFechaAperturaStr()));
			cuenta.setContabilidad(contabilidad);
			cuenta.setDniTitular(cuentaForm.getDniTitular());
			cuenta.setTitular(cuentaForm.getTitular());
			cuenta.setDniTitular2(cuentaForm.getDniTitular2());
			cuenta.setTitular2(cuentaForm.getTitular2());
			
			cuenta.setSaldoInicial(NumeroUtil.formatearImporteComaAPunto(cuentaForm.getSaldoInicialStr()));
			
			cuenta.setEntidad(cuentaForm.getEntidad());
			cuenta.setSucursal(cuentaForm.getSucursal());
			cuenta.setDc(cuentaForm.getDc());
			cuenta.setNumCuenta(cuentaForm.getNumCuenta());
			
			cuenta.setNotas(cuentaForm.getDescripcion());
			cuenta.setBaja("N");
			
			session.saveOrUpdate(cuenta);
			
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
	public void deleteCuenta(Integer id) {
		Cuenta cuenta=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			cuenta=(Cuenta)session.get(Cuenta.class, id);
			cuenta.setBaja("S");
							
			session.merge(cuenta);
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
	 *Devuelve todas las cuentas relacionadas con una contabilidad 
	 */
	@Override
	public List<Cuenta> getLstCuentasPorContabilidad(Integer idContabilidad) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_CUENTAS_POR_CONTABILIDAD_QUERY);
			query.setParameter("idContabilidad", new Integer(idContabilidad), Hibernate.INTEGER);
			lstCuentas=(List<Cuenta>)query.list();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println(e);
		}		
		return lstCuentas;
	}
	/**
	 *Devuelve el balance (la suma de apuntes contables mas el saldo inicial de una cuenta) 
	 */
	@Override
	public String getBalanceCuenta(Integer idCuenta) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		String balance=new String("0");
		BigDecimal decimalBalance=new BigDecimal(balance);
		
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(BALANCE_POR_CUENTA_QUERY);
			query.setParameter("idCuenta", new Integer(idCuenta), Hibernate.INTEGER);
			balance=query.uniqueResult().toString();
			decimalBalance=new BigDecimal(balance).setScale(2,BigDecimal.ROUND_HALF_UP);
			
			System.out.println(decimalBalance);
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println(e);
		}		
		return decimalBalance.toString();
	}

	/**
	 * Devuelve la cuenta de la base de datos cuyo id sea el pasado por parametro
	 */
	@Override
	public Cuenta cargarCuenta(Integer idCuenta) {
		Cuenta cuenta=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			cuenta=(Cuenta)session.get(Cuenta.class, idCuenta);
			
		}
		catch (Exception e) {
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return cuenta;
	}
	
	@Override
	public Contabilidad cargarContabilidad(Integer idContabilidad) {
		Contabilidad contabilidad=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			contabilidad=(Contabilidad)session.get(Contabilidad.class, idContabilidad);
			
		}
		catch (Exception e) {
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return contabilidad;
	}

	@Override
	public List<TmaeBanco> getLstBancos() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<TmaeBanco> lstBancos=new ArrayList<TmaeBanco>();
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(" from TmaeBanco");
			lstBancos=(List<TmaeBanco>)query.list();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println(e);
		}		
		return lstBancos;
	}
	
}
