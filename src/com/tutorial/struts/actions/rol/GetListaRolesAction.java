package com.tutorial.struts.actions.rol;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaRolesAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
								throws Exception
	{
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("Failure");
		
		List<Perfil> lstRoles=ServiceUtils.getIRolService().getLstRoles();
		
		request.getSession().setAttribute("listado-roles",lstRoles);
		return mapping.findForward("Success");
	}
	
}
