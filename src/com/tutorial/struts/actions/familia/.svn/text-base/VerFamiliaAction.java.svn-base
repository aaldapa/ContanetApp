package com.tutorial.struts.actions.familia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.familia.FamiliaForm;
import com.tutorial.struts.forms.familia.FamiliaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerFamiliaAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			  HttpServletRequest request, HttpServletResponse response)
			  throws Exception 
{


		/**Programando la logica de negocio fuera del action*/
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
		return mapping.findForward("failure");
		
		FamiliaListForm formulario=(FamiliaListForm) form;
		FamiliaForm familiaForm=new FamiliaForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (!formulario.getAccion().equals(recursos.getMessage("boton.new"))&& formulario.getId()!=null){
		familiaForm=ServiceUtils.getIFamiliaService().getFamilia(formulario.getId());
		
		}
		familiaForm.setAccion(formulario.getAccion());
		request.setAttribute("familiaForm", familiaForm);
		return mapping.findForward("success");
		}
	
}
