package com.tutorial.struts.actions.apunte;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tutorial.struts.forms.apunte.ApunteBean;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;
import com.tutorial.struts.utils.NumeroUtil;

public class RecargarLstApuntesAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
				throws Exception{
		
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		String fechaInicioStr=request.getParameter("fechaInicio");
		String fechaFinStr=request.getParameter("fechaFin");
		Integer idContabilidad=new Integer(request.getParameter("idContabilidad"));
		Integer idGrupo=new Integer(request.getParameter("idGrupo"));
		Integer idClase=new Integer(request.getParameter("idClase"));
		Integer idCuenta=new Integer(request.getParameter("idCuenta"));
		String concepto=request.getParameter("idConcepto");
		
		try {
			if (request.getParameter("term")!=null)
				concepto=request.getParameter("term");
		}catch (Exception e) {
			System.out.println(e);
		}
		
		response.setContentType("text/json");
		
		StringWriter out = new StringWriter();
		JSONObject objetoPrincipal = new JSONObject();
		
		
		//Si se le pasa el idContabilidad como parametro, deseamos recargar las cuentas
		if(idContabilidad!=null && idContabilidad>0){
			JSONArray jsonArray = new JSONArray();
			List<Cuenta>lstCuentas=ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
			for (Cuenta cuenta:lstCuentas){
				OptionBean optionBean=new OptionBean(cuenta.getIdCuenta(), cuenta.getNotas());
				jsonArray.add(optionBean);
			}
			objetoPrincipal.put("lstCuentas", jsonArray);
		}
		
		//Si se le pasa el idGrupo como parametro, deseamos recargar las clases
		if(idGrupo!=null && idGrupo>0){
				JSONArray jsonArray = new JSONArray();
				List<Clase>lstClases=ServiceUtils.getIClaseService().getLstClases(idGrupo);
				for (Clase clase:lstClases){
					OptionBean optionBean=new OptionBean(clase.getIdClase(), clase.getNombre());
					jsonArray.add(optionBean);
				}
				objetoPrincipal.put("lstClases", jsonArray);
		}
				
		List<ApunteForm> lstApuntesForm=null;
		//Si se ha seleccionado alguna contabilidad cargamos los apuntes
		if(idContabilidad!=null && idContabilidad>0)
			lstApuntesForm=ServiceUtils.getIApunteService().getLstApuntesForm(idCuenta, idGrupo, idClase, fechaInicioStr, fechaFinStr, concepto,"");
		
		JSONArray jsonArray = new JSONArray();
		Map map = new HashMap();
		
		if (lstApuntesForm!=null && lstApuntesForm.size()>0){
		
			for (ApunteForm apunte:lstApuntesForm){
				ApunteBean apunteBean=new ApunteBean
					(apunte.getId(), apunte.getTipoApunte(), apunte.getFechaStr(), apunte.getConcepto()
					,apunte.getNombreGrupo(), apunte.getNombreClase(), NumeroUtil.formatearACastellano(apunte.getImporte().setScale(2,BigDecimal.ROUND_HALF_UP)), NumeroUtil.formatearACastellano(apunte.getSaldo().setScale(2,BigDecimal.ROUND_HALF_UP)));
				
				jsonArray.add(apunteBean);
			}
						
			BigDecimal ingresos=ServiceUtils.getIApunteService().getTotalIngresos(lstApuntesForm); 
			BigDecimal gastos= ServiceUtils.getIApunteService().getTotalGastos(lstApuntesForm);
			BigDecimal balance= ingresos.subtract(gastos);
			
			map.put("numApuntes", lstApuntesForm.size());
			map.put("ingresos",NumeroUtil.formatearACastellano(ingresos));
			map.put("gastos", NumeroUtil.formatearACastellano(gastos));
			map.put("balance", NumeroUtil.formatearACastellano(balance));
			
		}
		
		objetoPrincipal.put("resumen", map);	
		objetoPrincipal.put("lstApuntes", jsonArray);
		
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
