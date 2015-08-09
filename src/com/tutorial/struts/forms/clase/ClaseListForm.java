package com.tutorial.struts.forms.clase;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;

public class ClaseListForm extends BaseForm implements Serializable {

	private String []ids=null;
	private Integer idGrupo;
	private List<Grupo> lstGrupos;
	private Grupo grupo=new Grupo();
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update
		if (recursos.getMessage("clases.listado.delete").equals(getAccion())
				|| recursos.getMessage("clases.listado.update").equals(getAccion()))
		{
			//Si no se seleccionan clase
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.clases.listado.no.seleccion"));
			}
			//Si se seleccionan multiples clase
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.clases.listado.seleccion.multiple"));
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
			setAccion(recursos.getMessage("clases.listado.view"));
		
		return errors;
	}
	
	public void setIds(String[] ids) {this.ids = ids;}
	public String[] getIds() {return ids;}

	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	
	public List<Grupo> getLstGrupos() {
		return ServiceUtils.getIGrupoService().getLstGrupos();
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}
	
}
