package com.tutorial.struts.forms.informe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;

public class InformeForm extends BaseForm implements Serializable{

	private Integer idUsuario;
	
	private String fechaInicioStr="01/01/"+FechaUtil.getFechaActual().substring(6, 10);
	private String fechaFinStr=FechaUtil.getFechaActual().toString();
	private Integer idContabilidad;
	private Integer idCuenta;
	private Integer idGrupo;
	private Integer idClase;
	
	private boolean detallado=false;
	
	private List<Cuenta> lstCuentas;
	private List<Contabilidad>lstContabilidades;
	private List<Grupo>lstGrupos;
	private List<Clase>lstClases;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si se pulsa el boton ha de seleccionarse cuenta
		if (recursos.getMessage("boton.informe").equals(getAccion())){
			if (GenericValidator.isBlankOrNull(fechaInicioStr))
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.listado.finicio")));
			if (GenericValidator.isBlankOrNull(fechaFinStr))
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.listado.fhasta")));
			if (idCuenta==null || idCuenta<1)
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.listado.cuentas")));
		}
		return errors;
	}
	
	
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdUsuario() {return idUsuario;}
	public String getFechaInicioStr() {return fechaInicioStr;}
	public void setFechaInicioStr(String fechaInicioStr) {this.fechaInicioStr = fechaInicioStr;}
	public String getFechaFinStr() {return fechaFinStr;}
	public void setFechaFinStr(String fechaFinStr) {this.fechaFinStr = fechaFinStr;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public Integer getIdClase() {return idClase;}
	public void setIdClase(Integer idClase) {this.idClase = idClase;}
	public boolean isDetallado() {return detallado;}
	public void setDetallado(boolean detallado) {this.detallado = detallado;}

	public List<Cuenta> getLstCuentas() {
		if (idContabilidad!=null && idContabilidad>0)
			//Carga de lstCuentas
			lstCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
		else
			lstCuentas= new ArrayList<Cuenta>();
	return lstCuentas;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public List<Contabilidad> getLstContabilidades() {
		if(idUsuario!=null)
			lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		return lstContabilidades;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	
	public void setLstClases(List<Clase> lstClases) {this.lstClases = lstClases;}
	public List<Grupo> getLstGrupos() {
		lstGrupos=ServiceUtils.getIGrupoService().getLstGrupos();
		return lstGrupos;
	}
	public List<Clase> getLstClases() {	
		
		if (idGrupo!=null && idGrupo>0)
			lstClases=ServiceUtils.getIClaseService().getLstClases(idGrupo);
		else
			lstClases=new ArrayList<Clase>();
		return lstClases;
	}
	
}
