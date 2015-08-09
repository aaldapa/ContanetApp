package com.tutorial.struts.service.login;

import com.tutorial.struts.service.Service;

import com.tutorial.domain.entidades.baseDatos.Usuario;

public interface ILoginService extends Service {

	public Usuario authenticate(String email, String password);
}
