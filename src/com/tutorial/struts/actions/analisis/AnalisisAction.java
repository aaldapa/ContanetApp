package com.tutorial.struts.actions.analisis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import com.tutorial.domain.entidades.baseDatos.VistaApunteClases;
import com.tutorial.domain.entidades.baseDatos.VistaApunteGrupos;
import com.tutorial.domain.entidades.baseDatos.VistaApunteMes;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.analisis.VistaApunteMesesBean;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.LoginBean;

public class AnalisisAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
				 throws Exception 
	{
		//--- Compruebo que el usuario esta logado. 
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
		return mapping.findForward("failure");
		
		String tipoGrafica="meses";
		Integer idCuenta=null;
		Integer anio=new Integer(FechaUtil.getFechaActual().substring(6, 10));
		Integer idGrupo=null;
		
		
		//Si idContabilidad se ha pasado en la llamada al action como parametro...
		if (request.getParameter("idCuenta")!=null)
			idCuenta=new Integer(request.getParameter("idCuenta"));
		if (request.getParameter("anio")!=null)
			anio=new Integer(request.getParameter("anio"));
		if (request.getParameter("idGrupo")!=null)
			idGrupo=new Integer(request.getParameter("idGrupo"));
		if (request.getParameter("tipoGrafica")!=null)
			tipoGrafica=request.getParameter("tipoGrafica");
		
		response.setContentType("text/json");
		
		StringWriter out = new StringWriter();
		JSONObject objetoPrincipal = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		//Grafica por meses
		if (StringUtils.equals("meses", tipoGrafica)){
			List<VistaApunteMes>vistaMes=ServiceUtils.getIAnalisisService().getVistaApuntesMes(anio, idCuenta);
		
			for (VistaApunteMes apt:vistaMes){
				VistaApunteMesesBean vistaJson=
					new VistaApunteMesesBean(
						apt.getIdVista(), 
						apt.getIngresos(), 
						apt.getGastos().negate(),
						apt.getDiferencias(),apt.getMesNumero(),  new Integer(apt.getAnio()), apt.getMes());
			
				jsonArray.add(vistaJson);
			}

		}
		//Grafica por grupo
		else if(StringUtils.equals("grupos", tipoGrafica)){
			List<VistaApunteGrupos>vistaGrupo=ServiceUtils.getIAnalisisService().getVistaApuntesGrupo(anio, idCuenta);
			
			for (VistaApunteGrupos vista:vistaGrupo)
				jsonArray.add(vista);
		}
		//Grafica por clase
		else if(StringUtils.equals("clases", tipoGrafica)){
			List<VistaApunteClases>vistaClase=ServiceUtils.getIAnalisisService().getVistaApuntesClases(anio, idCuenta, idGrupo);
			
			for (VistaApunteClases vista:vistaClase)
				jsonArray.add(vista);
		}
		
		objetoPrincipal.put("vista", jsonArray);
		
		objetoPrincipal.writeJSONString(out);
		
		//Muestro en consola para ver la contruccion del json
		System.out.println(out.toString());
		
		PrintWriter pw = response.getWriter();
		pw.write(out.toString());
		pw.flush();
		pw.close();
		
		return null;
	}
	
}



