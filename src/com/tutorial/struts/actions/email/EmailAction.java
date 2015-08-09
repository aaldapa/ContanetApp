package com.tutorial.struts.actions.email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.utils.LoginBean;

public class EmailAction extends CustomBaseAction {
	
	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
	
	//Si no esta logado cargo mensaje error desde CustomBaseAction
	LoginBean loginBean=comprobarLogin(request);
	if (loginBean==null)
	return mapping.findForward("failure");
	
	
	return mapping.findForward("success");
	}

}
