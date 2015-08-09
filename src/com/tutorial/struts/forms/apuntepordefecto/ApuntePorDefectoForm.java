package com.tutorial.struts.forms.apuntepordefecto;

import java.util.ArrayList;
import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;

public class ApuntePorDefectoForm extends BaseForm {

	private Integer idUsuario;
	private Integer idContabilidad;
	private Integer idCuenta;
	private Integer idGrupo;
	private Integer idClase;
	private String tipoApunte, baja;
	
	private List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
	private List<Contabilidad>lstContabilidades=new ArrayList<Contabilidad>();
	private List<Grupo>lstGrupos=new ArrayList<Grupo>();
	private List<Clase>lstClases=new ArrayList<Clase>();
	
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
	
	public Integer getIdUsuario() {return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public Integer getIdClase() {return idClase;}
	public void setIdClase(Integer idClase) {this.idClase = idClase;}
	public String getTipoApunte() {return tipoApunte;}
	public void setTipoApunte(String tipoApunte) {this.tipoApunte = tipoApunte;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	public void setLstClases(List<Clase> lstClases) {this.lstClases = lstClases;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
		
}
