package com.tutorial.struts.actions.email;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.email.EmailForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class EnvioEmailAction extends CustomBaseAction {

	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{

		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
			
		
				
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		EmailForm formulario=(EmailForm) form;
		String path=null;
		File f=null;
		
		if (form instanceof EmailForm) {
			
			String fileNewName = formulario.getFile().getFileName();
			FormFile file = formulario.getFile(); //Recuperamos la representación del archivo
			//ubicación y nombre del archivo a guardar
			
			//ruta al directorio de ficheros
			String contextoRaiz = "/WEB-INF/classes/adjuntos";
			path = getServlet().getServletContext().getRealPath(contextoRaiz).concat("\\")+fileNewName;
			//Controlamos las condiciones para subirlo
						
			if (file.getFileSize()>0){
				try {
					// se comprueba que la ruta exista
					f = new File(path);
					if (makeSureDirectoryExists(parent(f))) {
						// Se graba en la ruta del fichero;
						FileOutputStream out = new FileOutputStream(f);
						out.write(file.getFileData());
						out.flush();
						out.close();
					}
				}
		
				catch (Exception ex) {
					System.out.println ("Lanzada excepcion en EnvioEmailAction:" +ex);
					return null ;
				}
				//Destruimos el archivo temporal
				file.destroy();
				System.out.print( "Subido el fichero" );
				//Retornamos y continuamos en
			
			}
			request.setAttribute("resultadoEnvio",
	     			ServiceUtils.getIEmailService().enviarEmail(recursos, formulario.getAsunto(), formulario.getTexto(), f));
	     	//true);
			
			return mapping.findForward("success");
		}
	
		//Nunca sucederá en este ejemplo
		return null ;
	}

	
	/**
	* Devuelve la ruta padre del subdirectorio actual
	* @param File --> El archivo del cual se quiere sacar su directorio o directorio padre
	* @return File --> Crea un archivo con la ruta del directorio padre
	*/
	private File parent(File f) {
		String dirname = f.getParent();
		if (dirname == null ) {
			return new File(File.separator);
		}
		return new File(dirname);
	}

	/**
	* Crear un subdirectorio si este no existe
	* @param dir --> El path del archivo (dirección + nombre)
	* @return True -> Existe o se ha creado False --> No existe y no se ha podido crear
	*/
	private boolean makeSureDirectoryExists(File dir) {

		if (!dir.exists()) {
			if (makeSureDirectoryExists(parent(dir)))
				dir.mkdir();
			else
				return false ;
		}
		return true ;
	}
	
}
