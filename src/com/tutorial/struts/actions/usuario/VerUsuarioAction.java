package com.tutorial.struts.actions.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.usuario.UsuarioForm;
import com.tutorial.struts.forms.usuario.UsuarioListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerUsuarioAction extends CustomBaseAction {

	/**
   	 * Este método simplemente reenvia al estado de Success,
   	 * el cual debería representar la página usuario.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("Failure");
		
		UsuarioListForm formulario=(UsuarioListForm) form;
		UsuarioForm usuarioForm=new UsuarioForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (!formulario.getAccion().equals(recursos.getMessage("usuarios.listado.new"))&& formulario.getId()!=null){
			usuarioForm=ServiceUtils.getIUsuarioService().getUsuario(formulario.getId());
		
		 }
		usuarioForm.setAccion(formulario.getAccion());
		//cargo el listado de roles
		//usuarioForm.setLstRoles(ServiceUtils.getIUsuarioService().getLstRoles());
		request.setAttribute("usuarioForm", usuarioForm);
		return mapping.findForward("Sucess");
    }
	
}
