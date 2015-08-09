package com.tutorial.struts.forms.cuenta;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.forms.BaseForm;

public class CuentaListForm extends BaseForm implements Serializable{
	
	private String []ids=null;
	private Integer idUsuario;
	private Contabilidad contabilidad=new Contabilidad();
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update
		if (recursos.getMessage("cuentas.listado.delete").equals(getAccion())
				|| recursos.getMessage("cuentas.listado.update").equals(getAccion()))
		{
			//Si no se seleccionan cuenta
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.cuentas.listado.no.seleccion"));
			}
			//Si se seleccionan multiples cuenta
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.cuentas.listado.seleccion.multiple"));
			}
			else{
				//Guardamos el id seleccionado de la lista de Strings en la propiedad id
				for (String idArray:ids){
					setId(new Integer(idArray));
					break;
				}
			}
		}
		//Si el boton pulsado ha sido ver
		if (getAccion()==null)
			setAccion(recursos.getMessage("cuentas.listado.view"));
		
		return errors;
	}
	
	public void setIds(String[] ids) {this.ids = ids;}
	public String[] getIds() {return ids;}
	public Integer getIdUsuario() {	return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}

	public void setContabilidad(Contabilidad contabilidad) {this.contabilidad = contabilidad;}
	public Contabilidad getContabilidad() {return contabilidad;}
	
}
