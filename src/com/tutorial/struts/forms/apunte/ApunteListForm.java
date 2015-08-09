package com.tutorial.struts.forms.apunte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.BaseForm;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;

public class ApunteListForm extends BaseForm implements Serializable{

	private String []ids=null;
	private Integer idUsuario;
	
	private Integer idContabilidad;
	private Integer idCuenta;
	private Integer idGrupo;
	private Integer idClase;
	
	private List<Cuenta> lstCuentas;
	private List<Contabilidad>lstContabilidades;
	private List<Grupo>lstGrupos;
	private List<Clase>lstClases;
	private String fechaFinStr=FechaUtil.getFechaActual().toString();
	private String fechaInicioStr=FechaUtil.formatear(
			FechaUtil.sumarPeriodo(FechaUtil.getDate(), -720, 0, 0, 0),
			new StringBuffer(), new FieldPosition(1)).toString();
	
	private String concepto;

	private BigDecimal totalIngresos=new BigDecimal("0");
	private BigDecimal totalGastos=new BigDecimal ("0");
	private BigDecimal totalBalance=new BigDecimal("0");
	
	private List<ApunteForm> lstApuntesForm = new ArrayList<ApunteForm>();
	private List<ApunteForm> lstApuntesFormReorganizar = new ArrayList<ApunteForm>();
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		
		ActionErrors errors=new ActionErrors();
		MessageResources recursos=(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
		
		//Si el boton pulsado es delete o update o copy
		if (recursos.getMessage("boton.delete").equals(getAccion())
				|| recursos.getMessage("boton.update").equals(getAccion())
				|| recursos.getMessage("boton.copy").equals(getAccion())
				)
		{
			//Si no se seleccionan apuntes
			if (ids==null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.apuntes.listado.no.seleccion"));
			}
			//Si se seleccionan multiples apuntes
			else if (ids.length>1) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.apuntes.listado.seleccion.multiple"));
			}
			else{
				//Guardamos el id seleccionado de la lista de Strings en la propiedad id
				for (String idArray:ids){
					setId(new Integer(idArray));
					break;
				}
			}
		}
		//Si el boton pulsado ha sido ver
		if (getAccion()==null)
			setAccion(recursos.getMessage("clases.listado.view"));
		
