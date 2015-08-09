package com.tutorial.struts.actions.contabilidad;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.contabilidad.ContabilidadListForm;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaCuentasAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		

		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		CuentaListForm cuentaListForm=(CuentaListForm) form;
		List<Cuenta> lstCuentas=ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(cuentaListForm.getContabilidad().getIdContabilidad());
		cuentaListForm.setContabilidad(ServiceUtils.getICuentaService().cargarContabilidad(cuentaListForm.getContabilidad().getIdContabilidad()));	
		
		request.setAttribute("listadoCuentasForm",cuentaListForm);
		request.setAttribute("listado-cuentas",lstCuentas);
		request.getSession().setAttribute("listado-cuentas",lstCuentas);
		return mapping.findForward("success");
		
	}
}
