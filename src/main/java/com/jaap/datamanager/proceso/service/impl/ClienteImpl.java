package com.jaap.datamanager.proceso.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.proceso.models.dao.IClienteDAO;
import com.jaap.datamanager.proceso.models.entity.Cliente;
import com.jaap.datamanager.proceso.service.IClienteService;
import com.jaap.datamanager.seguridad.models.dao.IUsuarioDAO;
import com.jaap.datamanager.seguridad.models.entity.Perfil;
import com.jaap.datamanager.seguridad.models.entity.Usuario;

@Service
public class ClienteImpl implements IClienteService {

	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarClientes() {
		return this.clienteDAO.buscarClientes();
	}

	@Override
	@Transactional
	public Cliente grabarCliente(Cliente cli) {
		Cliente cliente = this.clienteDAO.save(cli);
		//ademas grabar en usuario cliente, para que tenga acceso a las facturas
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		//verificar si existe usuario con esa cedula
		Usuario us = this.usuarioDAO.buscarPorUsuario(cli.getCedula());
		if( us == null ) {
			//se registra el usuario
			Usuario usu = new Usuario();
			usu.setApellidos( cli.getApellidos() );
			usu.setCargo( "S/C" );
			usu.setCedula( cli.getCedula() );
			usu.setClave( encoder.encode( cli.getCedula() ) );
			usu.setCliente(cli);
			usu.setDireccion(cli.getDireccion());
			usu.setEstado( "A" );
			usu.setNombres( cli.getNombres() );
			usu.setPerfil( new Perfil( 4 ) ); //4 e el perfil cliente
			usu.setUsuario( cli.getCedula() );
			usu.setTelefono( cli.getTelefono() );
			
			this.usuarioDAO.save( usu );
		}
		return cliente;
	}

	@Override
	@Transactional
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<LinkedHashMap<String, Object>> consultarClientes() {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.clienteDAO.consultarClientes();
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

}
