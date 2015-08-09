package com.tutorial.struts.forms.contabilidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;

public class ContabilidadForm extends BaseForm implements Serializable {

	private String nombre,notas,baja, predeterminada;
	private Integer idUsuario;
	
	private List<Usuario> lstUsuarios;
	private String [] idsUsuariosSelected;
	private ArrayList<Integer> lstIdsContabilidadesUsuario=new ArrayList<Integer>();
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		
		if (getNombre().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.contabilidades.formulario.campo.requerido","Nombre"));
		if (getNotas().trim().isEmpty())
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.contabilidades.formulario.campo.requerido","Descripción"));
		
		return errors;
		
	}

	public String getNombre() {	return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getNotas() {return notas;}
	public void setNotas(String notas) {this.notas = notas;}
	public String getBaja() {return baja;}
	public void setBaja(String baja) {this.baja = baja;}
	public void setPredeterminada(String predeterminada) {this.predeterminada = predeterminada;}
	public String getPredeterminada() {return predeterminada;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
	public Integer getIdUsuario() {return idUsuario;}
	public void setLstUsuarios(List<Usuario> lstUsuarios) {this.lstUsuarios = lstUsuarios;}
	public void setIdsUsuariosSelected(String[] idsUsuariosSelected) {this.idsUsuariosSelected = idsUsuariosSelected;}
	public void setLstIdsContabilidadesUsuario(ArrayList<Integer> lstIdsContabilidadesUsuario) {this.lstIdsContabilidadesUsuario = lstIdsContabilidadesUsuario;}
	public String[] getIdsUsuariosSelected() {return idsUsuariosSelected;}
	public ArrayList<Integer> getLstIdsContabilidadesUsuario() {return lstIdsContabilidadesUsuario;}
	
	public List<Usuario> getLstUsuarios() {
		lstUsuarios=ServiceUtils.getIUsuarioService().getFamiliares(idUsuario);
		return lstUsuarios;
	}


	
	
}
