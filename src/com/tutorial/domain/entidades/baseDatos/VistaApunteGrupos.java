package com.tutorial.domain.entidades.baseDatos;

import java.math.BigDecimal;

import com.tutorial.struts.actions.CustomBaseAction;

public class VistaApunteGrupos extends CustomBaseAction {

	private Integer idVista;
	private BigDecimal ingresos, gastos, diferencias;
	private Integer anio;
	private Integer idCuenta;
	private String nombreGrupo;
	
	public VistaApunteGrupos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VistaApunteGrupos(Integer idVista, BigDecimal ingresos,
			BigDecimal gastos, BigDecimal diferencias, Integer anio,
			Integer idCuenta, String nombreGrupo) {
		super();
		this.idVista = idVista;
		this.ingresos = ingresos;
		this.gastos = gastos;
		this.diferencias = diferencias;
		this.anio = anio;
		this.idCuenta = idCuenta;
		this.nombreGrupo = nombreGrupo;
	}

	public Integer getIdVista() {
		return idVista;
	}

	public void setIdVista(Integer idVista) {this.idVista = idVista;}
	public BigDecimal getIngresos() {return ingresos;}
	public void setIngresos(BigDecimal ingresos) {this.ingresos = ingresos;}
	public BigDecimal getGastos() {return gastos;}
	public void setGastos(BigDecimal gastos) {this.gastos = gastos;}
	public BigDecimal getDiferencias() {return diferencias;}
	public void setDiferencias(BigDecimal diferencias) {this.diferencias = diferencias;}
	public Integer getAnio() {return anio;}
	public void setAnio(Integer anio) {this.anio = anio;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public String getNombreGrupo() {return nombreGrupo;}
	public void setNombreGrupo(String nombreGrupo) {this.nombreGrupo = nombreGrupo;}
}