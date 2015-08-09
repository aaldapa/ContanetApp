package com.tutorial.struts.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class BaseForm extends ActionForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String accion;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getAccion() {
		return accion;
	}
	
	
}
