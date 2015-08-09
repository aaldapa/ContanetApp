package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Grupo implements Serializable {
	
	private Integer idGrupo;
	private String nombre,notas;
	private String baja;
	private Set claseses=new HashSet();
	private Set apuntePorDefectoes=new HashSet();
	
	public Grupo() {
		super();
	}
	
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public String getNombre() {	return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public void setBaja(String baja) {this.baja = baja;}
	public String getBaja() {return baja;}
	public void setClaseses(Set claseses) {this.claseses = claseses;}
	public Set getClaseses() {return claseses;}
	public void setApuntePorDefectoes(Set apuntePorDefectoes) {this.apuntePorDefectoes = apuntePorDefectoes;}
	public Set getApuntePorDefectoes() {return apuntePorDefectoes;}
		
}
