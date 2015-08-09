package com.tutorial.struts.actions.contabilidad;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.contabilidad.ContabilidadListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaContabilidadesAction extends CustomBaseAction {

	
	public ActionForward execute (ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response){
	
		/**Programando la logica de negocio fuera del action*/
		ContabilidadListForm contabilidadListForm=new ContabilidadListForm();
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Contabilidad> lstContabilidades=new ArrayList<Contabilidad>();
		
		lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario());
		request.getSession().setAttribute("contabilidadListForm",contabilidadListForm);
		request.getSession().setAttribute("listado-contabilidades",lstContabilidades);
		return mapping.findForward("success");
	}
}
