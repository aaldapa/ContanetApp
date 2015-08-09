package com.tutorial.struts.actions.rol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.contabilidad.ContabilidadForm;
import com.tutorial.struts.forms.rol.RolForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class RolAction extends CustomBaseAction {

	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página contabilidad.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {

		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("Failure");
		
		RolForm formulario=(RolForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("roles.listado.new"))
					||formulario.getAccion().equals(recursos.getMessage("roles.listado.update")))
				//Llamada al metodo que guarda el rol
				ServiceUtils.getIRolService().saveRol(formulario);
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("roles.listado.delete"))){
				//Llamamos al metodo que elimina			
				ServiceUtils.getIRolService().deleteRol(formulario.getId());
			}
		}
		
		return mapping.findForward("Sucess");
    }

	
}
