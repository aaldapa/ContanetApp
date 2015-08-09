package com.tutorial.struts.service;

import com.tutorial.struts.service.analisis.AnalisisServiceImpl;
import com.tutorial.struts.service.analisis.IAnalisisService;
import com.tutorial.struts.service.apunte.ApunteServiceImpl;
import com.tutorial.struts.service.apunte.IApunteService;
import com.tutorial.struts.service.apuntepordefecto.ApuntePorDefectoImpl;
import com.tutorial.struts.service.apuntepordefecto.IApuntePorDefectoService;
import com.tutorial.struts.service.clase.ClaseServiceImpl;
import com.tutorial.struts.service.clase.IClaseService;
import com.tutorial.struts.service.contabilidad.ContabilidadServiceImpl;
import com.tutorial.struts.service.contabilidad.IContabilidadService;
import com.tutorial.struts.service.cuenta.CuentaServiceImpl;
import com.tutorial.struts.service.cuenta.ICuentaService;
import com.tutorial.struts.service.deposito.DepositoServiceImpl;
import com.tutorial.struts.service.deposito.IDepositoService;
import com.tutorial.struts.service.email.EmailServiceImpl;
import com.tutorial.struts.service.email.IEmailService;
import com.tutorial.struts.service.expediente.ExpedienteServiceImpl;
import com.tutorial.struts.service.expediente.IExpedienteService;
import com.tutorial.struts.service.familia.FamiliaServiceImpl;
import com.tutorial.struts.service.familia.IFamiliaService;
import com.tutorial.struts.service.grupo.GrupoServiceImpl;
import com.tutorial.struts.service.grupo.IGrupoService;
import com.tutorial.struts.service.login.ILoginService;
import com.tutorial.struts.service.login.LoginServiceImpl;
import com.tutorial.struts.service.report.IReportService;
import com.tutorial.struts.service.report.ReportServiceImpl;
import com.tutorial.struts.service.rol.IRolService;
import com.tutorial.struts.service.rol.RolServiceImpl;
import com.tutorial.struts.service.usuario.IUsuarioService;
import com.tutorial.struts.service.usuario.UsuarioServiceImpl;

public class ServiceUtils {

	/**
	 * Gets Instance of ILoginService
	 * @return ILoginService Instance
	 */	
	public static ILoginService getILoginService(){
		return LoginServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IFamiliaService
	 * @return IFamiliaService Instance
	 */	
	public static IFamiliaService getIFamiliaService(){
		return FamiliaServiceImpl.getInstance();
	}
	
	
	/**
	 * Gets Instance of IContabilidadService
	 * @return IContabilidadService Instance
	 */	
	public static IContabilidadService getIContabilidadService(){
		return ContabilidadServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IUsuarioService
	 * @return IUsuarioService Instance
	 */	
	public static IUsuarioService getIUsuarioService(){
		return UsuarioServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IRolService
	 * @return IRolService Instance
	 */	
	public static IRolService getIRolService(){
		return RolServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IGrupoService
	 * @return IGrupoService Instance
	 */	
	public static IGrupoService getIGrupoService(){
		return GrupoServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IClaseService
	 * @return IClaseService Instance
	 */	
	public static IClaseService  getIClaseService (){
		return ClaseServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of ICuentaService
	 * @return ICuentaService Instance
	 */	
	public static ICuentaService  getICuentaService (){
		return CuentaServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IEmailService
	 * @return IEmailService Instance
	 */	
	public static IEmailService  getIEmailService (){
		return EmailServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IApunteService
	 * @return IApunteService Instance
	 */	
	public static IApunteService  getIApunteService (){
		return ApunteServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IExpedienteService
	 * @return IExpedienteService Instance
	 */	
	public static IExpedienteService  getIExpedienteService (){
		return ExpedienteServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IReportService
	 * @return IReportService Instance
	 */	
	public static IReportService  getIReportService (){
		return ReportServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IApuntePorDefectoService
	 * @return IApuntePorDefectoService Instance
	 */	
	public static IApuntePorDefectoService  getIApuntePorDefectoService(){
		return ApuntePorDefectoImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IAnalisisService
	 * @return IAnalisisService Instance
	 */	
	public static IAnalisisService  getIAnalisisService(){
		return AnalisisServiceImpl.getInstance();
	}
	
	/**
	 * Gets Instance of IDepositoService
	 * @return IDepositoService Instance
	 */	
	public static IDepositoService  getIDepositoService(){
		return DepositoServiceImpl.getInstance();
	}
	
}
