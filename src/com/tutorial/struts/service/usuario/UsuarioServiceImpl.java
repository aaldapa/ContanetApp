package com.tutorial.struts.service.usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.ContabilidadUsuario;
import com.tutorial.domain.entidades.baseDatos.Familia;
import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.usuario.UsuarioForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;


public class UsuarioServiceImpl implements IUsuarioService {

	private static final  String LISTA_USUARIOS_QUERY =
			" from Usuario usuario " +
			" inner join fetch usuario.perfil as perfil" +
			" left join fetch usuario.familia as familia" +
			" where usuario.baja='N' order by usuario.familia.nombre";
	
	private static final String LISTA_CONTABILIDADES_USUARIO_QUERY=
		" select contabilidadUsuario.contabilidad from ContabilidadUsuario contabilidadUsuario " +
		" where contabilidadUsuario.usuario=:idUsuario " +
		" and contabilidadUsuario.contabilidad.baja='N'" +
		" and contabilidadUsuario.baja='N'";
	
	private static final String LISTA_USUARIOS_CONTABILIDADES_QUERY=
		" select contabilidadUsuario.usuario from ContabilidadUsuario contabilidadUsuario " +
		" where contabilidadUsuario.contabilidad=:idContabilidad " +
		" and contabilidadUsuario.contabilidad.baja='N'" +
		" and contabilidadUsuario.baja='N'";
	
	/**
	 * Singleton Instance of UsuarioServiceImpl
	 */
	private static UsuarioServiceImpl usuarioServiceImpl = new UsuarioServiceImpl();
	
	/**
	 * Creates Instance of {@link UsuarioServiceImpl}
	 */
	private UsuarioServiceImpl(){		
	}
	
	/***
	 * Gets Instance of UsuarioServiceImpl
	 * @return UsuarioServiceImpl Implementation
	 */
	public static UsuarioServiceImpl getInstance(){	
		return usuarioServiceImpl;
	}

