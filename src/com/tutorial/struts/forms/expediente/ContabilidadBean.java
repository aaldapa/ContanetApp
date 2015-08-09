package com.tutorial.struts.forms.expediente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Deposito;

public class ContabilidadBean {

	private Contabilidad contabilidad;
	private List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
	private List<Deposito>lstDepositos=new ArrayList<Deposito>();
	private BigDecimal balanceContabilidad=new BigDecimal("0");
	
	public List<Cuenta> getLstCuentas() {return lstCuentas;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public void setContabilidad(Contabilidad contabilidad) {this.contabilidad = contabilidad;}
	public Contabilidad getContabilidad() {return contabilidad;}
	public void setBalanceContabilidad(BigDecimal balanceContabilidad) {this.balanceContabilidad = balanceContabilidad;}
	public BigDecimal getBalanceContabilidad() {	return balanceContabilidad;}
	public List<Deposito> getLstDepositos() {return lstDepositos;}
	public void setLstDepositos(List<Deposito> lstDepositos) {this.lstDepositos = lstDepositos;}
	
}
