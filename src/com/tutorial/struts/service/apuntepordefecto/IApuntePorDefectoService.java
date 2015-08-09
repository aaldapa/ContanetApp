package com.tutorial.struts.service.apuntepordefecto;

import com.tutorial.struts.forms.apuntepordefecto.ApuntePorDefectoForm;


public interface IApuntePorDefectoService {

	public ApuntePorDefectoForm getApuntePorDefecto(Integer idUsuario);
	public void saveApuntePorDefecto(ApuntePorDefectoForm apuntePorDefectoForm);
	
}
