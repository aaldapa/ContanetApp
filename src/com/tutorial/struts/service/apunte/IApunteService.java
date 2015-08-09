package com.tutorial.struts.service.apunte;

import java.math.BigDecimal;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.tutorial.domain.entidades.baseDatos.Apunte;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.forms.apunte.ApunteListForm;

public interface IApunteService {
	
	//Carga de apuntes
	public Boolean guardarFichero(String path, FormFile fichero);
	public Integer cargarMovimientos(String path, Integer idCuenta);
	public Integer cargarMovimientosExcel(String fileName, Integer idCuenta, Integer idBanco);
	//Consulta de apuntes
	/*public List<Apunte> getLstApuntes (Integer idCuenta, Integer idGrupo, 
			Integer idClase, String fechainicioStr, String fechaFinStr);
	*/
	public List<Apunte> getLstApuntes (Integer idCuenta, Integer idGrupo, 
			Integer idClase, String fechainicioStr, String fechaFinStr, String concepto, String tipo);
	
	//carga de apunteForm nuevo con los combos seleccionados del listado
	public ApunteForm setApunteNew(ApunteListForm apunteListForm);
	//carga de apunteForm seleccionado desde el listado
	public ApunteForm geApunte(Integer id, String accion);
	public void saveApunte(ApunteForm apunteForm);
	public void deleteApunte(Integer id);
	/*
	public List<ApunteForm>getLstApuntesForm(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr);
	*/
	public List<ApunteForm>getLstApuntesForm(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr, String concepto, String tipo);
	
	//Guarda todos los apuntes con la misma contabilidad,cuenta, grupo, clase..
	public void saveReorganizarApuntes(ApunteListForm apunteListForm);
	
	//Devuelve el total de ingresos de la lista de apuntes que se muestran en la jsp
	public BigDecimal getTotalIngresos(List<ApunteForm> lstApuntesForm);
	
	//Devuelve el total de gastos de la lista de apuntes que se muestran en la jsp
	public BigDecimal getTotalGastos(List<ApunteForm> lstApuntesForm);
	
	//Devuelve una lista de apuntes agrupados por concepto
	public List<Apunte> getLstApuntesAgrupadosConceptos(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr, String concepto, String tipo);
	
}
