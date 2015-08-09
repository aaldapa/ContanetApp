package com.tutorial.struts.actions.contabilidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.contabilidad.ContabilidadForm;
import com.tutorial.struts.forms.contabilidad.ContabilidadListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class ContabilidadAction extends CustomBaseAction
{
	
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
		
		ContabilidadForm formulario=(ContabilidadForm) form;
		ContabilidadListForm contabilidadListForm=new ContabilidadListForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (formulario.getAccion()!=null){
			//Si el usuario no tiene perfil de administrador (idPerfil=1) No puede hacer CRUD
			//if (loginBean.getUsuario().getPerfil().getIdPerfil()==1){
				//Si la accion pulsada ha sido nuevo o modificar
				if (formulario.getAccion().equals(recursos.getMessage("contabilidades.listado.new"))
						||formulario.getAccion().equals(recursos.getMessage("contabilidades.listado.update")))
					//Llamada al metodo que guarda la contabilidad
					ServiceUtils.getIContabilidadService().saveContabilidad(formulario,loginBean.getUsuario().getIdUsuario());
				//Si la accion pulsada ha sido eliminar
				if (formulario.getAccion().equals(recursos.getMessage("contabilidades.listado.delete")))
					//Llamamos al metodo que guarda una "S" el campo baja			
					ServiceUtils.getIContabilidadService().deleteContabilidad(formulario.getId(),loginBean.getUsuario().getIdUsuario());
			//}
			//Si no dispone de permisos, se muestra error
			/*else{
				ActionErrors errores = new ActionErrors();
				errores.add(Globals.ERROR_KEY, new ActionMessage("error.contabilidades.formulario.sin.permisos"));
				saveErrors(request, errores);
				return mapping.findForward("unauthorized");
			}*/
		}

		request.setAttribute("contabilidadForm", contabilidadListForm);
		return mapping.findForward("success");
		
    }
}
