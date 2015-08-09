package com.tutorial.struts.actions.grupo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.clase.ClaseForm;
import com.tutorial.struts.forms.clase.ClaseListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class ClaseAction extends CustomBaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {
	
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ClaseForm formulario=(ClaseForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("clases.listado.new"))
					||formulario.getAccion().equals(recursos.getMessage("clases.listado.update")))
				//Llamada al metodo que guarda el clase
				ServiceUtils.getIClaseService().saveClase(formulario);
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("clases.listado.delete"))){
				//Llamamos al metodo que elimina			
				ServiceUtils.getIClaseService().deleteClase(formulario.getId());
			}
			
			
		}
		
		//Le paso el un 0 en el clasesListForm.getIdGrupo() para que liste todas las clases
		ClaseListForm clasesListForm=new ClaseListForm();
		clasesListForm.setIdGrupo(0);
		formulario.setIdGrupo(0);
		request.setAttribute("listadoClasesForm",clasesListForm);
		return mapping.findForward("success");
    }
}
