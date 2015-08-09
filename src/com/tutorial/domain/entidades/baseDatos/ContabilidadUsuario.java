package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;

public class ContabilidadUsuario implements Serializable {

	private Integer idContabilidadUsuario;
	private Usuario usuario;
	private Contabilidad contabilidad;
	private String baja;
		
	public Integer getIdContabilidadUsuario() {return idContabilidadUsuario;}
	public void setIdContabilidadUsuario(Integer idContabilidadUsuario) {this.idContabilidadUsuario = idContabilidadUsuario;}
	public Usuario getUsuario() {return usuario;}
	public void setUsuario(Usuario usuario) {this.usuario = usuario;}
	public Contabilidad getContabilidad() {return contabilidad;}
	public void setContabilidad(Contabilidad contabilidad) {this.contabilidad = contabilidad;}
	public void setBaja(String baja) {this.baja = baja;}
	public String getBaja() {return baja;}
}
