package com.tutorial.struts.actions.expediente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.expediente.ExpedienteForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

/**
 * Muestra los datos del usuario logueado en la sesión.
 **/
public class GetExpedienteAction extends CustomBaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
   								 throws Exception 
	{
		//--- Compruebo que el usuario esta logado. 
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ExpedienteForm expedienteForm=(ExpedienteForm)form;
		
		if (expedienteForm!=null){
			if (expedienteForm.getLstIdsCuentasSelect()!=null && expedienteForm.getLstIdsCuentasSelect().length>0)
				expedienteForm=ServiceUtils.getIExpedienteService().getExpedienteForm(expedienteForm.getLstIdsCuentasSelect(),expedienteForm.getLstIdsDepositosSelect(), loginBean.getUsuario().getIdUsuario());
			else
				expedienteForm=ServiceUtils.getIExpedienteService().getExpedienteFormAllCuentas(loginBean.getUsuario().getIdUsuario());
		}

		request.setAttribute("expedienteForm",expedienteForm);
		// Devolvemos el ActionForward que está definido para Success
		return mapping.findForward("success");
  	}
}
