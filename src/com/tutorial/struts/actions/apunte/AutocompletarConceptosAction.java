package com.tutorial.struts.actions.apunte;

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

import com.tutorial.domain.entidades.baseDatos.Apunte;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.forms.apunte.ConceptoApunteBean;
import com.tutorial.struts.service.ServiceUtils;

public class AutocompletarConceptosAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
				throws Exception{
		
		String fechaInicioStr=request.getParameter("fechaInicio");
		String fechaFinStr=request.getParameter("fechaFin");;
		Integer idContabilidad=new Integer(request.getParameter("idContabilidad"));
		Integer idGrupo=new Integer(request.getParameter("idGrupo"));
		Integer idClase=new Integer(request.getParameter("idClase"));;
		Integer idCuenta=new Integer(request.getParameter("idCuenta"));
		String concepto=request.getParameter("term");
		
		response.setContentType("text/json");
		JSONObject objetoPrincipal = new JSONObject();
		StringWriter out = new StringWriter();
		
		//List<ApunteForm> lstApuntesForm=null;
		List<Apunte> lstApuntes=null;
		//Si se ha seleccionado alguna contabilidad cargamos los apuntes
		if(idContabilidad!=null && idContabilidad>0){
			//lstApuntesForm=ServiceUtils.getIApunteService().getLstApuntesForm(idCuenta, idGrupo, idClase, fechaInicioStr, fechaFinStr, concepto,"");
			lstApuntes=ServiceUtils.getIApunteService().getLstApuntesAgrupadosConceptos(idCuenta, idGrupo, idClase, fechaInicioStr, fechaFinStr, concepto,"");
		}
		
		/*JSONArray jsonArray = new JSONArray();
		
		if (lstApuntesForm!=null && lstApuntesForm.size()>0){
		
			for (ApunteForm apunte:lstApuntesForm){
				ConceptoApunteBean conceptoApunteBean=new ConceptoApunteBean(apunte.getId(),apunte.getConcepto());
				jsonArray.add(conceptoApunteBean);
			}
		}
		*/
		
		JSONArray jsonArray = new JSONArray();
		
		if (lstApuntes!=null && lstApuntes.size()>0){
		
			for (Apunte apunte:lstApuntes){
				ConceptoApunteBean conceptoApunteBean=new ConceptoApunteBean(apunte.getIdApunte(),apunte.getConcepto());
				jsonArray.add(conceptoApunteBean);
			}
		}
		
		objetoPrincipal.put("lstConceptos", jsonArray);
		
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
