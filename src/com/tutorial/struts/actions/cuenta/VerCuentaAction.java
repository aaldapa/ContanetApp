package com.tutorial.struts.actions.cuenta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.cuenta.CuentaForm;
import com.tutorial.struts.forms.cuenta.CuentaListForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class VerCuentaAction extends CustomBaseAction {
	/**
   	 * Este método simplemente reenvia al estado de success,
   	 * el cual debería representar la página cuenta.jsp.
   	 **/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		CuentaListForm formulario=(CuentaListForm) form;
		CuentaForm cuentaForm=new CuentaForm();
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		List<Contabilidad> lstContabilidades=new ArrayList<Contabilidad>();
		
		//Si la accion seleccionada es distinta de nuevo
		if (!formulario.getAccion().equals(recursos.getMessage("cuentas.listado.new"))&& formulario.getId()!=null){
			cuentaForm=ServiceUtils.getICuentaService().getCuenta(formulario.getId());
		}
		//Si la accion seleccionada es nuevo
		else{
			//selecciono la predeterminada
			cuentaForm.setIdContabilidad(ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario()).getIdContabilidad());
		}
		
		//cargo el listado de contabilidades que tiene acceso el usuario
		lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario());
		cuentaForm.setLstContabilidades(lstContabilidades);
		
		
		cuentaForm.setAccion(formulario.getAccion());
		
		//Carga de datos para la generacion de informe pdf con Ireport
		//String fileName="/report1.jrxml";
		//Map parameters = new HashMap();
	    //parameters.put("TITULO", recursos.getMessage("cuentas.listado.new"));
		//this.abrirPDF(recursos, fileName, parameters);
	  
		
		request.setAttribute("cuentaForm", cuentaForm);
		return  mapping.findForward("success");
    }
	
	
	
	
	/*PRUEBAS*/
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
	      conn = DriverManager.getConnection("jdbc:mysql://localhost/contanetapp","root", "anerel80");
	      conn.setAutoCommit(false);
	    }
	    catch (SQLException e) {
	      System.out.println("Error de conexión: " + e.getMessage());
	      System.exit(4);
	    }
	    return  conn;
	}
	
	private void abrirPDF(MessageResources recursos, String fileName, Map parameters){
		/*Prueba jasper report
		 * 
		 * */
		//ruta al directorio de ficheros
		String contextoRaiz = "/WEB-INF/classes/reportes";
		String ruta = getServlet().getServletContext().getRealPath(contextoRaiz);
		
		JasperReport jasperReport;
	    JasperPrint jasperPrint;
	    try
	    {
	      //1-Compilamos el archivo XML y lo cargamos en memoria
	      jasperReport = JasperCompileManager.compileReport(ruta+fileName);

	      //2-Llenamos el reporte con la información y parámetros necesarios
	      jasperPrint = JasperFillManager.fillReport(
	          jasperReport,parameters, this.getConnection());

	      //3-Exportamos el reporte a pdf y lo guardamos en disco
	     /* JasperExportManager.exportReportToPdfFile(
	          jasperPrint, "D:/eclipse Helios/workspace/AplicacionTutorial/src/reportes/report2.pdf");
	      */
	      //4- Abrir el pdf
	      JasperViewer.viewReport(jasperPrint, false);
	      	 
	    }
	    catch (JRException e)
	    {
	      e.printStackTrace();
	    }
	}
}
