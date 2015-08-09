package com.tutorial.struts.actions.apuntepordefecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetApuntePorDefectoAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
				 throws Exception{
	
//--- Compruebo que el usuario esta logado. 
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ApuntePorDefectoForm apuntePorDefectoForm=ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario());
		
		request.setAttribute("apuntePorDefectoForm",apuntePorDefectoForm);
		
		return mapping.findForward("success");
	}
}
