package com.tutorial.struts.actions.analisis;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.OptionBean;
import com.tutorial.struts.forms.analisis.AnalisisForm;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.LoginBean;

public class GetAnalisisAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
				 throws Exception 
	{
		//--- Compruebo que el usuario esta logado. 
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
		return mapping.findForward("failure");
		
		AnalisisForm analisisForm=(AnalisisForm) form;
		
		if (analisisForm==null)
			analisisForm=new AnalisisForm();
		
		//Tratamiento para seleccionar las opciones de la busqueda por defecto cuando queremos generar informes
		ApuntePorDefectoForm apuntePorDefectoForm=ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario());
		if (apuntePorDefectoForm!=null){
			analisisForm.setIdContabilidad(apuntePorDefectoForm.getIdContabilidad()!=null ? apuntePorDefectoForm.getIdContabilidad() : new Integer("0"));
			analisisForm.setIdCuenta(apuntePorDefectoForm.getIdCuenta()!=null ? apuntePorDefectoForm.getIdCuenta() : new Integer("0"));
		}
		
		//Carga de lstContabilidades
		analisisForm.setLstContabilidades(		
					ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario()));
		
		//cargo las cuentas
		List<Cuenta>lstCuentas=new ArrayList<Cuenta>();
		if (analisisForm.getIdContabilidad()>0)
			lstCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(analisisForm.getIdContabilidad());
		analisisForm.setLstCuentas(lstCuentas);
		
		//cargo las cuentas
		List<Grupo>lstGrupos=new ArrayList<Grupo>();
		lstGrupos=ServiceUtils.getIGrupoService().getLstGrupos();
		analisisForm.setLstGrupos(lstGrupos);
		analisisForm.setIdGrupo(analisisForm.getIdGrupo()!=null?analisisForm.getIdGrupo():new Integer(0));
		
		//Cargo los anios
		String anioStr=FechaUtil.getFechaActual().toString();
		analisisForm.setAnio(new Integer(anioStr.substring(6, 10)));
		Integer anioDesde=analisisForm.getAnio()-10;
		
		for (int cont=anioDesde;cont<=analisisForm.getAnio();cont++)
			analisisForm.getLstAnios().add(new OptionBean(cont,new Integer(cont).toString()));
		
		
		request.setAttribute("analisisForm", analisisForm);
		
		// En funcion del tipo de grafica mostraremos una jsp o otra
		if (StringUtils.equals("meses", analisisForm.getTipoGrafica()))
			return mapping.findForward("graficames");
		else if(StringUtils.equals("grupos", analisisForm.getTipoGrafica()))
			return mapping.findForward("graficagrupo");
		else
			return mapping.findForward("graficaclase");
	}
	
}
