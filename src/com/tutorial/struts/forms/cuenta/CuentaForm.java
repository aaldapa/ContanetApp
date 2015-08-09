package com.tutorial.struts.forms.cuenta;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.TmaeBanco;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;

public class CuentaForm extends BaseForm implements Serializable{
	
	private Integer idUsuario;
	
	private String dniTitular,titular,dniTitular2,titular2,saldoInicialStr;
	private String entidad,sucursal,dc,numCuenta;
	private Float saldoInicial=0f;
	private String fechaAperturaStr=FechaUtil.getFechaActual().toString();
	private String descripcion;
	
	private Integer idContabilidad, idBanco;
	private List<Contabilidad> lstContabilidades;
	private List<TmaeBanco> lstBancos;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
	
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		ActionErrors errors=new ActionErrors();
		if (GenericValidator.isBlankOrNull(getTitular()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.titular")));
		if (GenericValidator.isBlankOrNull(getEntidad()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.entidad")));
		if (GenericValidator.isBlankOrNull(getSucursal()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.sucursal")));
		if (GenericValidator.isBlankOrNull(getDc()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.dc")));
		if (GenericValidator.isBlankOrNull(getNumCuenta()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.numero")));
		if (idContabilidad.compareTo(new Integer(0))==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.contabilidad")));
		if (idBanco.compareTo(new Integer(0))==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.cuentas.formulario.campo.requerido",recursos.getMessage("cuenta.formulario.banco")));
		return errors;
			
	}

	public String getDniTitular() {return dniTitular;}
	public void setDniTitular(String dniTitular) {this.dniTitular = dniTitular;}
	public String getTitular() {return titular;}
	public void setTitular(String titular) {this.titular = titular;}
	public String getDniTitular2() {return dniTitular2;}
	public void setDniTitular2(String dniTitular2) {this.dniTitular2 = dniTitular2;}
	public String getTitular2() {return titular2;}
	public void setTitular2(String titular2) {this.titular2 = titular2;}
	public Float getSaldoInicial() {return saldoInicial;}
	public void setSaldoInicial(Float saldoInicial) {this.saldoInicial = saldoInicial;}
	public void setSaldoInicialStr(String saldoInicialStr) {this.saldoInicialStr = saldoInicialStr;}
	public String getSaldoInicialStr() {return saldoInicialStr;}
	public String getEntidad() {return entidad;}
	public void setFechaAperturaStr(String fechaAperturaStr) {
		this.fechaAperturaStr = fechaAperturaStr;
	}

	public String getFechaAperturaStr() {
		return fechaAperturaStr;
	}

	public void setEntidad(String entidad) {this.entidad = entidad;}
	public String getSucursal() {return sucursal;}
	public void setSucursal(String sucursal) {this.sucursal = sucursal;}
	public String getDc() {return dc;}
	public void setDc(String dc) {this.dc = dc;}
	public String getNumCuenta() {return numCuenta;}
	public void setNumCuenta(String numCuenta) {this.numCuenta = numCuenta;	}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public Integer getIdUsuario() {return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public List<Contabilidad> getLstContabilidades() {
		if(idUsuario!=null)
			lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		return lstContabilidades;
	}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}

	public Integer getIdBanco() {return idBanco;}
	public void setIdBanco(Integer idBanco) {this.idBanco = idBanco;}
	public List<TmaeBanco> getLstBancos() {
		lstBancos=ServiceUtils.getICuentaService().getLstBancos();
		return lstBancos;
	}
	public void setLstBancos(List<TmaeBanco> lstBancos) {this.lstBancos = lstBancos;}
		
}
