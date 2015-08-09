package com.tutorial.struts.actions.apunte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class ApunteAction extends CustomBaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {

		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ApunteForm formulario=(ApunteForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("boton.new"))
					||formulario.getAccion().equals(recursos.getMessage("boton.update"))
					||formulario.getAccion().equals(recursos.getMessage("boton.copy")))
				//Llamada al metodo que guarda el clase
				ServiceUtils.getIApunteService().saveApunte(formulario);
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("boton.delete"))){
				//Llamamos al metodo que elimina			
				ServiceUtils.getIApunteService().deleteApunte(formulario.getId());
			}
		}
		
		//Inicio octubre_12 
		request.setAttribute("listadoApuntesForm", formulario.getSeleccionAnterior());
		//Fin octubre_12
		return mapping.findForward("success");
    }
}
