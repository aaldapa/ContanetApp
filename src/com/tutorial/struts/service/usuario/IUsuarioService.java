package com.tutorial.struts.service.usuario;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Perfil;
import com.tutorial.domain.entidades.baseDatos.Usuario;
import com.tutorial.struts.forms.usuario.UsuarioForm;

public interface IUsuarioService {

	public List<Usuario>getLstUsuarios();
	public List<Perfil>getLstRoles();
	public UsuarioForm getUsuario(Integer id);
	public void saveUsuario(UsuarioForm usuarioForm);
	public void deleteUsuario (Integer id);
	public List<Contabilidad> getLstContabilidadesAccesoUsuario(Integer idUsuario);
	public List<Usuario> getLstUsuariosAccesoContabilidad (Integer idContabilidad);
	public List<Usuario> getFamiliares(Integer idFamilia); 

}
