package com.tutorial.struts.service.clase;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Grupo;
import com.tutorial.struts.forms.clase.ClaseForm;

public interface IClaseService {

	public List<Clase>getLstClases(Integer idGrupo);
	public ClaseForm getClase(Integer id);
	public void saveClase(ClaseForm claseForm);
	public void deleteClase (Integer id);
	public Grupo cargarGrupo(Integer id);
}
