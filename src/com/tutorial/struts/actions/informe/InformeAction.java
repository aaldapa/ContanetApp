package com.tutorial.struts.actions.informe;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.forms.informe.InformeForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class InformeAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{

		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		InformeForm informeForm=(InformeForm) form;
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (informeForm==null)
			informeForm=new InformeForm();
		
		//Tratamiento para seleccionar las opciones de la busqueda por defecto cuando queremos generar informes
		ApuntePorDefectoForm apuntePorDefectoForm=ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario());
		if (apuntePorDefectoForm!=null){
			informeForm.setIdContabilidad(apuntePorDefectoForm.getIdContabilidad()!=null ? apuntePorDefectoForm.getIdContabilidad() : new Integer("0"));
			informeForm.setIdCuenta(apuntePorDefectoForm.getIdCuenta()!=null ? apuntePorDefectoForm.getIdCuenta() : new Integer("0"));
			informeForm.setIdGrupo(apuntePorDefectoForm.getIdGrupo() !=null ? apuntePorDefectoForm.getIdGrupo(): new Integer("0"));
			informeForm.setIdClase(apuntePorDefectoForm.getIdClase() !=null ?apuntePorDefectoForm.getIdClase() : new Integer("0"));
		}
		
		//Carga de lstContabilidades
		informeForm.setLstContabilidades(		
			ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario())
		);
		List<Cuenta>lstCuentas=new ArrayList<Cuenta>();		
		informeForm.setLstCuentas(lstCuentas);
		
		request.setAttribute("informeForm", informeForm);
		return mapping.findForward("success");
	}
}
