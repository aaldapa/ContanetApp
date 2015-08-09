package com.tutorial.struts.service.expediente;

import com.tutorial.struts.forms.expediente.ExpedienteForm;

public interface IExpedienteService {
	public ExpedienteForm getExpedienteFormAllCuentas(Integer idUsuario);
	public ExpedienteForm getExpedienteForm(String []ids,String []idsDepos, Integer idUsuario);

}
