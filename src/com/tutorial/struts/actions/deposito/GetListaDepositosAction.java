package com.tutorial.struts.actions.deposito;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Deposito;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.deposito.DepositoListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaDepositosAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		DepositoListForm depositosListForm=(DepositoListForm) form;
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Deposito> lstDepositos=ServiceUtils.getIDepositoService().getLstDepositos(loginBean.getUsuario().getIdUsuario());
		
		request.setAttribute("listadoDepositosForm",depositosListForm);
		request.setAttribute("listado-depositos",lstDepositos);
		request.getSession().setAttribute("listado-depositos",lstDepositos);
		return mapping.findForward("success");
		
	}
}
