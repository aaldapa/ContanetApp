package com.tutorial.struts.actions.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.login.LoginForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.Constantes;
import com.tutorial.struts.utils.LoginBean;

/**
 * Implementa la lógica para autenticar a un usuario en la aplicación.
 **/
public class LoginAction extends CustomBaseAction
{
  	/**
   	 * Se llama por el controlador cuando un usuario intenta
   	 * loguearse en la aplicación.
   	 **/
  	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
  								 throws Exception
	{
  		
  		// Obtenemos el email y password del usuario.
		// Ya deberían haber sido validados por el ActionForm.
	    String email = ((LoginForm)form).getEmail();
	    String password = ((LoginForm)form).getPassword();
	    
	    System.out.println("EMAIL = " + email);
	    System.out.println("CLAVE = " + password);
	   
	    Usuario user=ServiceUtils.getILoginService().authenticate(email, password);
	    
	    //Si el usuario introducido no se encuentra en la base de datos mostrarmos error
		if (user== null){
			ActionErrors errores = new ActionErrors();
			errores.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("global.error.invalidlogin"));
			saveErrors(request, errores);

			return mapping.findForward("Failure");
		}else
		{
			LoginBean loginBean=new LoginBean();
			loginBean.setUsuario(user);
			
			// Metemos los datos del usuario en la sesión.
            request.getSession(true).setAttribute(Constantes.LOGIN, loginBean);
           
	    	return mapping.findForward("Success");
	    }
		
		
  	}
}