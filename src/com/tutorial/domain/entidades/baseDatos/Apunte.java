package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Apunte implements Serializable {

	private Integer idApunte;
	private Cuenta cuenta;
	private Clase clase;
	private String tipoOperacion;
	private Date fecha;
	private BigDecimal importe;
	private String concepto;
	private String notas, baja,traspaso;
	
	public Apunte() {
		super();
	}
	
	public Integer getIdApunte() {return idApunte;}
	public void setIdApunte(Integer idApunte) {this.idApunte = idApunte;}
	public Cuenta getCuenta() {return cuenta;}
	public void setCuenta(Cuenta cuenta) {this.cuenta = cuenta;}
	public Clase getClase() {return clase;}
	public void setClase(Clase clase) {this.clase = clase;}
	public String getTipoOperacion() {return tipoOperacion;}
	public void setTipoOperacion(String tipoOperacion) {this.tipoOperacion = tipoOperacion;}
	public Date getFecha() {return fecha;}
	public void setFecha(Date fecha) {this.fecha = fecha;}
	public void setImporte(BigDecimal importe) {this.importe = importe;}
	public BigDecimal getImporte() {return importe;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public void setConcepto(String concepto) {this.concepto = concepto;}
	public String getConcepto() {return concepto;}
	public String getConceptoAbreviado() {return concepto!=null?this.concepto.substring(0, 25):"";	}
	public void setTraspaso(String traspaso) {this.traspaso = traspaso;}
	public String getTraspaso() {return traspaso;}
		
}
