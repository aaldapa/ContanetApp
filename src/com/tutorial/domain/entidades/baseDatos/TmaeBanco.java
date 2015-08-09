package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;



public class TmaeBanco implements Serializable {

	private Integer idBanco;
	private String nombreBanco;
	private String nombreLogotipo;
	private Blob logotipo;
	
	private Set<Cuenta> cuentases =new HashSet();
	private Set depositoses= new HashSet();
	
	public TmaeBanco() {
		super();
	}

	public TmaeBanco(Integer idBanco, String nombreBanco,
			String nombreLogotipo, Blob logotipo, Set<Cuenta> cuentases) {
		super();
		this.idBanco = idBanco;
		this.nombreBanco = nombreBanco;
		this.nombreLogotipo = nombreLogotipo;
		this.logotipo = logotipo;
		this.cuentases = cuentases;
	}

	public Integer getidBanco() {return idBanco;}
	public void setidBanco(Integer codBanco) {this.idBanco = codBanco;}
	public String getNombreBanco() {return nombreBanco;}
	public void setNombreBanco(String nombreBanco) {this.nombreBanco = nombreBanco;}
	public Blob getLogotipo() {return logotipo;}
	public void setLogotipo(Blob logotipo) {this.logotipo = logotipo;}
	public void setNombreLogotipo(String nombreLogotipo) {this.nombreLogotipo = nombreLogotipo;}
	public String getNombreLogotipo() {return nombreLogotipo;}
	public void setCuentases(Set<Cuenta> cuentases) {this.cuentases = cuentases;}
	public Set<Cuenta> getCuentases() {return cuentases;}
	public Set getDepositoses() {return depositoses;}
	public void setDepositoses(Set depositoses) {this.depositoses = depositoses;}
	
}
