package com.tutorial.struts.forms.clase;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.service.grupo.GrupoServiceImpl;

public class ClaseForm extends BaseForm implements Serializable {

	private String nombre,descripcion;
	private Integer idGrupo;
	
	private List<Grupo> lstGrupos;

	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		
		if (getNombre().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.clases.formulario.campo.requerido","Nombre"));
		
		if (getIdGrupo()==null || getIdGrupo()==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.clases.formulario.campo.requerido","Grupo"));
		
		return errors;
			
	}


	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public List<Grupo> getLstGrupos() {
		//cargo la lista de grupos para mostrar en la lista desplegable
		return ServiceUtils.getIGrupoService().getLstGrupos();
	}
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	
	
}
