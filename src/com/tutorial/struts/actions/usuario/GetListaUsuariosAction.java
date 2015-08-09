package com.tutorial.struts.actions.usuario;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaUsuariosAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Usuario> lstUsuarios=ServiceUtils.getIUsuarioService().getLstUsuarios();
		
		request.getSession().setAttribute("listado-usuarios",lstUsuarios);
		return mapping.findForward("success");
	}
}