	@Override
	public List<Usuario> getLstUsuarios() {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Usuario> lstUsuarios=null;
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_USUARIOS_QUERY);
			lstUsuarios=(List<Usuario>)query.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		return lstUsuarios;
	}
	
	@Override
	public UsuarioForm getUsuario(Integer id) {
		
		List<Contabilidad> lstContabilidades=this.getLstContabilidadesAccesoUsuario(id);
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Usuario usuario=null;
		UsuarioForm usuarioForm=new UsuarioForm();
		
		try{
			
			transaction=session.beginTransaction();
									
			usuario=(Usuario)session.get(Usuario.class, id);
			
			usuarioForm.setId(usuario.getIdUsuario());
			usuarioForm.setNombre(usuario.getNombre());
			usuarioForm.setApellidos(usuario.getApellidos());
			usuarioForm.setEmail(usuario.getEmail());
			usuarioForm.setPassword(usuario.getPassword());
			usuarioForm.setIdPerfil(usuario.getPerfil().getIdPerfil());
			usuarioForm.setIdFamilia(usuario.getFamilia().getIdFamilia());
			
			for (Contabilidad contabilidad:lstContabilidades)
				usuarioForm.getLstIdsContabilidadesUsuario().add(contabilidad.getIdContabilidad());
			
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
		
		return usuarioForm;
	}
	
	@Override
	public void deleteUsuario(Integer id) {
		Usuario usuario=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
	
		try{
			transaction=session.beginTransaction();
			usuario=(Usuario)session.get(Usuario.class, id);
			usuario.setBaja("S");
			//Se da de baja en cascada todas las contabilidadesUsuario
			Iterator iteradorContabilidadUsuarios=usuario.getContabilidadesUsuarioses().iterator();
			
			while(iteradorContabilidadUsuarios.hasNext()){
				ContabilidadUsuario contabilidadUsuario=(ContabilidadUsuario)iteradorContabilidadUsuarios.next();
				contabilidadUsuario.setBaja("S");
			}
			
			session.merge(usuario);
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
	public void saveUsuario(UsuarioForm usuarioForm) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		try{
			transaction=session.beginTransaction();
			
			Usuario usuario=new Usuario();
			Perfil perfil=(Perfil)session.load(Perfil.class, usuarioForm.getIdPerfil());
			Familia familia=null;
			
			if (usuarioForm.getIdFamilia()!=null && usuarioForm.getIdFamilia()>0)
				familia=(Familia)session.load(Familia.class, usuarioForm.getIdFamilia());
			
			if (usuarioForm.getId()!=null && usuarioForm.getId()>0)
				usuario.setIdUsuario(usuarioForm.getId());
				
			usuario.setNombre(usuarioForm.getNombre());
			usuario.setApellidos(usuarioForm.getApellidos());
			usuario.setEmail(usuarioForm.getEmail());
			usuario.setPassword(usuarioForm.getPassword());
			usuario.setPerfil(perfil);
			usuario.setFamilia(familia);
			usuario.setBaja("N");
			
			session.saveOrUpdate(usuario);
			
			
			String arrayIdsSelected[]= new String [0];
			if (usuarioForm.getIdsContabilidadesSelected()!=null)
				arrayIdsSelected=usuarioForm.getIdsContabilidadesSelected();
			
			//***Guardar contabilidadesUsuario****/
			//Si se trata de un usuario nuevo
			
			if (usuarioForm.getId()==null || usuarioForm.getId()==0){
				for (int i=0;i<arrayIdsSelected.length;i++){
					Contabilidad contabilidad=(Contabilidad) session.load(Contabilidad.class, new Integer(arrayIdsSelected[i]));
					
					ContabilidadUsuario contabilidadUsuario=new ContabilidadUsuario();
					contabilidadUsuario.setContabilidad(contabilidad);
					contabilidadUsuario.setUsuario(usuario);
					contabilidadUsuario.setBaja("N");
					
					session.merge(contabilidadUsuario);
				}
			}
			
			else{
				//Si se trata de una modificacion, primero recojo las contabilidades a las que tenia acceso
				List<Contabilidad> lstContabilidadesAcceso=this.getLstContabilidadesAccesoUsuario(usuarioForm.getId());
				
				//Comparo las recogidas con las seleccionadas para ver cuales tengo que dar de BAJA
				for (Contabilidad contabilidad:lstContabilidadesAcceso){
					Boolean selected=false;
					//Si existe alguna contabilidad seleccionada las recorro para comparar
					if (arrayIdsSelected!=null){
						for (int i=0;i<arrayIdsSelected.length;i++){
							if (arrayIdsSelected[i].equals(contabilidad.getIdContabilidad().toString())){
								selected=true;
							}
						}
					}			
					//Si no esta seleccionada hay que dar de baja en la bd la contabilidadUsuario
					if (!selected){
						Query query=session.createQuery(" from ContabilidadUsuario cu" +
								" where cu.usuario.idUsuario=:idUsuario " +
								" and cu.contabilidad.idContabilidad=:idContabilidad " +
								" and cu.baja='N' ");
						query.setParameter("idUsuario",usuarioForm.getId(),Hibernate.INTEGER);
						query.setParameter("idContabilidad", contabilidad.getIdContabilidad());
						ContabilidadUsuario contabilidadUsuario=(ContabilidadUsuario)query.list().get(0);
						contabilidadUsuario.setBaja("S");
						session.merge(contabilidadUsuario);
					}
					
				}
				
				//Comparo las recogidas con las seleccionadas para ver cuales tengo que dar de ALTA
				//Si no existe ninguna seleccionada no hay ninguna que dar de alta
				if (arrayIdsSelected!=null){
					for (int i=0;i<arrayIdsSelected.length;i++){
						Boolean isPresent=false;
						for (Contabilidad contabilidad:lstContabilidadesAcceso){
							if (arrayIdsSelected[i].equals(contabilidad.getIdContabilidad().toString())){
								isPresent=true;
							}
						}
						//Si una de las contabilidades seleccionadas no esta seleccionada hay que dar de ALTA en la bd la contabilidadUsuario
						if (!isPresent){
							Contabilidad contabilidad=(Contabilidad)session.load(Contabilidad.class, new Integer(arrayIdsSelected[i]));
							Usuario usuarioBd=(Usuario)session.load(Usuario.class, usuarioForm.getId());
							ContabilidadUsuario contabilidadUsuario=new ContabilidadUsuario();
							contabilidadUsuario.setUsuario(usuarioBd);
							contabilidadUsuario.setContabilidad(contabilidad);
							contabilidadUsuario.setBaja("N");
							
							session.merge(contabilidadUsuario);
							
						}
					}
				}
			}
			
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
	public List<Perfil> getLstRoles() {
		List<Perfil> lstRoles=null;
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery("from Perfil");
			lstRoles=(List<Perfil>)query.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return lstRoles;
	}
	
	@Override
	public List<Contabilidad> getLstContabilidadesAccesoUsuario(Integer idUsuario){
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Contabilidad> lstContabilidades=new ArrayList<Contabilidad>();
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_CONTABILIDADES_USUARIO_QUERY);
			query.setParameter("idUsuario", new Integer(idUsuario), Hibernate.INTEGER);
			lstContabilidades=(List<Contabilidad>)query.list();
		}
		catch (Exception e) {}		
		
		return lstContabilidades;
	}

	@Override
	public List<Usuario> getLstUsuariosAccesoContabilidad (Integer idContabilidad){
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Usuario> lstUsuarios=new ArrayList<Usuario>();
		
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(LISTA_USUARIOS_CONTABILIDADES_QUERY);
			query.setParameter("idContabilidad", new Integer(idContabilidad), Hibernate.INTEGER);
			lstUsuarios=(List<Usuario>)query.list();
		}
		catch (Exception e) {}		
		
		return lstUsuarios;
	}

	@Override
	public List<Usuario> getFamiliares(Integer idUsuario) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Usuario>lstFamiliares=new ArrayList<Usuario>();
		try{
			transaction=session.beginTransaction();
			Query query=session.createQuery(" from Usuario u " +
					" inner join fetch u.perfil as perfil " +
					" left join fetch u.familia as familia " +
					" where u.familia.idFamilia in " +
					"( select fam.idFamilia from Familia fam" +
					"  where fam.idFamilia in " +
					"( select usuario.familia.idFamilia from Usuario usuario " +
					"  where usuario.idUsuario=:idUsuario) " +
					" ) ");
					
			query.setParameter("idUsuario", new Integer(idUsuario), Hibernate.INTEGER);
			lstFamiliares=(List<Usuario>)query.list();
			
			//Si no tiene familia añado a la lista al usuario de la aplicacion
			if (lstFamiliares.isEmpty()){
				lstFamiliares.add((Usuario)session.get(Usuario.class, idUsuario));
			}
			
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.out.println(e);
			if(transaction != null)
				transaction.rollback();
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return lstFamiliares;
	}

}
