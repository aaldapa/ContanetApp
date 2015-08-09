package com.tutorial.struts.forms.familia;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.forms.BaseForm;

public class FamiliaForm extends BaseForm implements Serializable {

	String nombre,notas,baja;

	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (GenericValidator.isBlankOrNull(getNombre()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("global.required",recursos.getMessage("familias.formulario.nombre")));
		return errors;
		
	}

	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	
}
