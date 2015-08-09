package com.tutorial.struts.forms.email;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.MessageResources;

public class EmailForm extends ActionForm {

	private String asunto;
	private String texto;
	private String rutaFichero;
	
	//Atributos para recoger el fichero
	private FormFile file;
	private String contenttype;
    private int size;
    private String nombreFichero;
    private String textoFichero;
	
    /*
     * (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * Comprueba si el archivo que se ha seleccionado supera el tamaño maximo permitido
     */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		ActionErrors errors=new ActionErrors();
		
		//has the maximum length been exceeded?
        Boolean maxLengthExceeded =
            (Boolean) request.getAttribute(
                MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

        if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
            if (errors == null) {
                errors = new ActionErrors();
            }
            errors.add(ActionMessages.GLOBAL_MESSAGE , new ActionMessage("global.limite.maximo.excedido"));
        }

        //Validacion de campos asunto y texto del email
        if (GenericValidator.isBlankOrNull(getAsunto()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("email.formulario.asunto")));
		if (GenericValidator.isBlankOrNull(getTexto()))
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("email.formulario.texto")));
        
		return errors;			
	}

	public String getAsunto() {return asunto;}
	public void setAsunto(String asunto) {this.asunto = asunto;}
	public String getTexto() {return texto;}
	public void setTexto(String texto) {this.texto = texto;}
	public void setFile(FormFile file) {this.file = file;}
	public FormFile getFile() {return file;}
	public String getContenttype() {return contenttype;}
	public void setContenttype(String contenttype) {this.contenttype = contenttype;}
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	public String getRutaFichero() {return rutaFichero;}
	public void setRutaFichero(String rutaFichero) {this.rutaFichero = rutaFichero;}
	public String getNombreFichero() {return nombreFichero;}
	public void setNombreFichero(String nombreFichero) {this.nombreFichero = nombreFichero;}
	public String getTextoFichero() {return textoFichero;}
	public void setTextoFichero(String textoFichero) {this.textoFichero = textoFichero;}

}
