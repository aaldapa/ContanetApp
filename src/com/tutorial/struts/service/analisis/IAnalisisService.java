package com.tutorial.struts.service.analisis;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.VistaApunteClases;
import com.tutorial.domain.entidades.baseDatos.VistaApunteGrupos;
import com.tutorial.domain.entidades.baseDatos.VistaApunteMes;

public interface IAnalisisService {

	public List<VistaApunteMes> getVistaApuntesMes (Integer anio, Integer id_cuenta);
	public List<VistaApunteGrupos> getVistaApuntesGrupo (Integer anio, Integer id_cuenta);
	public List<VistaApunteClases> getVistaApuntesClases (Integer anio, Integer id_cuenta, Integer id_grupo);
	
}
