
package com.tutorial.struts.actions.cuenta;

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
import com.tutorial.struts.actions.CustomBaseLookupDispatch;
import com.tutorial.struts.forms.cuenta.CuentaForm;
import com.tutorial.struts.forms.usuario.UsuarioForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;

public class CuentaAction extends CustomBaseAction {

	
	/*Metodo recoge los valores de los botones pulsados y los mapea con los nombres de los metodos a ejecutar
	 * 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("cuentas.listado.list", "list");
		map.put("cuentas.listado.new", "viewAdd");
		map.put("cuentas.listado.view", "view");
		map.put("cuentas.listado.update", "viewUpdate");
		map.put("cuentas.listado.delete", "viewDelete");
		map.put("cuentas.listado.insert", "insert");
		map.put("cuentas.listado.delete", "delete");
		return map;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**Programando la logica de negocio fuera del action*/
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		/*LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		List<Cuenta> lstCuentas=new ArrayList<Cuenta>();
		
		//Si el usuario tiene perfil de administrador mostramos todas las contabilidades
		if (loginBean.getUsuario().getPerfil().getIdPerfil().compareTo(new Integer(1))==0) 
			lstCuentas=ServiceUtils.getICuentaService().getLstCuentas();
		
		//Si no tan solo las contabilidades a las que tiene permiso
		//else
			//lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario());
		
		request.setAttribute("listado-cuentas",lstCuentas);
		return mapping.findForward("success");
	}*/
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response){	
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		CuentaForm formulario=(CuentaForm) form;
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		if (formulario.getAccion()!=null){
			//Si la accion pulsada ha sido nuevo o modificar
			if (formulario.getAccion().equals(recursos.getMessage("cuentas.listado.new"))
					||formulario.getAccion().equals(recursos.getMessage("cuentas.listado.update")))
				//Llamada al metodo que guarda la contabilidad
				ServiceUtils.getICuentaService().saveCuenta(formulario, loginBean.getUsuario().getIdUsuario());
			
			//Si la accion pulsada ha sido eliminar
			if (formulario.getAccion().equals(recursos.getMessage("cuentas.listado.delete"))){
				//Llamamos al metodo que elimina el registro de la base de datos			
				ServiceUtils.getICuentaService().deleteCuenta(formulario.getId());
			}
		}
		
		return mapping.findForward("success");
    }
}
