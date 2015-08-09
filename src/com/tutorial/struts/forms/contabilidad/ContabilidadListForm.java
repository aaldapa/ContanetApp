package com.tutorial.struts.forms.contabilidad;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.forms.BaseForm;

public class ContabilidadListForm extends BaseForm {

	private String[] ids=null;
	private Integer idUsuario;

	private List<String> idSelected;
	
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update
		if (recursos.getMessage("contabilidades.listado.delete").equals(getAccion())
				|| recursos.getMessage("contabilidades.listado.update").equals(getAccion())
				|| recursos.getMessage("contabilidades.listado.cuentas").equals(getAccion()))
		{
			//Si no se seleccionan contabilidades
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.contabiliades.listado.no.seleccion"));
			}
			//Si se seleccionan multiples contabilidades
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.contabiliades.listado.seleccion.multiple"));
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
			setAccion(recursos.getMessage("contabilidades.listado.view"));
		
		return errors;
	}
	
	public void setIds(String[] ids) {this.ids = ids;}
	public String[] getIds() {return ids;}

	public void setIdSelected(List<String> idSelected) {
		this.idSelected = idSelected;
	}

	public List<String> getIdSelected() {
		return idSelected;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}


	
}
