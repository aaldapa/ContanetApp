package com.tutorial.struts.actions.apunte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.ApunteListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class ReorganizarApunteAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			  HttpServletRequest request, HttpServletResponse response)
			  throws Exception {
		
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ApunteListForm apunteListForm=(ApunteListForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (apunteListForm.getAccion()!=null && apunteListForm.getAccion().equals(recursos.getMessage("boton.organizar"))){
			ServiceUtils.getIApunteService().saveReorganizarApuntes(apunteListForm);
			request.getSession().setAttribute("listadoApuntesForm", apunteListForm);
			return mapping.findForward("success");
		}
		if (apunteListForm.getAccion()==null ){
			request.getSession().setAttribute("listadoApuntesForm", apunteListForm);
			return mapping.findForward("success");
		}
		
				
		request.getSession().setAttribute("listadoApuntesForm", apunteListForm);
		return mapping.findForward("recargar");
	}
	
}
