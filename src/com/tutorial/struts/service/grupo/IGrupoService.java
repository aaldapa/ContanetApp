package com.tutorial.struts.service.grupo;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.grupo.GrupoForm;

public interface IGrupoService {

	public List<Grupo> getLstGrupos();
	public GrupoForm getGrupo(Integer idGrupo);
	public void saveGrupo (GrupoForm grupoForm);
	public void deleteGrupo (Integer idGrupo);
	
}
