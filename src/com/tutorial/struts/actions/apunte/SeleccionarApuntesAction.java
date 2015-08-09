package com.tutorial.struts.actions.apunte;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.UploadApunteForm;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

/**
 * @author Alber
 * Este Action lo utilizo para mostrar la jsp con el formulario para cargar el fichero de 
 * movimientos de las bbk 
 */

public class SeleccionarApuntesAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		UploadApunteForm formulario=(UploadApunteForm) form;
		//FormFile fichero=null;
		
		if (formulario==null){
		formulario=new UploadApunteForm();
		}
		//Tratamiento para seleccionar las opciones del apunte por defecto cuando queremos hacer uno nuevo
		ApuntePorDefectoForm apuntePorDefectoForm=ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario());
		if (apuntePorDefectoForm!=null && formulario.getIdCuenta()==null){
			formulario.setIdContabilidad(apuntePorDefectoForm.getIdContabilidad()!=null ? apuntePorDefectoForm.getIdContabilidad() : new Integer("0"));
			formulario.setIdCuenta(apuntePorDefectoForm.getIdCuenta()!=null ? apuntePorDefectoForm.getIdCuenta() : new Integer("0"));
		}
		
		
		//Carga de lstContabilidades
		formulario.setLstContabilidades(		
			ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario())
			);
		List<Cuenta>lstCuentas=new ArrayList<Cuenta>();
		if (formulario.getIdContabilidad()!=null && formulario.getIdContabilidad()>0)
			//Carga de lstCuentas
			lstCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(formulario.getIdContabilidad());
		
		
		
		formulario.setLstCuentas(lstCuentas);
		
		request.getSession().setAttribute("updloadApunteForm", formulario);
		return mapping.findForward("success");
		
	}
	
}
