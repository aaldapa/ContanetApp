package com.tutorial.struts.forms.apunte;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class ApunteBean implements Serializable, JSONStreamAware {

	private Integer id;
	private String tipoApunte;
	private String fechaStr;
	private String concepto;
	private String nombreGrupo;
	private String nombreClase;
	private String importeStr, saldoStr;

	public ApunteBean() {
		super();
	}
	
	public ApunteBean(Integer id, String tipoApunte, String fechaStr,
			String concepto, String nombreGrupo,
			String nombreClase, String importeStr, String saldoStr) {
		super();
		this.id = id;
		this.tipoApunte = tipoApunte;
		this.fechaStr = fechaStr;
		this.concepto = concepto;
		this.nombreGrupo = nombreGrupo;
		this.nombreClase = nombreClase;
		this.importeStr = importeStr;
		this.saldoStr = saldoStr;
	}


	@Override
	public void writeJSONString(Writer out) throws IOException {
		  LinkedHashMap obj = new LinkedHashMap();
		  obj.put("id", new Integer(id));
		  obj.put("tipo", tipoApunte);
		  obj.put("fechaStr", fechaStr);
		  obj.put("concepto", concepto);
		  obj.put("nombreGrupo", nombreGrupo);
		  obj.put("nombreClase", nombreClase);
		  obj.put("importe", importeStr);
		  obj.put("saldo", saldoStr);
          JSONValue.writeJSONString(obj, out);
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public String getTipoApunte() {return tipoApunte;}
	public void setTipoApunte(String tipoApunte) {this.tipoApunte = tipoApunte;}
	public String getFechaStr() {return fechaStr;}
	public void setFechaStr(String fechaStr) {this.fechaStr = fechaStr;}
	public String getConcepto() {return concepto;}
	public void setConcepto(String concepto) {this.concepto = concepto;}
	public String getNombreGrupo() {return nombreGrupo;}
	public void setNombreGrupo(String nombreGrupo) {this.nombreGrupo = nombreGrupo;}
	public String getNombreClase() {return nombreClase;}
	public void setNombreClase(String nombreClase) {this.nombreClase = nombreClase;}
	public String getImporteStr() {return importeStr;}
	public void setImporteStr(String importeStr) {this.importeStr = importeStr;}
	public String getSaldoStr() {return saldoStr;}
	public void setSaldoStr(String saldoStr) {this.saldoStr = saldoStr;}
	
}
