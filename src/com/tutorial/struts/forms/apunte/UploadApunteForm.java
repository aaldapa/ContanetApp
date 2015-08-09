package com.tutorial.struts.forms.apunte;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.forms.BaseForm;

public class UploadApunteForm extends BaseForm implements Serializable {

	private Integer idCuenta;
	private Integer idContabilidad;
	private List<Cuenta> lstCuentas;
	private List<Contabilidad>lstContabilidades;
	private FormFile file;
	private String resultado;
	private Integer apuntesInsertados;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (idContabilidad==null || idContabilidad<1){
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.importar.contabilidades")));
		}
		else{
			if (idCuenta<1){
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.importar.cuentas")));
			}
			else{
				if (file.getFileSize()==0)
					
					errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.importar.selecion.archivo")));
				else{
					//Si no se trata de un excel mostrar error
					if (!file.getContentType().equalsIgnoreCase("application/xls") 
							&& !file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")
							&& !file.getContentType().equalsIgnoreCase("text/html"))
						
						
						errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("error.apunte.extension"));
				}
			}	
		}
		//Si existe algun error que deje de mostrarse el numero de apuntes insertados 
		if (errors.size()>0)
			apuntesInsertados=null;
		
		return errors;
	}
	
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public List<Cuenta> getLstCuentas() {return lstCuentas;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	public List<Contabilidad> getLstContabilidades() {return lstContabilidades;}
	public void setFile(FormFile file) {this.file = file;}
	public FormFile getFile() {return file;}
	public void setResultado(String resultado) {this.resultado = resultado;}
	public String getResultado() {return resultado;}
	public void setApuntesInsertados(Integer apuntesInsertados) {this.apuntesInsertados = apuntesInsertados;}
	public Integer getApuntesInsertados() {return apuntesInsertados;}
	
}
