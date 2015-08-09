package pruebas;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class PruebaJson extends CustomBaseAction 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
				 				throws Exception 
	{
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		Integer idContabilidad=new Integer(request.getParameter("idContabilidad"));
		response.setContentType("text/json");
		
		StringWriter out = new StringWriter();
		JSONObject objetoPrincipal = new JSONObject();
		JSONArray cuentasArray = new JSONArray();
		
		/*
		StringBuffer strJQuery = new StringBuffer();
		
		strJQuery.append("[ ");
		strJQuery.append("{");
		strJQuery.append("\"");
		strJQuery.append("codpostal");
		strJQuery.append("\"");
		strJQuery.append(":");
		strJQuery.append("\"");			
		strJQuery.append(codpostal);
		strJQuery.append("\"");
		strJQuery.append("}");
		strJQuery.append("]");
			

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-json;charset=UTF-8");

		PrintWriter pw = response.getWriter();
		pw.write(strJQuery.toString());
		pw.flush();
		pw.close();
		
		return null;
		*/

		
		
		
		
		List<Cuenta>lstCuentas=ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
		for (Cuenta cuenta:lstCuentas){
			CuentaBean cuentaBean=new CuentaBean(cuenta.getIdCuenta(), cuenta.getNotas());
			cuentasArray.add(cuentaBean);
		}
		
		objetoPrincipal.put("lstCuentas", cuentasArray);
		objetoPrincipal.writeJSONString(out);
		
		System.out.println(out.toString());

		PrintWriter pw = response.getWriter();
		pw.write(out.toString());
		pw.flush();
		pw.close();
		
		return null;
	}
}




