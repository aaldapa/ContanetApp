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
import com.tutorial.struts.forms.cuenta.CuentaForm;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class CuentaAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			  HttpServletRequest request, HttpServletResponse response){	
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		CuentaForm formulario=(CuentaForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("cuentas.listado.new"))
				||formulario.getAccion().equals(recursos.getMessage("cuentas.listado.update")))
				//Llamada al metodo que guarda la contabilidad
				ServiceUtils.getICuentaService().saveCuenta(formulario, loginBean.getUsuario().getIdUsuario());
			
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("cuentas.listado.delete"))){
				//Llamamos al metodo que elimina el registro de la base de datos			
				ServiceUtils.getICuentaService().deleteCuenta(formulario.getId());
			}
		}
		
		CuentaListForm cuentaListForm=new CuentaListForm();
		cuentaListForm.setContabilidad(ServiceUtils.getICuentaService().cargarContabilidad(formulario.getIdContabilidad()));	
		
		request.setAttribute("listadoCuentasForm",cuentaListForm);
		return mapping.findForward("success");
	}
	
}
