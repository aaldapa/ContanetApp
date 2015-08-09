package com.tutorial.domain.entidades.baseDatos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Usuario implements Serializable {

	private Integer idUsuario;
	private Perfil perfil;
	private Familia familia;
	private String nombre, apellidos,email,password;
	private String baja;
	private String nombreCompleto;
		
	private Set contabilidadeses=new HashSet();
	private Set contabilidadesUsuarioses=new HashSet();
	private Set cuentases=new HashSet();
	private Set apuntePorDefectoes=new HashSet();
	private Set depositoses=new HashSet();
	
	public Usuario() {
		super();
	}
	
	public Integer getIdUsuario() {return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public void setPerfil(Perfil perfil) {this.perfil = perfil;}
	public Perfil getPerfil() {return perfil;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getApellidos() {return apellidos;}
	public void setApellidos(String apellidos) {this.apellidos = apellidos;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public void setContabilidadeses(Set contabilidadeses) {this.contabilidadeses = contabilidadeses;}
	public Set getContabilidadeses() {return contabilidadeses;}
	public void setContabilidadesUsuarioses(Set contabilidadesUsuarioses) {this.contabilidadesUsuarioses = contabilidadesUsuarioses;}
	public Set getContabilidadesUsuarioses() {return contabilidadesUsuarioses;}
	public void setCuentases(Set cuentases) {this.cuentases = cuentases;}
	public Set getCuentases() {	return cuentases;}
	public void setBaja(String baja) {this.baja = baja;}
	public String getBaja() {return baja;}
	public void setFamilia(Familia familia) {this.familia = familia;}
	public Familia getFamilia() {return familia;}
	public void setNombreCompleto(String nombreCompleto) {this.nombreCompleto = nombreCompleto;}
	public String getNombreCompleto() {
		return getNombre()+" "+getApellidos() ;
	}

	public void setApuntePorDefectoes(Set apuntePorDefectoes) {this.apuntePorDefectoes = apuntePorDefectoes;}
	public Set getApuntePorDefectoes() {return apuntePorDefectoes;}
	public Set getDepositoses() {return depositoses;}
	public void setDepositoses(Set depositoses) {this.depositoses = depositoses;}
	
}
