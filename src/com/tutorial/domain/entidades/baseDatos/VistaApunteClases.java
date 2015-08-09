package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.math.BigDecimal;

public class VistaApunteClases implements Serializable {

	private Integer idVista;
	private BigDecimal ingresos, gastos, diferencias;
	private Integer anio;
	private String grupo,clase;
	private Integer idCuenta;
	private Integer idGrupo;
	
	public VistaApunteClases() {
		super();
	}

	public VistaApunteClases(Integer idVista, BigDecimal ingresos,
			BigDecimal gastos, BigDecimal diferencias, Integer anio,
			String grupo, String clase, Integer idCuenta, Integer idGrupo) {
		super();
		this.idVista = idVista;
		this.ingresos = ingresos;
		this.gastos = gastos;
		this.diferencias = diferencias;
		this.anio = anio;
		this.grupo = grupo;
		this.clase = clase;
		this.idCuenta = idCuenta;
		this.idGrupo = idGrupo;
	}

	public Integer getIdVista() {return idVista;}
	public void setIdVista(Integer idVista) {this.idVista = idVista;}
	public BigDecimal getIngresos() {return ingresos;}
	public void setIngresos(BigDecimal ingresos) {this.ingresos = ingresos;}
	public BigDecimal getGastos() {return gastos;}
	public void setGastos(BigDecimal gastos) {this.gastos = gastos;}
	public BigDecimal getDiferencias() {return diferencias;}
	public void setDiferencias(BigDecimal diferencias) {this.diferencias = diferencias;}
	public Integer getAnio() {return anio;}
	public void setAnio(Integer anio) {this.anio = anio;}
	public String getGrupo() {return grupo;}
	public void setGrupo(String grupo) {this.grupo = grupo;}
	public String getClase() {return clase;}
	public void setClase(String clase) {this.clase = clase;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
}
