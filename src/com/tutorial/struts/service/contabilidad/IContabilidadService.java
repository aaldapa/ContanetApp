package com.tutorial.struts.service.contabilidad;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.struts.forms.contabilidad.ContabilidadForm;

public interface IContabilidadService {

	@SuppressWarnings("rawtypes")
	public List getLstContabilidades(Integer idUsuario);
	public List<Contabilidad> getLstContabilidadesAll();
	public ContabilidadForm getContabilidad(Integer id, Integer idUsuario);
	public Contabilidad getContabilidadPrederterminada(Integer idUsuario);
	public void saveContabilidad(ContabilidadForm contabilidadForm,Integer idUsuario);
	public void deleteContabilidad (Integer id,Integer idUsuario);
	public List<Contabilidad> getLstContabilidadesSqlIn(List<String>lstIdsContabilidades, Integer idUsuario);
}
