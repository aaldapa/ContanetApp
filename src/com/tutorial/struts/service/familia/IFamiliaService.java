package com.tutorial.struts.service.familia;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Familia;
import com.tutorial.struts.forms.familia.FamiliaForm;

public interface IFamiliaService {

	public List<Familia>getLstFamilias();
	public FamiliaForm getFamilia(Integer id);
	public void saveFamilia(FamiliaForm familiaForm);
	public void deleteFamilia (Integer id);
	
}
