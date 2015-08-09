package com.tutorial.struts.actions.contabilidad;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.cuenta.CuentaForm;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerCuentaAction extends CustomBaseAction {

	/**
   	 * Este método simplemente reenvia al estado de success,
   	 * el cual debería representar la página cuenta.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		CuentaListForm formulario=(CuentaListForm) form;
		CuentaForm cuentaForm=new CuentaForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		List<Contabilidad> lstContabilidades=new ArrayList<Contabilidad>();
		
		//Si la accion seleccionada es distinta de nuevo
		if (!formulario.getAccion().equals(recursos.getMessage("cuentas.listado.new"))&& formulario.getId()!=null){
			cuentaForm=ServiceUtils.getICuentaService().getCuenta(formulario.getId());
			lstContabilidades.add(ServiceUtils.getICuentaService().cargarContabilidad(cuentaForm.getIdContabilidad()));
		}
		//Si la accion seleccionada es nuevo
		else{
			//selecciono la contabilidad de la que vengo
			//cargo el listado de contabilidades con la contabilidad de la que se viene
			lstContabilidades.add(ServiceUtils.getICuentaService().cargarContabilidad(formulario.getContabilidad().getIdContabilidad()));
		}
		
		
		cuentaForm.setLstContabilidades(lstContabilidades);
		
		
		cuentaForm.setAccion(formulario.getAccion());
		
		//Carga de datos para la generacion de informe pdf con Ireport
		//String fileName="/report1.jrxml";
		//Map parameters = new HashMap();
	    //parameters.put("TITULO", recursos.getMessage("cuentas.listado.new"));
		//this.abrirPDF(recursos, fileName, parameters);
	  
		
		request.setAttribute("cuentaForm", cuentaForm);
		return  mapping.findForward("success");
    }
	
}
