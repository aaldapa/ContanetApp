package com.tutorial.struts.actions.deposito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.deposito.DepositoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class DepositoAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		DepositoForm formulario=(DepositoForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("boton.new"))
					||formulario.getAccion().equals(recursos.getMessage("boton.update")))
				//Llamada al metodo que guarda 
				ServiceUtils.getIDepositoService().saveDeposito(formulario);
			
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("cuentas.listado.delete"))){
				//Llamamos al metodo que elimina el registro de la base de datos			
				ServiceUtils.getIDepositoService().deleteDeposito(formulario.getId());
			}
		}

		return mapping.findForward("success");
		
	}

	
}
