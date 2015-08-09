package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Perfil implements Serializable {

	private Integer idPerfil;
	private String nombre,descripcion;
	private Set usuarioses= new HashSet();
	private String baja;
	
	public Integer getIdPerfil() {return idPerfil;}
	public void setIdPerfil(Integer idPerfil) {this.idPerfil = idPerfil;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public void setUsuarioses(Set usuarioses) {this.usuarioses = usuarioses;}
	public Set getUsuarioses() {return usuarioses;}
	public void setBaja(String baja) {this.baja = baja;}
	public String getBaja() {return baja;}
		
}
