package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Contabilidad implements Serializable{
	
	private Integer idContabilidad;
	
	//Usuario que ha creado la contabilidad y que tendra permiso para modificarla
	private Usuario creador;
	
	private String nombreContabilidad, notas, baja, predeterminada;
	private Set contabilidadesUsuarioses=new HashSet();
	private Set cuentases=new HashSet();
	private Set apuntePorDefectoes=new HashSet();
	private Set depositoses= new HashSet();
	
	public Contabilidad() {
		super();
	}
	
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public void setCreador(Usuario creador) {this.creador = creador;}
	public Usuario getCreador() {return creador;}
	public String getNombreContabilidad() {return nombreContabilidad;}
	public void setNombreContabilidad(String nombreContabilidad) {this.nombreContabilidad = nombreContabilidad;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public void setContabilidadesUsuarioses(Set contabilidadesUsuarioses) {this.contabilidadesUsuarioses = contabilidadesUsuarioses;}
	public Set getContabilidadesUsuarioses() {return contabilidadesUsuarioses;}
	public void setPredeterminada(String predeterminada) {this.predeterminada = predeterminada;}
	public String getPredeterminada() {return predeterminada;}
	public void setCuentases(Set cuentases) {this.cuentases = cuentases;}
	public Set getCuentases() {return cuentases;}
	public Set getApuntePorDefectoes() {return apuntePorDefectoes;}
	public void setApuntePorDefectoes(Set apuntePorDefectoes) {this.apuntePorDefectoes = apuntePorDefectoes;}
	public Set getDepositoses() {return depositoses;}
	public void setDepositoses(Set depositoses) {this.depositoses = depositoses;}
	
}
