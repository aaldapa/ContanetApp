package com.tutorial.struts.actions.clase;

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

public class VerClaseAction extends CustomBaseAction {

	/**
   	 * Este m�todo simplemente reenvia al estado de Success,
   	 * el cual deber�a representar la p�gina rol.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ClaseListForm formulario=(ClaseListForm) form;
		ClaseForm claseForm=new ClaseForm();
		claseForm.setIdGrupo(formulario.getIdGrupo());
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (!formulario.getAccion().equals(recursos.getMessage("clase.listado.new"))&& formulario.getId()!=null)
			claseForm=ServiceUtils.getIClaseService().getClase(formulario.getId());
		 
		claseForm.setAccion(formulario.getAccion());
		request.setAttribute("claseForm", claseForm);
		return mapping.findForward("success");
    }
}
