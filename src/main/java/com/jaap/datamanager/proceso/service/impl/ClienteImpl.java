package com.jaap.datamanager.proceso.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
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

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarClientesPorEstado(String estado) {
		return this.clienteDAO.buscarClientesPorEstado(estado);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<LinkedHashMap<String, Object>> buscarClientesPlanillar(Integer idAnio, Integer idMes) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.clienteDAO.consultarClientePlanillar(idAnio, idMes);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

}
