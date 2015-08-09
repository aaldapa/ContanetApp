package com.tutorial.struts.actions.comun;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.OptionBean;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class RecargarCombosAction extends CustomBaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
				throws Exception{
		
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		Integer idContabilidad=null;
		Integer idGrupo=null;
		
		//Si idContabilidad se ha pasado en la llamada al action como parametro...
		if (request.getParameter("idContabilidad")!=null)
			idContabilidad=new Integer(request.getParameter("idContabilidad"));
		
		//Si idGrupo se ha pasado en la llamada al action como parametro...
		if (request.getParameter("idGrupo")!=null)
			idGrupo=new Integer(request.getParameter("idGrupo"));
		
		
		response.setContentType("text/json");
		
		StringWriter out = new StringWriter();
		JSONObject objetoPrincipal = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		
		//Si se le pasa el idContabilidad como parametro, deseamos recargar las cuentas
		if(idContabilidad!=null && idContabilidad>0){
			List<Cuenta>lstCuentas=ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
			
			for (Cuenta cuenta:lstCuentas){
				OptionBean optionBean=new OptionBean(cuenta.getIdCuenta(), cuenta.getNotas());
				jsonArray.add(optionBean);
			}
		}
		objetoPrincipal.put("lstCuentas", jsonArray);
		
		//Si se le pasa el idGrupo como parametro, deseamos recargar las clases
		if(idGrupo!=null && idGrupo>0){
			jsonArray = new JSONArray();
			List<Clase>lstClases=ServiceUtils.getIClaseService().getLstClases(idGrupo);
			for (Clase clase:lstClases){
				OptionBean optionBean=new OptionBean(clase.getIdClase(), clase.getNombre());
				jsonArray.add(optionBean);
			}
		}
		objetoPrincipal.put("lstClases", jsonArray);
		
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
