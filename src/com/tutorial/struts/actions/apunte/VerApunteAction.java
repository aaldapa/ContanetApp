package com.tutorial.struts.actions.apunte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.tutorial.struts.actions.CustomBaseAction;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.forms.apunte.ApunteListForm;
import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.LoginBean;
import com.tutorial.struts.utils.NumeroUtil;

public class VerApunteAction extends CustomBaseAction {
	/**
   	 * Este método simplemente reenvia al estado de success,
   	 * el cual debería representar la página apunte.jsp.
   	 **/
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
    						  throws Exception 
    {
		//Si no esta logado cargo mensaje error desde CustomBaseAction
		LoginBean loginBean=comprobarLogin(request);
		if (loginBean==null)
			return mapping.findForward("failure");
		
		ApunteListForm apunteListForm=new ApunteListForm();
		ApunteForm apunteForm=new ApunteForm();
		Integer id,idContabilidad=0,idCuenta=0,idGrupo=0,idClase=0;
		String ids[]=new String[0];
		String accion="";
		
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		//Controlo si la llamada a apunte.jsp viene de una recarga de pagina o de listar.jsp
		if (form instanceof ApunteListForm){
			apunteListForm=(ApunteListForm) form;
			ids=apunteListForm.getIds();
			id=apunteListForm.getId();
			idContabilidad=apunteListForm.getIdContabilidad();
			idCuenta=apunteListForm.getIdCuenta();
			idGrupo=apunteListForm.getIdGrupo();
			idClase=apunteListForm.getIdClase();
			accion=apunteListForm.getAccion();
		}
		else{
			apunteForm=(ApunteForm) form;
			id=apunteForm.getId();
			idContabilidad=apunteForm.getIdContabilidad();
			idCuenta=apunteForm.getIdCuenta();
			idGrupo=apunteForm.getIdGrupo();
			idClase=apunteForm.getIdClase();
			accion=apunteForm.getAccion();
		}
		//Si se viene del listado y se pulsa el boton nuevo del listado
		if (form instanceof ApunteListForm && accion.equalsIgnoreCase(recursos.getMessage("boton.new"))){
			apunteForm=ServiceUtils.getIApunteService().setApunteNew(apunteListForm);
		}
		//Si se ha pulsado ver, eliminar, modificar, cargamos el apunte de la base de datos
		else if (form instanceof ApunteListForm 
				&& !accion.equals(recursos.getMessage("boton.new"))
				&& !accion.equals(recursos.getMessage("boton.organizar"))
				&& id!=null){
			apunteForm=ServiceUtils.getIApunteService().geApunte(id, accion);
		}
		
		//Si es recarga cargar el importe
		else if (form instanceof ApunteForm){
			apunteForm.setImporte(NumeroUtil.formatearImporteComaAPuntoBD(apunteForm.getImporteStr()));
		}
		
		//Carga de lstContabilidades
		apunteForm.setLstContabilidades(		
			ServiceUtils.getIContabilidadService().getLstContabilidades(loginBean.getUsuario().getIdUsuario()));
				
		if (form instanceof ApunteListForm 
				&& accion.equals(recursos.getMessage("boton.organizar"))
				&& ids!=null){
			
			request.getSession().setAttribute("listadoApuntesForm", apunteListForm);
			return mapping.findForward("reorganizarApuntes");
		}
		
		apunteForm.setAccion(accion);
		
		//Inicio octubre_12
		if (form instanceof ApunteListForm)
			apunteForm.setSeleccionAnterior(apunteListForm);
		//Fin octubre_12
		
		request.setAttribute("apunteForm", apunteForm);
		return mapping.findForward("success");
    }

}
