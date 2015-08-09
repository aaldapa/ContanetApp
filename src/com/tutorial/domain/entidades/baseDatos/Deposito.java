package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.Date;

public class Deposito implements Serializable {
	
	private Integer idDeposito;
	private Contabilidad contabilidad;
	private TmaeBanco banco;
	private Usuario idCreador;
	private Date fechaApertura, fechaVencimiento;
	private String nombreDeposito, numeroDeposito,titular,cotitular, descripcion, baja;
	private Float rentabilidad, rentabilidadAnticipada, importe;
	
	public Deposito() {
		super();
	}

	public Deposito(Integer idDeposito, String numeroDeposito, Contabilidad contabilidad,
			Usuario idCreador, Date fechaApertura, Date fechaVencimiento,
			String nombreDeposito, String titular, String cotitular,
			String descripcion, String baja, Float rentabilidad,
			Float rentabilidadAnticipada, Float importe) {
		super();
		this.idDeposito = idDeposito;
		this.numeroDeposito = numeroDeposito;
		this.contabilidad = contabilidad;
		this.idCreador = idCreador;
		this.fechaApertura = fechaApertura;
		this.fechaVencimiento = fechaVencimiento;
		this.nombreDeposito = nombreDeposito;
		this.titular = titular;
		this.cotitular = cotitular;
		this.descripcion = descripcion;
		this.baja = baja;
		this.rentabilidad = rentabilidad;
		this.rentabilidadAnticipada = rentabilidadAnticipada;
		this.importe=importe;
	}

	public Integer getIdDeposito() {return idDeposito;}
	public void setIdDeposito(Integer idDeposito) {this.idDeposito = idDeposito;}
	public String getNumeroDeposito() {return numeroDeposito;}
	public void setNumeroDeposito(String numeroDeposito) {this.numeroDeposito = numeroDeposito;}
	public Contabilidad getContabilidad() {return contabilidad;}
	public void setContabilidad(Contabilidad contabilidad) {this.contabilidad = contabilidad;}
	public Usuario getIdCreador() {return idCreador;}
	public void setIdCreador(Usuario idCreador) {this.idCreador = idCreador;}
	public Date getFechaApertura() {return fechaApertura;}
	public void setFechaApertura(Date fechaApertura) {this.fechaApertura = fechaApertura;}
	public Date getFechaVencimiento() {return fechaVencimiento;}
	public void setFechaVencimiento(Date fechaVencimiento) {this.fechaVencimiento = fechaVencimiento;}	
	public String getNombreDeposito() {return nombreDeposito;}
	public void setNombreDeposito(String nombreDeposito) {this.nombreDeposito = nombreDeposito;}
	public String getTitular() {return titular;}
	public void setTitular(String titular) {this.titular = titular;}
	public String getCotitular() {return cotitular;}
	public void setCotitular(String cotitular) {this.cotitular = cotitular;}
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public Float getRentabilidad() {return rentabilidad;}
	public void setRentabilidad(Float rentabilidad) {this.rentabilidad = rentabilidad;}
	public Float getRentabilidadAnticipada() {return rentabilidadAnticipada;}
	public void setRentabilidadAnticipada(Float rentabilidadAnticipada) {this.rentabilidadAnticipada = rentabilidadAnticipada;}
	public TmaeBanco getBanco() {return banco;}
	public void setBanco(TmaeBanco banco) {this.banco = banco;}
	public Float getImporte() {return importe;}
	public void setImporte(Float importe) {this.importe = importe;}

		
}
