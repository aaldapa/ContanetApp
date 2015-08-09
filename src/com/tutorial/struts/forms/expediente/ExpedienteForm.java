package com.tutorial.struts.forms.expediente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ExpedienteForm extends ActionForm implements Serializable{

	private List<ContabilidadBean>lstContabilidadBeans=new ArrayList<ContabilidadBean>();
	private BigDecimal balanceActual=new BigDecimal("0");
	private String [] lstIdsCuentasSelect;
	private String [] lstIdsDepositosSelect;

	public List<ContabilidadBean> getLstContabilidadBeans() {return lstContabilidadBeans;}
	public void setLstContabilidadBeans(List<ContabilidadBean> lstContabilidadBeans) {this.lstContabilidadBeans = lstContabilidadBeans;}
	public BigDecimal getBalanceActual() {return balanceActual;}
	public void setBalanceActual(BigDecimal balanceActual) {this.balanceActual = balanceActual;}
	
	public void setLstIdsCuentasSelect(String [] lstIdsCuentasSelect) {this.lstIdsCuentasSelect = lstIdsCuentasSelect;}
	public String [] getLstIdsCuentasSelect() {return lstIdsCuentasSelect;}
	public String [] getLstIdsDepositosSelect() {return lstIdsDepositosSelect;}
	public void setLstIdsDepositosSelect(String [] lstIdsDepositosSelect) {this.lstIdsDepositosSelect = lstIdsDepositosSelect;}
	
}
