package com.tutorial.struts.actions.apunte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.UploadApunteForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.Constantes;
import com.tutorial.struts.utils.LoginBean;

public class ImportarApunteAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		UploadApunteForm formulario=(UploadApunteForm) form;
		//Indico la ruta conde guardar el fichero seleccionado en la jsp
		String path= getServlet().getServletContext()
						.getRealPath(Constantes.CONTEXTO_RAIZ_MOVIMIENTOS).
						concat("\\")+formulario.getFile().getFileName();
		
		//Guardo fichero en el servidor
		ServiceUtils.getIApunteService().guardarFichero(path, formulario.getFile());
		
		//Guardar movimientos en tabla apuntes	
		//formulario.setApuntesInsertados(ServiceUtils.getIApunteService().cargarMovimientos(path, formulario.getIdCuenta()));
		
		Cuenta cuenta=ServiceUtils.getICuentaService().cargarCuenta(formulario.getIdCuenta());
		//Guardar movimientos en tabla apuntes 	
		formulario.setApuntesInsertados(ServiceUtils.getIApunteService().cargarMovimientosExcel(path, formulario.getIdCuenta(),cuenta.getBanco().getidBanco()));
		
		formulario.setResultado("Carga completada");
		formulario.setAccion("subida");
		
		request.setAttribute("updloadApunteForm", formulario);
		return mapping.findForward("success");
	}
		
}
