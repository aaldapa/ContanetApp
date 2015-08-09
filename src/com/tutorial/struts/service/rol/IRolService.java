package com.tutorial.struts.service.rol;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.struts.forms.rol.RolForm;

public interface IRolService {

	public List<Perfil>getLstRoles();
	public RolForm getRoles(Integer id);
	public void saveRol(RolForm rolForm);
	public void deleteRol (Integer id);
	
}
