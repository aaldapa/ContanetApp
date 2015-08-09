package com.tutorial.struts.actions.contabilidad;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.contabilidad.ContabilidadForm;
import com.tutorial.struts.forms.contabilidad.ContabilidadListForm;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerContabilidadAction extends CustomBaseAction
{
	
	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página contabilidad.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {

		/**Programando la logica de negocio fuera del action*/
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ContabilidadListForm formulario=(ContabilidadListForm) form;
		ContabilidadForm contabilidadForm=new ContabilidadForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (formulario.getAccion().equals(recursos.getMessage("contabilidades.listado.cuentas"))&& formulario.getId()!=null){
			
			List<Cuenta> lstCuentas=ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(formulario.getId());
			CuentaListForm cuentasListForm=new CuentaListForm();
			cuentasListForm.setContabilidad(ServiceUtils.getICuentaService().cargarContabilidad(formulario.getId()));
			request.setAttribute("listadoCuentasForm",cuentasListForm);
			request.setAttribute("listado-cuentas",lstCuentas);
			request.getSession().setAttribute("listado-cuentas",lstCuentas);
			return mapping.findForward("verCuentas");
		}
		
		if (!formulario.getAccion().equals(recursos.getMessage("contabilidades.listado.new"))&& formulario.getId()!=null)
			contabilidadForm=ServiceUtils.getIContabilidadService().getContabilidad(formulario.getId(),loginBean.getUsuario().getIdUsuario());
		
		contabilidadForm.setAccion(formulario.getAccion());
		contabilidadForm.setIdUsuario(loginBean.getUsuario().getIdUsuario());
		request.setAttribute("contabilidadForm", contabilidadForm);
		return mapping.findForward("success");
    }
		
}