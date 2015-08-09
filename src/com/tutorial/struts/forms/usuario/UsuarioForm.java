package com.tutorial.struts.forms.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Familia;
import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;

public class UsuarioForm extends BaseForm implements Serializable{

	private String nombre, apellidos,email,password;
	private Integer idPerfil;
	private List<Perfil> lstRoles;
	private Integer idFamilia;
	private List<Familia>lstFamilia;
	private List<Contabilidad> lstContabilidades;
	private String [] idsContabilidadesSelected;
	private ArrayList<Integer> lstIdsContabilidadesUsuario=new ArrayList<Integer>();
	
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		//MessageResources message=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (getNombre().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.usuarios.formulario.campo.requerido","Nombre"));
		if (getApellidos().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.usuarios.formulario.campo.requerido","Apellidos"));
		if (getEmail().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.usuarios.formulario.campo.requerido","Email"));
		if (getPassword().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.usuarios.formulario.campo.requerido","Password"));
		if (getIdPerfil()==null || getIdPerfil()==0)
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.usuarios.formulario.campo.requerido","Perfil"));
		
		
		
		return errors;
		
	}

	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;	}
	public String getApellidos() {return apellidos;}
	public void setApellidos(String apellidos) {this.apellidos = apellidos;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public void setIdPerfil(Integer idPerfil) {this.idPerfil = idPerfil;}
	public Integer getIdPerfil() {return idPerfil;}
	public void setIdFamilia(Integer idFamilia) {this.idFamilia = idFamilia;}
	public Integer getIdFamilia() {	return idFamilia;}
	public void setLstRoles(List<Perfil> lstRoles) {this.lstRoles = lstRoles;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public void setIdsContabilidadesSelected(String [] idsContabilidadesSelected) {this.idsContabilidadesSelected = idsContabilidadesSelected;}
	public String [] getIdsContabilidadesSelected() {return idsContabilidadesSelected;}
	public void setLstIdsContabilidadesUsuario(ArrayList<Integer> lstIdsContabilidadesUsuario) {this.lstIdsContabilidadesUsuario = lstIdsContabilidadesUsuario;}
	public void setLstFamilia(List<Familia> lstFamilia) {this.lstFamilia = lstFamilia;}
	public ArrayList<Integer> getLstIdsContabilidadesUsuario() { 
		//Cargo los valores de la lista de contabilidades con los seleccionados en la jsp
		if (idsContabilidadesSelected!=null){
			for (int i=0;i<idsContabilidadesSelected.length;i++){
				lstIdsContabilidadesUsuario.add(new Integer(idsContabilidadesSelected[i]));
			}
		}
		/*
		List<Contabilidad> lstContabilidadesUsuario= ServiceUtils.getIUsuarioService().getLstContabilidadesAccesoUsuario(getId());
		for (Contabilidad contabilidad:lstContabilidadesUsuario){
			lstIdsContabilidadesUsuario.add(contabilidad.getIdContabilidad());
		}
		*/
			
		return	lstIdsContabilidadesUsuario;
	}
	
	public List<Perfil> getLstRoles() {	return  ServiceUtils.getIUsuarioService().getLstRoles();}
	public List<Contabilidad> getLstContabilidades() {return ServiceUtils.getIContabilidadService().getLstContabilidadesAll();}
	public List<Familia> getLstFamilia() {return ServiceUtils.getIFamiliaService().getLstFamilias();}
}
