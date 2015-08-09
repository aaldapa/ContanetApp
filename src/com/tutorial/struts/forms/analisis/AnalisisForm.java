package com.tutorial.struts.forms.analisis;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.forms.OptionBean;

public class AnalisisForm extends BaseForm {

	private Integer anio;
	private List<OptionBean>lstAnios=new ArrayList<OptionBean>();
	
	private Integer idUsuario;
	private Integer idContabilidad;
	private Integer idCuenta;
	private Integer idGrupo;
	
	private String tipoGrafica;
	
	private List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
	private List<Contabilidad>lstContabilidades=new ArrayList<Contabilidad>();
	private List<Grupo>lstGrupos=new ArrayList<Grupo>();
	
	
	public AnalisisForm() {
		super();
	}
	
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		//Si no se selecciona ninguna opcion
		if (tipoGrafica==null)
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.grafica.seleccion"));
		
		return errors;
	}
	
	public List<Grupo> getLstGrupos() {return lstGrupos;}
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	public List<Cuenta> getLstCuentas() {return lstCuentas;}
	public List<Contabilidad> getLstContabilidades() {return lstContabilidades;}
	public Integer getIdUsuario() {return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {	this.idContabilidad = idContabilidad;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public Integer getAnio() {return anio;}
	public void setAnio(Integer anio) {this.anio = anio;}
	public List<OptionBean> getLstAnios() {return lstAnios;}
	public void setLstAnios(List<OptionBean> lstAnios) {this.lstAnios = lstAnios;}
	public void setTipoGrafica(String tipoGrafica) {this.tipoGrafica = tipoGrafica;}
	public String getTipoGrafica() {return tipoGrafica;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
}