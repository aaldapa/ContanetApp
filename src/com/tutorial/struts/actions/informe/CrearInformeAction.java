package com.tutorial.struts.actions.informe;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.informe.InformeForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.LoginBean;


public class CrearInformeAction extends CustomBaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		InformeForm informeForm=(InformeForm) form;
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
						
		//ruta al directorio de ficheros
		String contextoRaiz = "/reportes";
		String ruta = getServlet().getServletContext().getRealPath(contextoRaiz);			
		
		//Carga de datos para la generacion de informe pdf con Ireport
		String fileName="";	
		if (informeForm.isDetallado())
			fileName=ruta+"/informeApuntesDetalle.jrxml";
		else
			fileName=ruta+"/informeApuntesResumen.jrxml";
						
		@SuppressWarnings("rawtypes")
		Map parameters = new HashMap();
	    if (informeForm.isDetallado())
	    	parameters.put("P_TITULO", recursos.getMessage("informe.titulo.ordenado.meses"));
	    else
	    	parameters.put("P_TITULO", recursos.getMessage("grafica.titulo.ordenado.meses"));
	    
	    parameters.put("P_FINICIO", FechaUtil.formatearADate(informeForm.getFechaInicioStr()));
	    parameters.put("P_FFIN", FechaUtil.formatearADate(informeForm.getFechaFinStr())); 
	    
	    if (informeForm.getIdContabilidad().intValue()>0)
	    	parameters.put("P_CONTABILIDAD", 
	    			ServiceUtils.getIReportService().getContabilidad(informeForm.getIdContabilidad()).getNombreContabilidad());
	    
	    parameters.put("P_IDCUENTA", informeForm.getIdCuenta());
	    
	    if (informeForm.getIdCuenta().intValue()>0)
	    	parameters.put("P_CUENTA", 
	    			ServiceUtils.getIReportService().getCuenta(informeForm.getIdCuenta()).getNotas());
	   
	    //Si grupo viene cargado
	    if (informeForm.getIdGrupo().intValue()>0){
	    	parameters.put("P_GRUPO", 
	    			ServiceUtils.getIReportService().getGrupo(informeForm.getIdGrupo()).getNombre());
	    	 parameters.put("P_IDGRUPO", informeForm.getIdGrupo());
	    	 if (informeForm.isDetallado())
	    		 fileName=ruta+"/informeApuntesDetalleGrupo.jrxml";
	    	 else
	    		 fileName=ruta+"/informeApuntesResumenGrupo.jrxml";
	    }
	    //Si clase viene cargado
	    if (informeForm.getIdClase().intValue()>0){
	    	parameters.put("P_CLASE", 
	    			ServiceUtils.getIReportService().getClase(informeForm.getIdClase()).getNombre());
	    	parameters.put("P_IDCLASE", informeForm.getIdClase());
	    	if (informeForm.isDetallado())
	    		fileName=ruta+"/informeApuntesDetalleClase.jrxml";
	    	else
	    		fileName=ruta+"/informeApuntesResumenClase.jrxml";
	    }
	    
	    this.abrirPDF(response, fileName, parameters);
	    	    		
		request.setAttribute("informeForm", informeForm);
		return null;
	}
	
	
	private Connection getConnection(){
		Connection conn = null;
		
		 // Cargamos el driver JDBC
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println("MySQL JDBC Driver not found.");
	      System.exit(1);
	    }
	    //Para iniciar el Logger.
	    //inicializaLogger();
	    try {
	      conn = DriverManager.getConnection("jdbc:mysql://localhost/contanetapp","root", "root");
	      conn.setAutoCommit(false);
	    }
	    catch (SQLException e) {
	      System.out.println("Error de conexión: " + e.getMessage());
	      System.exit(4);
	    }
	    return  conn;
	}
	
	public void abrirPDF(HttpServletResponse response, String fileName, Map parameters){
		try
	    {
			OutputStream outputStream = null;
		    outputStream = response.getOutputStream();
			
			String nombreInforme = "InformeApuntes_"+System.currentTimeMillis();
			
			JasperReport jasperReport;
		    JasperPrint jasperPrint;
		    
		      //1-Compilamos el archivo XML y lo cargamos en memoria
		      jasperReport = JasperCompileManager.compileReport(fileName);
	
		      //2-Llenamos el reporte con la información y parámetros necesarios
		      jasperPrint = JasperFillManager.fillReport(
		          jasperReport,parameters, this.getConnection());
		    		
	
		      // establecer el Content Type a PDF
		      response.setContentType("application/pdf");
		      response.setHeader("content-disposition", "attachment;filename="+nombreInforme+".pdf" );
			  
		      
		      //3-Exportamos el reporte a pdf y lo guardamos en disco
		      JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		     
		      //4- Abrir el pdf
		      
		      outputStream.flush();
		      outputStream.close();
		      response.getOutputStream().flush();
		      response.getOutputStream().close(); 
	    }
		catch (IOException e){e.printStackTrace();}
		catch (JRException e){e.printStackTrace();}
	    
	}
}
