package com.tutorial.struts.forms.rol;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.forms.BaseForm;

public class RolForm extends BaseForm {

	private String nombre, descripcion;
	private String usuario,roles,contabilidad;
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		
		if (getNombre().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.roles.formulario.campo.requerido","Nombre"));
		if (getDescripcion().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.roles.formulario.campo.requerido","Descripción"));
		
		return errors;
		
	}
	
	public String getUsuario() {return usuario;}
	public void setUsuario(String usuario) {this.usuario = usuario;}
	public String getRoles() {return roles;}
	public void setRoles(String roles) {this.roles = roles;}
	public String getContabilidad() {return contabilidad;}
	public void setContabilidad(String contabilidad) {this.contabilidad = contabilidad;}
}
