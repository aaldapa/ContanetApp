package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Clase implements Serializable {

	private Integer idClase;
	private Grupo grupo;
	private String nombre,notas, baja;
	
	private Set apunteses=new HashSet();
	private Set apuntePorDefectoes=new HashSet();
	
	public Clase() {
		super();
	}
	
	public Integer getIdClase() {return idClase;}
	public void setIdClase(Integer idClase) {this.idClase = idClase;}
	public Grupo getGrupo() {return grupo;}
	public void setGrupo(Grupo grupo) {this.grupo = grupo;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public void setApunteses(Set apunteses) {this.apunteses = apunteses;}
	public Set getApunteses() {return apunteses;}
	public void setApuntePorDefectoes(Set apuntePorDefectoes) {this.apuntePorDefectoes = apuntePorDefectoes;}
	public Set getApuntePorDefectoes() {return apuntePorDefectoes;}
	
}
