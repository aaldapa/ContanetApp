package com.tutorial.struts.utils;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Usuario;

public class LoginBean {

	//Usuario logado
	Usuario usuario;
	//Contabilidad con la que esta trabajando
	Contabilidad contabilidad;
	
	public Contabilidad getContabilidad() {
		return contabilidad;
	}
	public void setContabilidad(Contabilidad contabilidad) {
		this.contabilidad = contabilidad;
	}
	public Usuario getUsuario() {return usuario;}
	public void setUsuario(Usuario usuario) {this.usuario = usuario;} 
}
