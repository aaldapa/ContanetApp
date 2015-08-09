package com.tutorial.struts.service.contabilidad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.ContabilidadUsuario;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.contabilidad.ContabilidadForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;
import com.tutorial.struts.service.ServiceUtils;

public class ContabilidadServiceImpl implements IContabilidadService {

	private static final long serialVersionUID = 872905902784301462L;

	private static final String LISTA_CONTABILIDADES_QUERY = 
			" select cu.contabilidad from ContabilidadUsuario cu " +
			" where cu.usuario.id=:usuario "+ 
			" and cu.contabilidad.baja='N'" +
			" and cu.baja='N' " +
			" order by cu.contabilidad.idContabilidad";
	
	private static final String LISTA_CONTABILIDADES_ALL_QUERY = 
		" from Contabilidad contabilidad " +
		" where contabilidad.baja='N' ";
	
	private static final String CONTABILIDAD_PREDETERMINADA_QUERY=
			" select cu.contabilidad from ContabilidadUsuario cu " +
			" where cu.baja='N' " +
			" and cu.contabilidad.baja='N' " +
			" and cu.contabilidad.predeterminada='S' " +
			" and cu.usuario.idUsuario=:idUsuario";
	
	
	/**
	 * Singleton Instance of ContabilidadServiceImpl
	 */
	private static ContabilidadServiceImpl contabilidadServiceImpl = new ContabilidadServiceImpl();
	
	/**
	 * Creates Instance of {@link ContabilidadServiceImpl}
	 */
	private ContabilidadServiceImpl(){}
	
	/***
	 * Gets Instance of ContabilidadServiceImpl
	 * @return ContabilidadServiceImpl Implementation
	 */
	public static ContabilidadServiceImpl getInstance(){	
		return contabilidadServiceImpl;
	}
	/**
	 * Devuelve el listado de contabilidades a las que un usuario tiene acceso
	 * @param Integer idUsuario  
	 * @return List<Contabilidad> 
	 */
	@Override
	public List<Contabilidad> getLstContabilidades(Integer idUsuario) {
			
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Contabilidad>lstcontabilidades=null;
		try{
			transaction=session.beginTransaction();
		
			Query query=session.createQuery(LISTA_CONTABILIDADES_QUERY);
			query.setParameter("usuario", new Integer(idUsuario), Hibernate.INTEGER);
			lstcontabilidades=(List<Contabilidad>)query.list();
			session.getTransaction().commit();
			
		}

		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally {		 
			HibernateUtil.closeSession();
		 }

		return lstcontabilidades;
	}
	
	/**
	 * Devuelve la contabilidad predeterminada del usuario cuyo id se pasa
	 * como parametro
	 */
	@Override
	public Contabilidad getContabilidadPrederterminada(Integer idUsuario){
		Contabilidad contabilidad=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;

		try{
			transaction=session.beginTransaction();
		
			Query query=session.createQuery(CONTABILIDAD_PREDETERMINADA_QUERY);
			query.setParameter("idUsuario", new Integer(idUsuario), Hibernate.INTEGER);
			contabilidad=(Contabilidad)query.uniqueResult();
			session.getTransaction().commit();
			
		}

		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return contabilidad;
	}
	
	@Override
	public ContabilidadForm getContabilidad(Integer id, Integer idUsuario) {
		
		List<Usuario> lstContabilidadesUsuario=new ArrayList<Usuario>();
		lstContabilidadesUsuario.addAll(ServiceUtils.getIUsuarioService().getLstUsuariosAccesoContabilidad(id));
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Contabilidad contabilidad=null;
		
		ContabilidadForm contabilidadForm=new ContabilidadForm();
		
		boolean rollback = true;
		try{
			
			transaction=session.beginTransaction();
			contabilidad=(Contabilidad)session.get(Contabilidad.class, id);
						
			contabilidadForm.setId(contabilidad.getIdContabilidad());
			contabilidadForm.setNombre(contabilidad.getNombreContabilidad());
			contabilidadForm.setNotas(contabilidad.getNotas());
			contabilidadForm.setPredeterminada(contabilidad.getPredeterminada());
			contabilidadForm.setBaja(contabilidad.getBaja());
			
			for (Usuario usuario:lstContabilidadesUsuario)
				contabilidadForm.getLstIdsContabilidadesUsuario().add(usuario.getIdUsuario());
			
			//Si no tiene familia añado a la lista al usuario de la aplicacion
			if (lstContabilidadesUsuario.isEmpty()){
				lstContabilidadesUsuario.add((Usuario)session.get(Usuario.class, idUsuario));
			}
			
			session.flush();
			session.getTransaction().commit();
			rollback = false;
			
		}
		finally{
			HibernateUtil.closeSession();
			if( rollback && transaction != null){ 
				transaction.rollback();
			}		
		}
		return contabilidadForm;
	}
	
	
	
