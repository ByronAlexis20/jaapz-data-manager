package com.jaap.datamanager.proceso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IClienteDAO;
import com.jaap.datamanager.proceso.models.entity.Cliente;
import com.jaap.datamanager.proceso.service.IClienteService;

@Service
public class ClienteImpl implements IClienteService {

	@Autowired
	private IClienteDAO clienteDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarClientes() {
		return this.clienteDAO.buscarClientes();
	}

	@Override
	@Transactional
	public Cliente grabarCliente(Cliente cli) {
		return this.clienteDAO.save(cli);
	}

	@Override
	public Boolean validarCedula(String cedula, Integer id) {
		Boolean bandera = false;
		if(id == null) {
			List<Cliente> lista = this.clienteDAO.buscarPorCedula(cedula);
			if(lista.size() > 0) {
				bandera = true;
			}
		}else {
			List<Cliente> lista = this.clienteDAO.buscarPorCedula(cedula, id);
			if(lista.size() > 0) {
				bandera = true;
			}
		}
		return bandera;
	}

}
