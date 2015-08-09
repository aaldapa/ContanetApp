package com.tutorial.struts.forms.grupo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.forms.BaseForm;

public class GrupoForm extends BaseForm implements Serializable {

	private String nombre, notas, descripcion;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		
		if (nombre.trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.grupos.formulario.campo.requerido","Nombre"));
						
		return errors;
	}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
