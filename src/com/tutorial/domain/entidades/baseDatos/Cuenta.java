package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Cuenta implements Serializable{

	private Integer idCuenta;
	private Contabilidad contabilidad;
	private TmaeBanco banco;
	//Usuario que ha creado la cuenta y que tendra permiso para modificarla
	private Usuario creador;
	private Date fechaApertura;
	private String entidad,sucursal,dc,numCuenta;
	private String dniTitular, titular,dniTitular2, titular2;
	private Float saldoInicial=0.f; 
	private String notas, baja;
	private String labelCuenta;
	private Set apunteses=new HashSet();
	private Set apuntePorDefectoes=new HashSet();
	private Set depositoses=new HashSet();
	
	private Float balance=0f;
	
	public Cuenta() {
		super();
	}

	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public String getEntidad() {return entidad;}
	public void setEntidad(String entidad) {this.entidad = entidad;}
	public String getSucursal() {return sucursal;}
	public void setSucursal(String sucursal) {this.sucursal = sucursal;}
	public String getDc() {return dc;}
	public void setDc(String dc) {this.dc = dc;}
	public String getNumCuenta() {return numCuenta;}
	public void setNumCuenta(String numCuenta) {this.numCuenta = numCuenta;}
	public String getDniTitular() {return dniTitular;}
	public void setDniTitular(String dniTitular) {this.dniTitular = dniTitular;}
	public String getTitular() {return titular;}
	public void setTitular(String titular) {this.titular = titular;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public void setApunteses(Set apunteses) {this.apunteses = apunteses;}
	public Set getApunteses() {return apunteses;}
	public String getDniTitular2() {return dniTitular2;}
	public void setDniTitular2(String dniTitular2) {this.dniTitular2 = dniTitular2;}
	public String getTitular2() {return titular2;}
	public void setTitular2(String titular2) {this.titular2 = titular2;}
	public Float getSaldoInicial() {return saldoInicial;}
	public void setSaldoInicial(Float saldoInicial) {this.saldoInicial = saldoInicial;}
	public void setContabilidad(Contabilidad contabilidad) {this.contabilidad = contabilidad;}
	public Contabilidad getContabilidad() {return contabilidad;}
	public void setLabelCuenta(String labelCuenta) {this.labelCuenta = labelCuenta;	}
	public void setCreador(Usuario creador) {this.creador = creador;}
	public Usuario getCreador() {return creador;}
	public String getLabelCuenta() {
		StringBuffer labelCompleta=new StringBuffer();
		labelCompleta.append(getEntidad()+" "+ getSucursal()+" "+ getDc()+" "+ getNumCuenta()
					+" ("+getTitular());
		if (getTitular2()!=null)
			labelCompleta.append(" - "+getTitular2());
		labelCompleta.append(")");
		return labelCompleta.toString();
	}

	public void setBalance(Float balance) {this.balance = balance;}
	public Float getBalance() {return balance;}
	public void setFechaApertura(Date fechaApertura) {this.fechaApertura = fechaApertura;}
	public Date getFechaApertura() {return fechaApertura;}
	public Set getApuntePorDefectoes() {return apuntePorDefectoes;}
	public void setApuntePorDefectoes(Set apuntePorDefectoes) {this.apuntePorDefectoes = apuntePorDefectoes;}
	public void setBanco(TmaeBanco banco) {this.banco = banco;}
	public TmaeBanco getBanco() {return banco;}
	public Set getDepositoses() {return depositoses;}
	public void setDepositoses(Set depositoses) {this.depositoses = depositoses;}
}
