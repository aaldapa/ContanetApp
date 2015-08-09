package com.tutorial.struts.forms.analisis;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import com.tutorial.struts.utils.NumeroUtil;

public class VistaApunteMesesBean implements Serializable/*, JSONStreamAware*/ {

	private Integer idVista;
	private BigDecimal ingresos, gastos, diferencias;
	private Integer mesNumero,anio;
	private String mes;
	
	public VistaApunteMesesBean(Integer idVista, BigDecimal ingresos,
			BigDecimal gastos, BigDecimal diferencias, Integer mesNumero,
			Integer anio, String mes) {
		super();
		this.idVista = idVista;
		this.ingresos = ingresos;
		this.gastos = gastos;
		this.diferencias = diferencias;
		this.mesNumero = mesNumero;
		this.anio = anio;
		this.mes = mes;
	}
	
	public VistaApunteMesesBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getIdVista() {return idVista;}
	public void setIdVista(Integer idVista) {this.idVista = idVista;}
	
	public BigDecimal getIngresos() {return ingresos;}
	public void setIngresos(BigDecimal ingresos) {this.ingresos = ingresos;}
	public BigDecimal getGastos() {return gastos;}
	public void setGastos(BigDecimal gastos) {this.gastos = gastos;}
	public BigDecimal getDiferencias() {return diferencias;}
	public void setDiferencias(BigDecimal diferencias) {this.diferencias = diferencias;}
	public Integer getMesNumero() {	return mesNumero;}
	public void setMesNumero(Integer mesNumero) {this.mesNumero = mesNumero;}
	public Integer getAnio() {return anio;}
	public void setAnio(Integer anio) {this.anio = anio;}
	public String getMes() {return mes;}
	public void setMes(String mes) {this.mes = mes;}
	
	/*@Override
	public void writeJSONString(Writer out) throws IOException {
		LinkedHashMap obj = new LinkedHashMap();
		  obj.put("id", new Integer(idVista));
		  obj.put("mesNumero", new Integer(mesNumero));
		  obj.put("anio", new Integer(anio));
		  obj.put("mes", mes.substring(0, 2).concat(anio.toString()));
		  obj.put("ingresos", NumeroUtil.formatearACastellano(ingresos));
		  obj.put("gastos", NumeroUtil.formatearACastellano(gastos));
		  obj.put("diferencia", NumeroUtil.formatearACastellano(ingresos));
        JSONValue.writeJSONString(obj, out);
	}
*/
}
