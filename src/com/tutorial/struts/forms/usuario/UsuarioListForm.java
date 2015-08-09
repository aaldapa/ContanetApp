package com.tutorial.struts.forms.usuario;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.forms.BaseForm;

public class UsuarioListForm extends BaseForm {
	
	private String[] ids=null;
	
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update
		if (recursos.getMessage("usuarios.listado.delete").equals(getAccion())
				|| recursos.getMessage("usuarios.listado.update").equals(getAccion()))
		{
			//Si no se seleccionan usuario
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.usuarios.listado.no.seleccion"));
			}
			//Si se seleccionan multiples usuario
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.usuarios.listado.seleccion.multiple"));
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
			setAccion(recursos.getMessage("usuarios.listado.view"));
		
		return errors;
	}
	
	public void setIds(String[] ids) {this.ids = ids;}
	public String[] getIds() {return ids;}


}
