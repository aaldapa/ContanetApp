package com.tutorial.struts.actions.familia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.familia.FamiliaForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class FamiliaAction extends CustomBaseAction {
	

	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página contabilidad.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {
		/** Programando de la logica de negocio fuera del action*/
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		FamiliaForm formulario=(FamiliaForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("boton.new"))
					||formulario.getAccion().equals(recursos.getMessage("boton.update")))
					//Llamada al metodo que guarda la familia
				ServiceUtils.getIFamiliaService().saveFamilia(formulario);
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("boton.delete")))
				//Llamamos al metodo que guarda una "S" el campo baja			
				ServiceUtils.getIFamiliaService().deleteFamilia(formulario.getId());
			
		}
  		
		return mapping.findForward("success");
		
    }

}
