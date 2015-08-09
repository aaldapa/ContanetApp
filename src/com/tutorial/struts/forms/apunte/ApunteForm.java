package com.tutorial.struts.forms.apunte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
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

public class ApunteForm extends BaseForm implements Serializable {

	private Integer idUsuario;
	private Integer idContabilidad;
	private Integer idCuenta;
	private Integer idGrupo;
	private Integer idClase;
	private String tipoApunte;
	private String traspaso;
	
	private List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
	private List<Contabilidad>lstContabilidades=new ArrayList<Contabilidad>();
	private List<Grupo>lstGrupos=new ArrayList<Grupo>();
	private List<Clase>lstClases=new ArrayList<Clase>();
	
	//Inicio octubre_12
	private ApunteListForm seleccionAnterior=new ApunteListForm();
	//Fin octubre_12
	
	private String fechaStr;
	private Date fecha;
	private String concepto;
	private String conceptoAbreviado;
	private String notas;
	private String nombreGrupo;
	private String nombreClase;
	private String importeStr, saldoStr;

	private BigDecimal importe,saldo;

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
	ActionErrors errors=new ActionErrors();
	MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
	
		if (idContabilidad==null || idContabilidad<1)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.formulario.contabilidades")));
		if (idCuenta==null || idCuenta<1)
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.formulario.cuentas")));
		if (idGrupo==null || idGrupo<1)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.formulario.grupos")));
		if (idClase==null || idClase<1)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.formulario.clases")));
		if (GenericValidator.isBlankOrNull(getImporteStr()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("global.required",recursos.getMessage("apunte.formulario.importe")));
		if (GenericValidator.isBlankOrNull(getConcepto()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("global.required",recursos.getMessage("apunte.formulario.concepto")));
		return errors;
	}
	
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {	this.idContabilidad = idContabilidad;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {	this.idCuenta = idCuenta;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public Integer getIdClase() {return idClase;}
	public void setIdClase(Integer idClase) {this.idClase = idClase;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	public void setLstClases(List<Clase> lstClases) {this.lstClases = lstClases;}
	public String getFechaStr() {return fechaStr;}
	public void setFechaStr(String fechaStr) {this.fechaStr = fechaStr;	}
	public String getConcepto() {return concepto;}
	public void setConcepto(String concepto) {this.concepto = concepto;}
	public String getImporteStr() {	return importeStr;}
	public void setImporteStr(String importeStr) {this.importeStr = importeStr;}
	public String getSaldoStr() {return saldoStr;}
	public void setSaldoStr(String saldoStr) {this.saldoStr = saldoStr;}	
	public BigDecimal getImporte() {return importe;}
	public void setImporte(BigDecimal importe) {this.importe = importe;}
	public BigDecimal getSaldo() {return saldo;}
	public void setSaldo(BigDecimal saldo) {this.saldo = saldo;}
	
	
	public List<Cuenta> getLstCuentas() {
		if (idContabilidad>0)
			//Carga de lstCuentas
			lstCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
		return lstCuentas;
	}
	public List<Contabilidad> getLstContabilidades() {
		if(idUsuario!=null)
			lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		return lstContabilidades;
	}
	public void setTipoApunte(String tipoApunte) {this.tipoApunte = tipoApunte;}
	public String getTipoApunte() {return tipoApunte;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getNotas() {return notas;}

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

	public void setFecha(Date fecha) {this.fecha = fecha;}
	public Date getFecha() {
		return FechaUtil.formatearADate(this.getFechaStr());
	}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdUsuario() {return idUsuario;}
	public String getNombreGrupo() {return nombreGrupo;}
	public void setNombreGrupo(String nombreGrupo) {this.nombreGrupo = nombreGrupo;}
	public String getNombreClase() {return nombreClase;}
	public void setNombreClase(String nombreClase) {this.nombreClase = nombreClase;}
	public void setConceptoAbreviado(String conceptoAbreviado) {this.conceptoAbreviado = conceptoAbreviado;}
	public String getConceptoAbreviado() {	
		conceptoAbreviado="";
		if (concepto!=null){
			if (concepto.length()>24)conceptoAbreviado=this.concepto.substring(0, 24);
			else conceptoAbreviado=this.getConcepto();
		}
		
		return conceptoAbreviado;	}
	public ApunteForm() {super();}
	
	//Inicio octubre_12
	public void setSeleccionAnterior(ApunteListForm seleccionAnterior) {this.seleccionAnterior = seleccionAnterior;}
	public ApunteListForm getSeleccionAnterior() {return seleccionAnterior;}
	//Fin octubre_12

	public String getTraspaso() {return traspaso;}
	public void setTraspaso(String traspaso) {this.traspaso = traspaso;}
}
