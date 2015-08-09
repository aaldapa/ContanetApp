package com.tutorial.struts.forms.deposito;

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

public class DepositoForm extends BaseForm implements Serializable{
	
	private Integer idUsuario, idContabilidad, idBanco;
	private String numeroDeposito;
	private String nombreDeposito,titular,cotitular,descripcion, fechaAperturaStr, fechaVencimientoStr, importeStr, rentabilidadStr, rentabilidadCancelacionStr;
	private Float importe, rentabilidad,rentabilidadCancelacion;
	private List<Contabilidad> lstContabilidades;
	private List<TmaeBanco> lstBancos;
	
	
	public DepositoForm() {
		super();
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		ActionErrors errors=new ActionErrors();
		if (GenericValidator.isBlankOrNull(getFechaAperturaStr()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.fapertura")));
		if (GenericValidator.isBlankOrNull(getFechaVencimientoStr()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.fvencimiento")));
		if (GenericValidator.isBlankOrNull(getTitular()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.titular")));
		if (GenericValidator.isBlankOrNull(getImporteStr()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.importe")));
		if (GenericValidator.isBlankOrNull(getRentabilidadStr()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.rentabilidad")));
		if (idContabilidad.compareTo(new Integer(0))==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.contabilidad")));
		if (idBanco.compareTo(new Integer(0))==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.banco")));
		if (GenericValidator.isBlankOrNull(getNumeroDeposito()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.depositos.formulario.campo.requerido",recursos.getMessage("deposito.formulario.ndeposito")));
		return errors;
			
	}

	public String getNumeroDeposito() {return numeroDeposito;}
	public void setNumeroDeposito(String numeroDeposito) {this.numeroDeposito = numeroDeposito;}
	public Integer getIdUsuario() {return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public Integer getIdBanco() {return idBanco;}
	public void setIdBanco(Integer idBanco) {this.idBanco = idBanco;}
	public String getNombreDeposito() {return nombreDeposito;}
	public void setNombreDeposito(String nombreDeposito) {this.nombreDeposito = nombreDeposito;}
	public String getTitular() {return titular;}
	public void setTitular(String titular) {this.titular = titular;}
	public String getCotitular() {return cotitular;}
	public void setCotitular(String cotitular) {this.cotitular = cotitular;}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public String getFechaAperturaStr() {return fechaAperturaStr;}
	public void setFechaAperturaStr(String fechaAperturaStr) {this.fechaAperturaStr = fechaAperturaStr;}
	public Float getImporte() {return importe;}
	public void setImporte(Float importe) {this.importe = importe;}
	public Float getRentabilidad() {return rentabilidad;}
	public void setRentabilidad(Float rentabilidad) {this.rentabilidad = rentabilidad;}
	public Float getRentabilidadCancelacion() {return rentabilidadCancelacion;}
	public void setRentabilidadCancelacion(Float rentabilidadCancelacion) {this.rentabilidadCancelacion = rentabilidadCancelacion;}
	public String getFechaVencimientoStr() {return fechaVencimientoStr;}
	public void setFechaVencimientoStr(String fechaVencimientoStr) {this.fechaVencimientoStr = fechaVencimientoStr;}
	public String getImporteStr() {return importeStr;}
	public void setImporteStr(String importeStr) {this.importeStr = importeStr;}
	public String getRentabilidadStr() {return rentabilidadStr;}
	public void setRentabilidadStr(String rentabilidadStr) {this.rentabilidadStr = rentabilidadStr;}
	public String getRentabilidadCancelacionStr() {return rentabilidadCancelacionStr;}
	public void setRentabilidadCancelacionStr(String rentabilidadCancelacionStr) {this.rentabilidadCancelacionStr = rentabilidadCancelacionStr;}
	public List<Contabilidad> getLstContabilidades() {
		if(idUsuario!=null)
			lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		return lstContabilidades;
	}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public List<TmaeBanco> getLstBancos() {
		lstBancos=ServiceUtils.getICuentaService().getLstBancos();
		return lstBancos;
	}
	public void setLstBancos(List<TmaeBanco> lstBancos) {this.lstBancos = lstBancos;}
	
	
	

}
