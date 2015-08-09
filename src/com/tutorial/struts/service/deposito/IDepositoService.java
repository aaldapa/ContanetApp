package com.tutorial.struts.service.deposito;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Deposito;
import com.tutorial.struts.forms.deposito.DepositoForm;

public interface IDepositoService {

	public List<Deposito> getLstDepositos(Integer idUsuario);
	public DepositoForm getDeposito(Integer id);
	public void saveDeposito(DepositoForm depositoForm);
	public void deleteDeposito(Integer id);
	public List<Deposito> getLstDepositosPorContabilidad(Integer idContabilidad);
	
}
