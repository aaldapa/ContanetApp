package com.tutorial.struts.actions.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.actions.CustomBaseAction;

public class LogoutAction extends CustomBaseAction 
{
  	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
    							 throws Exception 
    {
  		//Invalidamos la session
  		request.getSession().invalidate();
    	
    	return mapping.findForward("Success");
  	}
}