	@Override
	public void deleteContabilidad(Integer id,Integer idUsuario) {
		Contabilidad contabilidad=null;
		List<Contabilidad>lstContabilidades=this.getLstContabilidades(idUsuario);
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		boolean rollback = true;
		try{
			
			transaction=session.beginTransaction();
			contabilidad=(Contabilidad)session.get(Contabilidad.class, id);
			contabilidad.setBaja("S");
			
			//Se da de baja en cascada todas las contabilidadesUsuario
			Iterator iteradorContabilidadUsuarios=contabilidad.getContabilidadesUsuarioses().iterator();
			
			while(iteradorContabilidadUsuarios.hasNext()){
				ContabilidadUsuario contabilidadUsuario=(ContabilidadUsuario)iteradorContabilidadUsuarios.next();
				contabilidadUsuario.setBaja("S");
			}
			
			session.merge(contabilidad);
			
			//Si la contabilidad que queremos borrar era la predeterminada, colocamos otra como predeterminada
			if (contabilidad.getPredeterminada().equalsIgnoreCase("S")){
				
				for (Contabilidad contabilidadAPredeterminar:lstContabilidades){
					if (contabilidadAPredeterminar.getIdContabilidad().compareTo(contabilidad.getIdContabilidad())!=0){
						contabilidadAPredeterminar.setPredeterminada("S");
						session.merge(contabilidadAPredeterminar);
						break;
					}
				}
			}
			
			
			session.flush();
			session.getTransaction().commit();
			rollback = false;
		}
		catch (Exception error) {
			System.out.println(error);
			if( rollback && transaction != null) 
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
		
		
	}

	@Override
	public void saveContabilidad(ContabilidadForm contabilidadForm,Integer idUsuario) {
				
		/* Tratamiento para establecer o no la contabilidad como predeterminada
		 * 1.- Se selecciona como predeterminada
		 * Se ponen todas las contabilidades como no predeterminadas
		 * 2.- Si no esta marcada como predeterminada
		 * Si no existe ninguna predeterminada, se pone a predeterminada
		 */
	
		if (contabilidadForm.getPredeterminada()==null)
			contabilidadForm.setPredeterminada(this.isPredeterminada(idUsuario, contabilidadForm.getId()));
		else
			this.deletePredeterminadaUsuario(idUsuario);
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
						
		try{
			transaction=session.beginTransaction();
			
			//Preparo los objetos necesario para el salvado
			Contabilidad contabilidad=new Contabilidad();

			if (contabilidadForm.getId()!=null && contabilidadForm.getId()>0)
				contabilidad.setIdContabilidad(contabilidadForm.getId());
			else
				//Si es una contabilidad nueva guardamos el usuario como usuario creador de la contabilidad
				contabilidad.setCreador((Usuario)session.get(Usuario.class, idUsuario));
			
			contabilidad.setNombreContabilidad(contabilidadForm.getNombre());
			contabilidad.setNotas(contabilidadForm.getNotas());
			contabilidad.setPredeterminada(contabilidadForm.getPredeterminada());
			contabilidad.setBaja("N");
		
			session.saveOrUpdate(contabilidad);
			
			String arrayIdsSelected[]= new String [0];
			if (contabilidadForm.getIdsUsuariosSelected()!=null)
				arrayIdsSelected=contabilidadForm.getIdsUsuariosSelected();
			
			//***Guardar contabilidadesUsuario****/
			//Si se trata de una nueva contabilidad
			
			if (contabilidadForm.getId()==null || contabilidadForm.getId()==0){
				for (int i=0;i<arrayIdsSelected.length;i++){
					Usuario usuario=(Usuario) session.load(Usuario.class, new Integer(arrayIdsSelected[i]));
					
					ContabilidadUsuario contabilidadUsuario=new ContabilidadUsuario();
					contabilidadUsuario.setContabilidad(contabilidad);
					contabilidadUsuario.setUsuario(usuario);
					contabilidadUsuario.setBaja("N");
					
					session.merge(contabilidadUsuario);
				}
			}
			else{
				//Si se trata de una modificacion, primero recojo usuarios que tienen acceso a la contabilidad
				List<Usuario> lstContabilidadesUsuario=ServiceUtils.getIUsuarioService().getLstUsuariosAccesoContabilidad(contabilidadForm.getId());
				
				//Comparo las recogidas con las seleccionadas para ver cuales tengo que dar de BAJA
				for (Usuario usuario:lstContabilidadesUsuario){
					Boolean selected=false;
					//Si existe algun usuario seleccionado los recorro para comparar
					if (arrayIdsSelected!=null){
						for (int i=0;i<arrayIdsSelected.length;i++){
							if (arrayIdsSelected[i].equals(usuario.getIdUsuario().toString())){
								selected=true;
							}
						}
					}
					//Si no esta seleccionado hay que dar de baja en la bd la contabilidadUsuario
					if (!selected){
						Query query=session.createQuery(" from ContabilidadUsuario cu" +
								" where cu.usuario.idUsuario=:idUsuario " +
								" and cu.contabilidad.idContabilidad=:idContabilidad " +
								" and cu.baja='N' ");
						query.setParameter("idUsuario",usuario.getIdUsuario(),Hibernate.INTEGER);
						query.setParameter("idContabilidad", contabilidadForm.getId());
						ContabilidadUsuario contabilidadUsuario=(ContabilidadUsuario)query.list().get(0);
						contabilidadUsuario.setBaja("S");
						session.merge(contabilidadUsuario);
					}
				}
				
				//Comparo los recogidos con los seleccionados para ver cuales tengo que dar de ALTA
				//Si no existe ninguno seleccionado no hay que dar de alta ninguno 
				if (arrayIdsSelected!=null){
					for (int i=0;i<arrayIdsSelected.length;i++){
						Boolean isPresent=false;
						for (Usuario usuario:lstContabilidadesUsuario){
							if (arrayIdsSelected[i].equals(usuario.getIdUsuario().toString())){
								isPresent=true;
							}
						}
						//Si uno de los usuarios seleccionados no esta en bd hay que dar de ALTA al contabilidadUsuario
						if (!isPresent){
							Usuario usuario=(Usuario)session.load(Usuario.class,new Integer(arrayIdsSelected[i]));
							
							Contabilidad contabilidadBd=(Contabilidad)session.load(Contabilidad.class, contabilidadForm.getId());
							ContabilidadUsuario contabilidadUsuario=new ContabilidadUsuario();
							contabilidadUsuario.setUsuario(usuario);
							contabilidadUsuario.setContabilidad(contabilidadBd);
							contabilidadUsuario.setBaja("N");
							
							session.merge(contabilidadUsuario);
							
						}
					}
				}
			}
				
			session.flush();
			transaction.commit();
		}
		catch (Exception error) {
			System.out.println(error);
			transaction.rollback();

		}
		finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<Contabilidad> getLstContabilidadesAll() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Contabilidad>lstcontabilidades=null;
		try{
			transaction=session.beginTransaction();
		
			Query query=session.createQuery(LISTA_CONTABILIDADES_ALL_QUERY);
			lstcontabilidades=(List<Contabilidad>)query.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return lstcontabilidades;
	}

	private String isPredeterminada(Integer idUsuario, Integer idContabilidad){
		List<Contabilidad>lstContabilidades=this.getLstContabilidades(idUsuario);
		if (lstContabilidades.isEmpty())
			return "S";
		else{
			for(Contabilidad contabilidad:lstContabilidades){
				if (contabilidad.getIdContabilidad().compareTo(idContabilidad)!=0){
					if (contabilidad.getPredeterminada().equalsIgnoreCase("S"))
						return "N";
				}
			}
			//Si era la predeterminada pongo a otra como predeterminada
			for(Contabilidad contabilidad:lstContabilidades){
				if (contabilidad.getIdContabilidad().intValue()==idContabilidad.intValue()){
					if(contabilidad.getPredeterminada().equalsIgnoreCase("S")){
						Session session=HibernateUtil.currentSession();
						Transaction transaction = null;
						boolean rollback = true;
						try{
							
							transaction=session.beginTransaction();
												
							//Si la contabilidad que queremos borrar era la predeterminada, colocamos otra como predeterminada
							if (contabilidad.getPredeterminada().equalsIgnoreCase("S")){
								
								for (Contabilidad contabilidadAPredeterminar:lstContabilidades){
									if (contabilidadAPredeterminar.getIdContabilidad().compareTo(contabilidad.getIdContabilidad())!=0){
										contabilidadAPredeterminar.setPredeterminada("S");
										session.merge(contabilidadAPredeterminar);
										break;
									}
								}
							}
							
							session.flush();
							session.getTransaction().commit();
							rollback = false;
						}
						catch (Exception error) {
							System.out.println(error);
							if( rollback && transaction != null) 
								transaction.rollback();
						}
						finally{
							HibernateUtil.closeSession();
						}
						return "N";
					}
				}
			}
		}
		return "S";
	}
	
	private void deletePredeterminadaUsuario(Integer idUsuario){
		List<Contabilidad> lstContabilidades=new ArrayList<Contabilidad>();
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		boolean rollback = true;
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery("from Contabilidad contabilidad " +
					" where contabilidad.id in " +
					" (select cu.contabilidad.idContabilidad from ContabilidadUsuario cu" +
					" where  cu.usuario.idUsuario=:idUsuario " +
					" and cu.baja='N') " +
					" and contabilidad.predeterminada='S'" +
					" and contabilidad.baja='N'");
			query.setParameter("idUsuario",idUsuario,Hibernate.INTEGER);
			
			
			lstContabilidades=(List<Contabilidad>)query.list();
			for (Contabilidad contabilidad:lstContabilidades){
				if (contabilidad.getPredeterminada().equalsIgnoreCase("S")){
					contabilidad.setPredeterminada("N");
					session.merge(contabilidad);
				}	
			}
			
			session.flush();
			session.getTransaction().commit();
			rollback = false;
		}
		catch (Exception error) {
			if( rollback && transaction != null) 
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<Contabilidad> getLstContabilidadesSqlIn(List<String> lstIdsContabilidades, Integer idUsuario) {
		List<Contabilidad> lstContabilidadesSqlIn=new ArrayList<Contabilidad>();
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		//Contruccion de la sql
		StringBuffer sql=new StringBuffer();
		sql.append(" select cu.contabilidad from ContabilidadUsuario cu " +
			" where cu.usuario.id=:idUsuario "+ 
			" and cu.contabilidad.baja='N'" +
			" and cu.baja='N' " +
			" and cu.contabilidad.idContabilidad in ( ");
		
		for (int cont=0;cont<lstIdsContabilidades.size();cont++){	
			if (cont>0)
				sql.append(" , ");
				
			sql.append(lstIdsContabilidades.get(cont));
			
		}
		sql.append(" )");
		sql.append(" order by cu.contabilidad.nombreContabilidad");
		
		try{
			transaction=session.beginTransaction();
			
			Query query=session.createQuery(sql.toString());
			query.setParameter("idUsuario",idUsuario,Hibernate.INTEGER);
			lstContabilidadesSqlIn=(List<Contabilidad>)query.list();
									
			session.flush();
			session.getTransaction().commit();
			
		}
		catch (Exception error) {
			if( transaction != null) 
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return lstContabilidadesSqlIn;
	}
	
}
