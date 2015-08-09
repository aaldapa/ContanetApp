package com.tutorial.struts.actions.grupo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.clase.ClaseListForm;
import com.tutorial.struts.forms.grupo.GrupoForm;
import com.tutorial.struts.forms.grupo.GrupoListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerGrupoAction extends CustomBaseAction {

	
	public ActionForward execute (ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response){
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		GrupoListForm formulario=(GrupoListForm) form;
		GrupoForm grupoForm=new GrupoForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
				
		if (formulario.getAccion().equals(recursos.getMessage("grupos.listado.clases"))&& formulario.getId()!=null){
			
			List<Clase>lstClases=ServiceUtils.getIClaseService().getLstClases(formulario.getId());
			ClaseListForm claseListForm=new ClaseListForm();
			claseListForm.setGrupo(ServiceUtils.getIClaseService().cargarGrupo(formulario.getId()));
			request.setAttribute("listadoClasesForm",claseListForm);
			request.getSession().setAttribute("listadoClasesForm",claseListForm);
			request.setAttribute("listado-clases",lstClases);
			request.getSession().setAttribute("listado-clases",lstClases);
			return mapping.findForward("verClases");
		}
		
		
		if (!formulario.getAccion().equals(recursos.getMessage("grupos.listado.new"))&& formulario.getId()!=null)
			grupoForm=ServiceUtils.getIGrupoService().getGrupo(formulario.getId());
		
		grupoForm.setAccion(formulario.getAccion());
		request.setAttribute("grupoForm", grupoForm);
		return mapping.findForward("success");
		
	}
}
