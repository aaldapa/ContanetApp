package com.tutorial.struts.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.actions.LookupDispatchAction;

import com.tutorial.struts.utils.Constantes;
import com.tutorial.struts.utils.LoginBean;

public class CustomBaseLookupDispatch extends LookupDispatchAction {

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		return null;
	}

	// Obtenemos el objeto LoginBean con los datos del usuario que está en la sesión.
  	protected LoginBean comprobarLogin(HttpServletRequest request) 
  	{
  		LoginBean loginBean = (LoginBean)getSessionObject(request, Constantes.LOGIN);
	
	    // Creamos un UserContainer para este usuario si no existe ...
	    if(loginBean == null){	      	
	    	ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.login.failed"));
            saveErrors(request, errors);
	    	return null;
	    }else 	
	    	return loginBean;
  	}
  	
  	// Obtenemos un objeto de la sesión por su nombre.
  	protected Object getSessionObject(HttpServletRequest req, String attrName) 
  	{
	    Object sessionObj = null;
	    HttpSession session = req.getSession(false);
	    
	    if (session != null)
	       	sessionObj = session.getAttribute(attrName);
    	   	
    	return sessionObj;
  	}
	
}
