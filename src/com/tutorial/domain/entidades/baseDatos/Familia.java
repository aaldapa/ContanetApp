package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Familia implements Serializable {

	private Integer idFamilia;
	private String nombre, notas;
	private String baja;
	
	private Set usuarioses= new HashSet();
	
	public Familia() {
		super();
	}
	
	public Integer getIdFamilia() {return idFamilia;}
	public void setIdFamilia(Integer idFamilia) {this.idFamilia = idFamilia;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public Set getUsuarioses() {return usuarioses;}
	public void setUsuarioses(Set usuarioses) {this.usuarioses = usuarioses;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	
}
