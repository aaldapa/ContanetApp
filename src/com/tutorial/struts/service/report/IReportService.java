package com.tutorial.struts.service.report;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.apunte.ApunteFormDatasource;

public interface IReportService {

	public void abrirPDFDataSource(HttpServletResponse response, String fileName, Map parameters, ApunteFormDatasource dataSource) throws IOException;
	public void abrirXLSDataSource(HttpServletResponse response, String fileName, Map parameters, ApunteFormDatasource dataSource);
	public Contabilidad getContabilidad(Integer idContabilidad);
	public Cuenta getCuenta(Integer idCuenta);
	public Grupo getGrupo(Integer idGrupo);
	public Clase getClase(Integer idClase);
}
