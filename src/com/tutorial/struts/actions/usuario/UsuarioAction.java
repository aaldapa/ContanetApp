package com.tutorial.struts.actions.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.usuario.UsuarioForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class UsuarioAction extends CustomBaseAction {

	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página usuario.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response){	
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("Failure");
		
		UsuarioForm formulario=(UsuarioForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("usuarios.listado.new"))
					||formulario.getAccion().equals(recursos.getMessage("usuarios.listado.update")))
				//Llamada al metodo que guarda la contabilidad
				ServiceUtils.getIUsuarioService().saveUsuario(formulario);
			
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("usuarios.listado.delete"))){
				//Llamamos al metodo que elimina el registro de la base de datos			
				ServiceUtils.getIUsuarioService().deleteUsuario(formulario.getId());
			}
		}
		
		return mapping.findForward("Sucess");
    }
	
}
