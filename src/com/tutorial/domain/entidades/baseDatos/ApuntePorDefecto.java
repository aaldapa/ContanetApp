package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;

public class ApuntePorDefecto implements Serializable {

	private Integer idApuntePorDefecto;
	private Usuario usuario;
	private Contabilidad contabilidadPorDefecto;
	private Cuenta cuentaPorDefecto;
	private Grupo grupoPorDefecto;
	private Clase clasePorDefecto;
	private String tipoOperacion;
	
	
	public ApuntePorDefecto() {
		super();
	}

	public ApuntePorDefecto(Integer idApuntePorDefecto,Usuario usuario,
			Contabilidad contabilidadPorDefecto, Cuenta cuentaPorDefecto,
			Grupo grupoPorDefecto, Clase clasePorDefecto, String tipoOperacion) {
		super();
		this.idApuntePorDefecto = idApuntePorDefecto;
		this.contabilidadPorDefecto = contabilidadPorDefecto;
		this.cuentaPorDefecto = cuentaPorDefecto;
		this.grupoPorDefecto = grupoPorDefecto;
		this.clasePorDefecto = clasePorDefecto;
		this.tipoOperacion = tipoOperacion;
		this.usuario=usuario;
	}

	public Integer getIdApuntePorDefecto() {return idApuntePorDefecto;}
	public void setIdApuntePorDefecto(Integer idApuntePorDefecto) {this.idApuntePorDefecto = idApuntePorDefecto;}
	public Contabilidad getContabilidadPorDefecto() {return contabilidadPorDefecto;}
	public void setContabilidadPorDefecto(Contabilidad contabilidadPorDefecto) {this.contabilidadPorDefecto = contabilidadPorDefecto;}
	public Cuenta getCuentaPorDefecto() {return cuentaPorDefecto;}
	public void setCuentaPorDefecto(Cuenta cuentaPorDefecto) {this.cuentaPorDefecto = cuentaPorDefecto;}
	public Grupo getGrupoPorDefecto() {return grupoPorDefecto;}
	public void setGrupoPorDefecto(Grupo grupoPorDefecto) {this.grupoPorDefecto = grupoPorDefecto;}
	public Clase getClasePorDefecto() {return clasePorDefecto;}
	public void setClasePorDefecto(Clase clasePorDefecto) {this.clasePorDefecto = clasePorDefecto;}
	public String getTipoOperacion() {return tipoOperacion;}
	public void setTipoOperacion(String tipoOperacion) {this.tipoOperacion = tipoOperacion;}
	public void setUsuario(Usuario usuario) {this.usuario = usuario;}
	public Usuario getUsuario() {return usuario;}
		
}
