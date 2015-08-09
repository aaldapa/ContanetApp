package com.tutorial.struts.actions.cuenta;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaCuentasAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		CuentaListForm cuentasListForm=(CuentaListForm) form;
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Cuenta> lstCuentas=lstCuentas=ServiceUtils.getICuentaService().getLstCuentas(loginBean.getUsuario().getIdUsuario());
		
		request.setAttribute("listadoCuentasForm",cuentasListForm);
		request.setAttribute("listado-cuentas",lstCuentas);
		request.getSession().setAttribute("listado-cuentas",lstCuentas);
		return mapping.findForward("success");
		
	}
}
