package com.tutorial.struts.actions.deposito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.deposito.DepositoForm;
import com.tutorial.struts.forms.deposito.DepositoListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerDepositoAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			  HttpServletRequest request, HttpServletResponse response)
			  
	{
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		DepositoListForm formulario=(DepositoListForm) form;
		DepositoForm depositoForm=new DepositoForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si la accion seleccionada es distinta de nuevo
		if (!formulario.getAccion().equals(recursos.getMessage("depositos.listado.new"))&& formulario.getId()!=null)
			depositoForm=ServiceUtils.getIDepositoService().getDeposito(formulario.getId());
				
		//Si la accion seleccionada es nuevo
		else{
			//selecciono la contabilidad predeterminada
			depositoForm.setIdContabilidad(ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario()).getIdContabilidad());
		}
		
		depositoForm.setLstBancos(ServiceUtils.getICuentaService().getLstBancos());
		depositoForm.setLstContabilidades(ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario()));
		depositoForm.setAccion(formulario.getAccion());
	
		request.setAttribute("depositoForm", depositoForm);
		return mapping.findForward("success");
	}
		
	
}
