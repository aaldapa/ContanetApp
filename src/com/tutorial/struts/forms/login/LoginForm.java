package com.tutorial.struts.forms.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Bean de formulario para que el usuario se valide.
 **/
public class LoginForm extends ActionForm 
{
  	private String password = null;
  	private String email = null;

  	public void setEmail(String email) 
  	{
	    this.email = email;
  	}
	
  	public String getEmail() 
  	{
	    return (this.email);
  	}
	
  	public String getPassword() 
  	{
	    return (this.password);
  	}
	
  	public void setPassword(String password) 
  	{
	    this.password = password;
  	}
	
  	/**
   	 * Validamos las propiedades que se han establecido para esta petición HTTP,
   	 * y devolvemos un objeto <code>ActionErrors</code> que encapsula cualquier
   	 * error de validación que encontremos. Si no se encuentran errores, devolvemos
   	 * <code>null</code> o un objeto <code>ActionErrors</code> sin mensajes de error.
   	 **/
  	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
  	{
    	ActionErrors errors = new ActionErrors();
	
	    if (getEmail() == null || getEmail().length() < 1) 
	    {
	      	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("global.required", "email"));
    	}
    	
    	if(getPassword() == null || getPassword().length() < 1) 
    	{
      		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("global.required", "password"));
    	}
    	
    	return errors;
  	}
	
  	/**
   	 * Resetea todas las propiedades a sus valores por defecto.
   	 */
  	public void reset(ActionMapping mapping, HttpServletRequest request) 
  	{
    	this.password = null;
    	this.email = null;
  	}

}
