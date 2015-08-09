package com.tutorial.struts.actions.apunte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.ApunteFormDatasource;
import com.tutorial.struts.forms.apunte.ApunteListForm;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class BusquedaApuntesAction extends CustomBaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ApunteListForm apunteListForm=(ApunteListForm) form;
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		if (apunteListForm==null){
			apunteListForm=new ApunteListForm();
		}
		
		//Carga de lstContabilidades
		apunteListForm.setLstContabilidades(		
			ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario())
		);
		
		List<Cuenta>lstCuentas=new ArrayList<Cuenta>();		
		apunteListForm.setLstCuentas(lstCuentas);
		if (apunteListForm.getAccion()==null){
			//Tratamiento para seleccionar las opciones del apunte por defecto cuando queremos hacer uno nuevo
			ApuntePorDefectoForm apuntePorDefectoForm=ServiceUtils.getIApuntePorDefectoService().getApuntePorDefecto(loginBean.getUsuario().getIdUsuario());
			if (apuntePorDefectoForm!=null){
				apunteListForm.setIdContabilidad(apuntePorDefectoForm.getIdContabilidad()!=null ? apuntePorDefectoForm.getIdContabilidad() : new Integer("0"));
				apunteListForm.setIdCuenta(apuntePorDefectoForm.getIdCuenta()!=null ? apuntePorDefectoForm.getIdCuenta() : new Integer("0"));
				apunteListForm.setIdGrupo(apuntePorDefectoForm.getIdGrupo() !=null ? apuntePorDefectoForm.getIdGrupo(): new Integer("0"));
				apunteListForm.setIdClase(apuntePorDefectoForm.getIdClase() !=null ?apuntePorDefectoForm.getIdClase() : new Integer("0"));
			}
		}
		/*
		if (!apuntePorDefectoForm.getTipoApunte().isEmpty())
			apunteListForm.setTipoApunte(apuntePorDefectoForm.getTipoApunte());
		*/
		
		//Si la accion ejecutada es exportar a pdf
		if (apunteListForm.getAccion()!=null && 
				(apunteListForm.getAccion().equals(recursos.getMessage("abrir.pdf"))
				||apunteListForm.getAccion().equals(recursos.getMessage("abrir.xls")))
		){
			
			//ruta al directorio de ficheros
			String contextoRaiz = "/reportes";
			String ruta = getServlet().getServletContext().getRealPath(contextoRaiz);			
			
			//Carga de datos para la generacion de informe pdf con Ireport
			String fileName="";	
			if (apunteListForm.getAccion().equals(recursos.getMessage("abrir.pdf")))
				fileName=ruta+"/reportApuntes.jrxml";

			else
				fileName=ruta+"/reportApuntesXLS.jrxml";
			
			@SuppressWarnings("rawtypes")
			Map parameters = new HashMap();
		    parameters.put("TITULO", (recursos.getMessage("apunte.listado.titulo")));
		    parameters.put("FECHA_I", apunteListForm.getFechaInicioStr());
		    parameters.put("FECHA_F", apunteListForm.getFechaFinStr());
		    if (apunteListForm.getIdContabilidad().intValue()>0)
		    	parameters.put("CONTABILIDAD", 
		    			ServiceUtils.getIReportService().getContabilidad(apunteListForm.getIdContabilidad()).getNombreContabilidad());
		    
		    if (apunteListForm.getIdCuenta().intValue()>0)
		    	parameters.put("CUENTA", 
		    			ServiceUtils.getIReportService().getCuenta(apunteListForm.getIdCuenta()).getNotas());
		    
		    if (apunteListForm.getIdGrupo().intValue()>0)
		    	parameters.put("GRUPO", 
		    			ServiceUtils.getIReportService().getGrupo(apunteListForm.getIdGrupo()).getNombre());
		    
		    if (apunteListForm.getIdClase().intValue()>0)
		    	parameters.put("CLASE", 
		    			ServiceUtils.getIReportService().getClase(apunteListForm.getIdClase()).getNombre());
		    
		    parameters.put("TOTALINGRESOS", apunteListForm.getTotalIngresos());
		    parameters.put("TOTALGASTOS", apunteListForm.getTotalGastos());
		    
		    ApunteFormDatasource apunteFormDatasource=new ApunteFormDatasource();
		    apunteFormDatasource.getLstApuntesForm().addAll(
		    		apunteListForm.getLstApuntesForm());
		    
		    if (apunteListForm.getAccion().equals(recursos.getMessage("abrir.pdf")))
		    	ServiceUtils.getIReportService().abrirPDFDataSource(response, fileName, parameters,apunteFormDatasource);
		    else if (apunteListForm.getAccion().equals(recursos.getMessage("abrir.xls")))
		    	ServiceUtils.getIReportService().abrirXLSDataSource(response, fileName, parameters,apunteFormDatasource);
		    	//this.abrirXls(response, fileName, parameters,apunteFormDatasource);
		    
		    return null;		
		}
		
		//Si la accion ejecutada es nuevo, limpiamos el panel de busqueda
		if (apunteListForm.getAccion()!=null 
				&& (apunteListForm.getAccion().equals(recursos.getMessage("boton.new"))
				|| apunteListForm.getAccion().equals(recursos.getMessage("boton.copy"))
				|| apunteListForm.getAccion().equals(recursos.getMessage("boton.guardar"))
				|| apunteListForm.getAccion().equals(recursos.getMessage("boton.update"))
				|| apunteListForm.getAccion().equals(recursos.getMessage("boton.organizar"))
				|| apunteListForm.getAccion().equals(recursos.getMessage("boton.delete")))
				){
			apunteListForm.setConcepto("");
			apunteListForm.setIdGrupo(0);
			apunteListForm.setIdClase(0);
		}
		
		request.getSession().setAttribute("listadoApuntesForm", apunteListForm);
		return mapping.findForward("success");
		
	}
}
