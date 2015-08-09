package com.tutorial.struts.actions.rol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.rol.RolForm;
import com.tutorial.struts.forms.rol.RolListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerRolAction extends CustomBaseAction {

	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página rol.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("Failure");
		
		RolListForm formulario=(RolListForm) form;
		RolForm rolForm=new RolForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (!formulario.getAccion().equals(recursos.getMessage("roles.listado.new"))&& formulario.getId()!=null)
			rolForm=ServiceUtils.getIRolService().getRoles(formulario.getId());
		 
		rolForm.setAccion(formulario.getAccion());
		request.setAttribute("rolForm", rolForm);
		return mapping.findForward("Sucess");
    }
}
