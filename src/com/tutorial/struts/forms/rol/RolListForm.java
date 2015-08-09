package com.tutorial.struts.forms.rol;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.forms.BaseForm;

public class RolListForm extends BaseForm {

	private String[] ids=null;
	
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update
		if (recursos.getMessage("roles.listado.delete").equals(getAccion())
				|| recursos.getMessage("roles.listado.update").equals(getAccion()))
		{
			//Si no se seleccionan roles
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.roles.listado.no.seleccion"));
			}
			//Si se seleccionan multiples roles
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.roles.listado.seleccion.multiple"));
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
			setAccion(recursos.getMessage("roles.listado.view"));
		
		return errors;
	}
	
	public void setIds(String[] ids) {this.ids = ids;}
	public String[] getIds() {return ids;}
	
	
}
