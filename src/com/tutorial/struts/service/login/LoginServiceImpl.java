package com.tutorial.struts.service.login;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tutorial.struts.hibernate.util.HibernateUtil;
import com.tutorial.struts.service.ServiceException;

import com.tutorial.domain.entidades.baseDatos.Usuario;


public class LoginServiceImpl implements ILoginService{

	
	private static final long serialVersionUID = -4414688371907028109L;

	private String AUTHENTICATION_QUERY = 
		" from Usuario as usuario " +
		" inner join fetch usuario.perfil as perfil " +
		" left join fetch usuario.familia as familia "+
		" where usuario.email= :Email and usuario.password = :Password";
	
	/**
	 * Singleton Instance of UserServiceImpl
	 */
	private static LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
	
	/**
	 * Creates Instance of {@link UserServiceImpl}
	 */
	private LoginServiceImpl(){		
	}
	
	/***
	 * Gets Instance of UserService
	 * @return UserService Implementation
	 */
	public static LoginServiceImpl getInstance(){	
		return loginServiceImpl;
	}
	
	@Override
	public Usuario authenticate(String email, String password) {
		Usuario user = null;
		try {
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(AUTHENTICATION_QUERY);
			query.setString("Email", email);
			query.setString("Password", password);
			@SuppressWarnings("unchecked")
			List<Usuario> list = (List<Usuario>) query.list();
			if (list.size() == 1) {
				user = (Usuario) list.get(0);
			}
			HibernateUtil.closeSession();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return user;
	}

}
