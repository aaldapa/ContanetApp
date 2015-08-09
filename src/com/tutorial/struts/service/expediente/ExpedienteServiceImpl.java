package com.tutorial.struts.service.expediente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Deposito;
import com.tutorial.struts.forms.expediente.ContabilidadBean;
import com.tutorial.struts.forms.expediente.ExpedienteForm;
import com.tutorial.struts.service.ServiceUtils;

public class ExpedienteServiceImpl implements IExpedienteService {

	/**
	 * Singleton Instance of ExpedienteServiceImpl
	 */
	private static ExpedienteServiceImpl expedienteServiceImpl = new ExpedienteServiceImpl();
	
	/**
	 * Creates Instance of {@link ExpedienteServiceImpl}
	 */
	private ExpedienteServiceImpl(){		
	}
	
	/***
	 * Gets Instance of ExpedienteServiceImpl
	 * @return ExpedienteServiceImpl Implementation
	 */
	public static ExpedienteServiceImpl getInstance(){	
		return expedienteServiceImpl;
	}
	
	@Override
	public ExpedienteForm getExpedienteFormAllCuentas(Integer idUsuario){
		ExpedienteForm expedienteForm=new ExpedienteForm();
		
		List<Contabilidad>lstContabilidades=new ArrayList<Contabilidad>();
		
		lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		
		int tamIdsCuenta=0;
		int tamIdsDeposito=0;
		
		for (Contabilidad contabilidad:lstContabilidades){
			ContabilidadBean contabilidadBean=new ContabilidadBean();
			contabilidadBean.setContabilidad(contabilidad);
			contabilidadBean.getLstCuentas().addAll(
					ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(contabilidad.getIdContabilidad()));
			contabilidadBean.getLstDepositos().addAll(
					ServiceUtils.getIDepositoService().getLstDepositosPorContabilidad(contabilidad.getIdContabilidad()));
			
			//Sumo las cuentas para inicializar el array que contiene los ids de las cuentas que se mostraran checkeados en la jsp		
			if (contabilidadBean.getLstCuentas()!=null)
				tamIdsCuenta=tamIdsCuenta+contabilidadBean.getLstCuentas().size();
			
			//Sumo los depositos para inicializar el array que contiene los ids de las depositos que se mostraran checkeados en la jsp		
			if (contabilidadBean.getLstDepositos()!=null)
				tamIdsDeposito=tamIdsDeposito+contabilidadBean.getLstDepositos().size();
				
			expedienteForm.getLstContabilidadBeans().add(contabilidadBean);	
		}
		
		String [] ids=new String[tamIdsCuenta];
		String [] idsD=new String[tamIdsDeposito];
		int cont=0;
		int element=0;
		for (ContabilidadBean contabilidadBean:expedienteForm.getLstContabilidadBeans()){
			
			for (Cuenta cuenta:contabilidadBean.getLstCuentas()){
				cuenta.setBalance(new Float(ServiceUtils.getICuentaService().getBalanceCuenta(cuenta.getIdCuenta())));
				if (ids.length>0)
					ids[cont]=cuenta.getIdCuenta().toString();
				cont++;
				contabilidadBean.setBalanceContabilidad(new BigDecimal(cuenta.getBalance()).add(contabilidadBean.getBalanceContabilidad()));
			}
			
			for (Deposito deposito:contabilidadBean.getLstDepositos()){
				
				if (idsD.length>0)
					idsD[element]=deposito.getIdDeposito().toString();
				element++;
				contabilidadBean.setBalanceContabilidad(new BigDecimal(deposito.getImporte()).add(contabilidadBean.getBalanceContabilidad()));
			}
			
			expedienteForm.setBalanceActual(expedienteForm.getBalanceActual().add(contabilidadBean.getBalanceContabilidad()));
		}
		
		//Carga de ids
		expedienteForm.setLstIdsCuentasSelect(ids);
		expedienteForm.setLstIdsDepositosSelect(idsD);

		return expedienteForm;
	}

	
	public ExpedienteForm getExpedienteForm(String []ids,String []idsDepos, Integer idUsuario){
		ExpedienteForm expedienteForm=new ExpedienteForm();
		List<Contabilidad>lstContabilidades=new ArrayList<Contabilidad>();
		
		//Si hay algo seleccionado
		lstContabilidades=ServiceUtils.getIContabilidadService().getLstContabilidades(idUsuario);
		
		int tamCuenta=0;  
		
		for (Contabilidad contabilidad:lstContabilidades){
			ContabilidadBean contabilidadBean=new ContabilidadBean();
			contabilidadBean.setContabilidad(contabilidad);
			contabilidadBean.getLstCuentas().addAll(
					ServiceUtils.getICuentaService().getLstCuentasPorContabilidad(contabilidad.getIdContabilidad()));
			contabilidadBean.getLstDepositos().addAll(
					ServiceUtils.getIDepositoService().getLstDepositosPorContabilidad(contabilidad.getIdContabilidad()));
			
			expedienteForm.getLstContabilidadBeans().add(contabilidadBean);	
		}
		
		for (ContabilidadBean contabilidadBean:expedienteForm.getLstContabilidadBeans()){
			for (Cuenta cuenta:contabilidadBean.getLstCuentas()){
				cuenta.setBalance(new Float(ServiceUtils.getICuentaService().getBalanceCuenta(cuenta.getIdCuenta())));
				for(int i=0;i<ids.length;i++){
					if (ids[i].equals(cuenta.getIdCuenta().toString())){
						expedienteForm.setBalanceActual(expedienteForm.getBalanceActual().add(new BigDecimal(cuenta.getBalance())));
						contabilidadBean.setBalanceContabilidad(new BigDecimal(cuenta.getBalance()).add(contabilidadBean.getBalanceContabilidad()));
					}
				}
			}
			for (Deposito deposito:contabilidadBean.getLstDepositos()){
				for(int i=0;i<idsDepos.length;i++){
					if (idsDepos[i].equals(deposito.getIdDeposito().toString())){
						expedienteForm.setBalanceActual(expedienteForm.getBalanceActual().add(new BigDecimal(deposito.getImporte())));
						contabilidadBean.setBalanceContabilidad(new BigDecimal(deposito.getImporte()).add(contabilidadBean.getBalanceContabilidad()));
					}
				}
			}
		}
		
		expedienteForm.setLstIdsCuentasSelect(ids);
		expedienteForm.setLstIdsDepositosSelect(idsDepos);
		return expedienteForm;
	}
}
