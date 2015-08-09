package com.tutorial.struts.service.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.apunte.ApunteFormDatasource;
import com.tutorial.struts.hibernate.util.HibernateUtil;


public class ReportServiceImpl implements IReportService {

	/**
	 * Singleton Instance of ReportServiceImpl
	 */
	private static ReportServiceImpl reportServiceImpl = new ReportServiceImpl();
	
	/**
	 * Creates Instance of {@link ReportServiceImpl}
	 */
	private ReportServiceImpl(){		
	}
	
	/***
	 * Gets Instance of ReportServiceImpl
	 * @return ReportServiceImpl Implementation
	 */
	public static ReportServiceImpl getInstance(){	
		return reportServiceImpl;
	}

	@Override
	public void abrirPDFDataSource(HttpServletResponse response, String fileName, Map parameters, ApunteFormDatasource dataSource )
	 throws IOException{
		try
	    {
			OutputStream outputStream = null;
		    outputStream = response.getOutputStream();
			
			String nombreInforme = "InformeApuntes";
			
			JasperReport jasperReport;
		    JasperPrint jasperPrint;
		    //1-Compilamos el archivo XML y lo cargamos en memoria
		    jasperReport = JasperCompileManager.compileReport(fileName);

		    //2-Llenamos el reporte con la información y parámetros necesarios
		    jasperPrint = JasperFillManager.fillReport(
		    		//jasperReport,parameters, this.getConnection());
		    		jasperReport, parameters, dataSource);

		    //establecer el Content Type a PDF
		    response.setContentType("application/pdf");	
		    response.setHeader("content-disposition", "attachment;filename="+nombreInforme+".pdf" );
		  
		    //3-Exportamos el reporte a pdf y lo guardamos en disco
		    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	     
		    //4- Abrir el pdf  
		    JRExporter exporter = new JRPdfExporter();
	      		        
		    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(nombreInforme+".pdf"));
	     
		    exporter.exportReport();
	      
	        //outputStream.flush();
		    //outputStream.close();
		    
		    //Abrir visualizador JasperViewer
		    //JasperViewer.viewReport(jasperPrint, false);
		   
	    }
		catch (JRException e)
	    {
	      // display stack trace in the browser
	      StringWriter stringWriter = new StringWriter();
	      PrintWriter printWriter = new PrintWriter(stringWriter);
	      e.printStackTrace(printWriter);
	      response.setContentType("text/plain");
	      response.getOutputStream().print(stringWriter.toString());
	    }
		catch (IOException e){e.printStackTrace();}
	    
	}

	
	/*
	@Override
	public void abrirPDFDataSource(HttpServletResponse response, String fileName, Map parameters, ApunteFormDatasource dataSource )
	  throws IOException{
		try{
			String nombreInforme = "InformeApuntes.pdf";
			ServletOutputStream servletOutputStream = response.getOutputStream();

			OutputStream outputStream = null;
		    outputStream = response.getOutputStream();
			
			
			JasperReport jasperReport;
		    JasperPrint jasperPrint;
		    //1-Compilamos el archivo XML y lo cargamos en memoria
		    jasperReport = JasperCompileManager.compileReport(fileName);

		    //2-Llenamos el reporte con la información y parámetros necesarios
		    jasperPrint = JasperFillManager.fillReport(
		    		//jasperReport,parameters, this.getConnection());
		    		jasperReport, parameters, dataSource);

		    byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, dataSource);
			
			//establecer el Content Type a PDF
		    response.setContentType("application/pdf");	
		    //response.setHeader("content-disposition", "attachment;filename="+nombreInforme+".pdf" );
			response.setContentLength(bytes.length);
			
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			
		}
		
		catch (JRException e)
	    {
	      // display stack trace in the browser
	      StringWriter stringWriter = new StringWriter();
	      PrintWriter printWriter = new PrintWriter(stringWriter);
	      e.printStackTrace(printWriter);
	      response.setContentType("text/plain");
	      response.getOutputStream().print(stringWriter.toString());
	    }
		catch (IOException e){e.printStackTrace();}
	}
*/
	
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
	
	@Override
	public Contabilidad getContabilidad(Integer idContabilidad) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Contabilidad contabilidad=null;
		
		try{
			transaction=session.beginTransaction();
			contabilidad=(Contabilidad)session.get(Contabilidad.class, idContabilidad);
			
			session.flush();
			session.getTransaction().commit();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return contabilidad;
	}
	
	@Override
	public Cuenta getCuenta(Integer idCuenta) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Cuenta cuenta=null;
		
		try{
			transaction=session.beginTransaction();
			cuenta=(Cuenta)session.get(Cuenta.class, idCuenta);
			
			session.flush();
			session.getTransaction().commit();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return cuenta;
	}
	
	@Override
	public Grupo getGrupo(Integer idGrupo) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Grupo grupo=null;
		
		try{
			transaction=session.beginTransaction();
			grupo=(Grupo)session.get(Grupo.class, idGrupo);
			
			session.flush();
			session.getTransaction().commit();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return grupo;
	}
	
	@Override
	public Clase getClase(Integer idClase) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		Clase clase=null;
		
		try{
			transaction=session.beginTransaction();
			clase=(Clase)session.get(Clase.class, idClase);
			
			session.flush();
			session.getTransaction().commit();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return clase;
	}

	@Override
	public void abrirXLSDataSource(HttpServletResponse response,
			String fileName, Map parameters, ApunteFormDatasource dataSource) {
		try{
			OutputStream outputStream = null;
		    outputStream = response.getOutputStream();
		    
			String nombreInforme = "InformeApuntes";
			
			JasperReport jasperReport;
		    JasperPrint jasperPrint;
		    
		    //1-Compilamos el archivo XML y lo cargamos en memoria
		    jasperReport = JasperCompileManager.compileReport(fileName);
	
		    //2-Llenamos el reporte con la información y parámetros necesarios
		    jasperPrint = JasperFillManager.fillReport(
		    //jasperReport,parameters, this.getConnection());
		    jasperReport, parameters, dataSource);
			
		    // establecer el Content Type a XLS
		    response.setContentType("application/xls");
		    response.setHeader("content-disposition", "attachment;filename="+nombreInforme+".xls" );
		    		    
		    //Exportar xls
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,outputStream);
			
			//Excel specific parameter
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			
			exporterXLS.exportReport();
					
			outputStream.flush();
			outputStream.close();
		}
		
		catch (IOException e){e.printStackTrace();}
		catch (JRException e){e.printStackTrace();}
		
	}
	
}
