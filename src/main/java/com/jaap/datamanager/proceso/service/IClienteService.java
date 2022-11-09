package com.jaap.datamanager.proceso.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.jaap.datamanager.proceso.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> buscarClientes();
	public Boolean validarCedula(String cedula,Integer id);
	public Cliente grabarCliente(Cliente cli);
	public List<Cliente> buscarClientesPorEstado(String estado);
	public List<LinkedHashMap<String, Object>> buscarClientesPlanillar(Integer idAnio, Integer idMes);
	
}
