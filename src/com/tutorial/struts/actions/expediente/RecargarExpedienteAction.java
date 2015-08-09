package com.tutorial.struts.actions.expediente;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.expediente.ExpedienteForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;
import com.tutorial.struts.utils.NumeroUtil;

public class RecargarExpedienteAction extends CustomBaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
				throws Exception{
		
//--- Compruebo que el usuario esta logado. 
		
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<String>lstIds=new ArrayList<String>();
		
		for (int i=0;i>-1;i++){
			String id=request.getParameter("lstIdsCuentasSelect["+i+"]");
			
			if (id==null)
				break;
			else
				lstIds.add(id);
		}
		
		String lstIdsCuentasSelect[]=new String [lstIds.size()];
		for (int cont=0;cont<lstIds.size();cont++){
			lstIdsCuentasSelect[cont]=lstIds.get(cont);
		}
		
		
		List<String>lstIdsDepos=new ArrayList<String>();
		
		for (int i=0;i>-1;i++){
			String id=request.getParameter("lstIdsDepositosSelect["+i+"]");
			
			if (id==null)
				break;
			else
				lstIdsDepos.add(id);
		}
		
		String lstIdsDepositosSelect[]=new String [lstIdsDepos.size()];
		for (int cont=0;cont<lstIdsDepos.size();cont++){
			lstIdsDepositosSelect[cont]=lstIdsDepos.get(cont);
		}
		
		
		
		
		response.setContentType("text/json");

		
		ExpedienteForm expediente=new ExpedienteForm();
		expediente=ServiceUtils.getIExpedienteService().getExpedienteForm(lstIdsCuentasSelect, lstIdsDepositosSelect, loginBean.getUsuario().getIdUsuario());
		
		
		/*
		Map map = new HashMap();  
		map.put("balanceActual", expediente.getBalanceActual());
		map.put("numContabilidades", expediente.getLstContabilidadBeans().size());
		
		for (int index=0;index<expediente.getLstContabilidadBeans().size();index++){
			map.put( "balanceContabilidad_"+index,expediente.getLstContabilidadBeans().get(index).getBalanceContabilidad());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map); 
		*/
		
		ArrayList lista = new ArrayList();
		lista.add(NumeroUtil.formatearACastellano(expediente.getBalanceActual().setScale(2,BigDecimal.ROUND_HALF_UP)));

		for (int index=0;index<expediente.getLstContabilidadBeans().size();index++){
			String obj="0";	
			obj=NumeroUtil.formatearACastellano(expediente.getLstContabilidadBeans().get(index).getBalanceContabilidad().setScale(2,BigDecimal.ROUND_HALF_UP));
			lista.add(obj);
		}
		
		JSONArray jsonArray = JSONArray.fromObject(lista);
		
		

		PrintWriter pw = response.getWriter();
		pw.write("({\"listaObjetos\":" + jsonArray.toString() + "})");
		pw.flush();
		pw.close();
		
		return null;
	}
	
	
}
