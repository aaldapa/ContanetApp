package com.tutorial.struts.actions.clase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.struts.actions.CustomBaseAction;
import org.apache.struts.action.ActionForm;

import com.tutorial.struts.forms.clase.ClaseListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class GetListaClasesAction extends CustomBaseAction {

	
	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		ClaseListForm clasesListForm=(ClaseListForm) form;
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		/**Este Action lo utilizo para mostrar el listado desde el menu, para recargar la pagina y para mostrar 
		 * el listado una vez se ejecuta una accion desde el formulario de Clases. Al volver del formulario, si estoy visualizando
		 * los datos de una clase, el form viene cargado de datos incluido el idGrupo por el que listo las clases para 
		 * el filtrado del combo.
		 *
		 */
		List<Clase> lstClases=new ArrayList<Clase>(); 
		if (clasesListForm.getId()!=null)
			clasesListForm.setIdGrupo(0);
		
		lstClases=ServiceUtils.getIClaseService().getLstClases(clasesListForm.getIdGrupo());
		
		request.setAttribute("listadoClasesForm",clasesListForm);
		request.setAttribute("listado-clases",lstClases);
		return mapping.findForward("success");
		
	}
								
}
