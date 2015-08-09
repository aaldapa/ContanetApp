package com.tutorial.struts.service.cuenta;

import java.util.List;

import com.tutorial.domain.entidades.baseDatos.Contabilidad;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.domain.entidades.baseDatos.TmaeBanco;
import com.tutorial.struts.forms.cuenta.CuentaForm;


public interface ICuentaService {

	public List<Cuenta> getLstCuentas(Integer idUsuario);
	public CuentaForm getCuenta(Integer id);
	public void saveCuenta(CuentaForm cuentaForm, Integer idUsuario);
	public void deleteCuenta (Integer id);
	public List<Cuenta> getLstCuentasPorContabilidad(Integer idContabilidad);
	public String getBalanceCuenta(Integer idCuenta);
	public Cuenta cargarCuenta(Integer idCuenta);
	public Contabilidad cargarContabilidad(Integer idContabilidad);
	public List<TmaeBanco> getLstBancos();
}
