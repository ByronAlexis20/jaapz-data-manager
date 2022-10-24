package com.jaap.datamanager.proceso.service;

import java.util.List;

import com.jaap.datamanager.proceso.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> buscarClientes();
	public Boolean validarCedula(String cedula,Integer id);
	public Cliente grabarCliente(Cliente cli);
}