		//Si el boton pulsado es reorganizar
		if (this.getAccion().equalsIgnoreCase(recursos.getMessage("boton.organizar"))){
			if (ids==null)
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.apuntes.listado.no.seleccion"));
			
			else{
				if (idContabilidad==null || idContabilidad<1)
					errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.importar.contabilidades")));
				
				else{
					if (idCuenta==null || idCuenta<1)
						errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("global.required",recursos.getMessage("apunte.importar.cuentas")));
					
				}
			}
		}

		return errors;
	}
	
	
	public Integer getIdContabilidad() {return idContabilidad;}
	public void setIdContabilidad(Integer idContabilidad) {this.idContabilidad = idContabilidad;}
	public Integer getIdCuenta() {return idCuenta;}
	public void setIdCuenta(Integer idCuenta) {this.idCuenta = idCuenta;}
	public Integer getIdGrupo() {return idGrupo;}
	public void setIdGrupo(Integer idGrupo) {this.idGrupo = idGrupo;}
	public Integer getIdClase() {return idClase;}
	public void setIdClase(Integer idClase) {this.idClase = idClase;}
	public List<Cuenta> getLstCuentas() {
		if (idContabilidad!=null && idContabilidad>0)
			//Carga de lstCuentas
			lstCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
		else
			lstCuentas= new ArrayList<Cuenta>();
	return lstCuentas;}
	public void setLstCuentas(List<Cuenta> lstCuentas) {this.lstCuentas = lstCuentas;}
	public List<Contabilidad> getLstContabilidades() {
		if(idUsuario!=null)
			lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		return lstContabilidades;}
	public void setLstContabilidades(List<Contabilidad> lstContabilidades) {this.lstContabilidades = lstContabilidades;}
	
	public void setLstGrupos(List<Grupo> lstGrupos) {this.lstGrupos = lstGrupos;}
	
	public void setLstClases(List<Clase> lstClases) {this.lstClases = lstClases;}
	public String getFechaInicioStr() {return fechaInicioStr;}
	public void setFechaInicioStr(String fechaInicioStr) {this.fechaInicioStr = fechaInicioStr;}
	public String getFechaFinStr() {return fechaFinStr;}
	public void setConcepto(String concepto) {this.concepto = concepto;}
	public String getConcepto() {return concepto;}
	public void setFechaFinStr(String fechaFinStr) {this.fechaFinStr = fechaFinStr;}	
	
	public List<Grupo> getLstGrupos() {
		lstGrupos=ServiceUtils.getIGrupoService().getLstGrupos();
		return lstGrupos;
	}
	public List<Clase> getLstClases() {	
		
		if (idGrupo!=null && idGrupo>0)
			lstClases=ServiceUtils.getIClaseService().getLstClases(idGrupo);
		else
			lstClases=new ArrayList<Clase>();
		return lstClases;
	}
	
	public void setLstApuntesForm(List<ApunteForm> lstApuntesForm) {this.lstApuntesForm = lstApuntesForm;}
	public List<ApunteForm> getLstApuntesForm() {
		
		if (idContabilidad!=null && idCuenta!=null && idContabilidad>0){
			//Si la cuenta seleccionada esta dentro de la lista de cuentas que pertenecen a la contabilidad
			if(this.isCuentaDeContabilidad(idContabilidad))
				lstApuntesForm=ServiceUtils.getIApunteService().getLstApuntesForm(idCuenta, idGrupo, idClase, fechaInicioStr, fechaFinStr, concepto,"");
		}
		return lstApuntesForm;
	}


	public String[] getIds() {return ids;}
	public void setIds(String[] ids) {this.ids = ids;}

	private Boolean isCuentaDeContabilidad(Integer idContabilidad){
		Boolean isCuentaDeContabilidad=false;
		List<Cuenta>listadoCuentas= ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(idContabilidad);
		for(Cuenta cuenta:listadoCuentas){
			if (idCuenta.compareTo(cuenta.getIdCuenta())==0){
				isCuentaDeContabilidad=true;
				break;
			}
		}
		return isCuentaDeContabilidad;
	}


	public Integer getIdUsuario() {	return idUsuario;}
	public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}

	public void setLstApuntesFormReorganizar(
			List<ApunteForm> lstApuntesFormReorganizar) {
		this.lstApuntesFormReorganizar = lstApuntesFormReorganizar;
	}


	public List<ApunteForm> getLstApuntesFormReorganizar() {
		for (int i=0;i<ids.length;i++){
			lstApuntesFormReorganizar.add(ServiceUtils.getIApunteService().geApunte(new Integer(ids[i]), super.getAccion()));
		}
		return lstApuntesFormReorganizar;
	}


	public BigDecimal getTotalIngresos() {
		if (!this.getLstApuntesForm().isEmpty())
			return ServiceUtils.getIApunteService().getTotalIngresos(this.getLstApuntesForm());
		return totalIngresos;
	}
	public void setTotalIngresos(BigDecimal totalIngresos) {this.totalIngresos = totalIngresos;}
	public BigDecimal getTotalGastos() {
		if (!this.getLstApuntesForm().isEmpty())
			return ServiceUtils.getIApunteService().getTotalGastos(this.getLstApuntesForm());
		return totalGastos;}
	public void setTotalGastos(BigDecimal totalGastos) {this.totalGastos = totalGastos;}
	public BigDecimal getTotalBalance() {return getTotalIngresos().subtract(getTotalGastos());}
	public void setTotalBalance(BigDecimal totalBalance) {this.totalBalance = totalBalance;}

}
