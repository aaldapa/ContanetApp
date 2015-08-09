package com.tutorial.struts.actions.familia;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Familia;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaFamiliasAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
{
		//Si no esta logado cargo mensaje error desde CustomBaseAction

		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Familia> lstFamilias=new ArrayList<Familia>();
		if (ServiceUtils.getIFamiliaService().getLstFamilias().size()>0)
			lstFamilias=ServiceUtils.getIFamiliaService().getLstFamilias();
		
		request.getSession().setAttribute("listado-familias",lstFamilias);
		return mapping.findForward("success");
	
	}
}
