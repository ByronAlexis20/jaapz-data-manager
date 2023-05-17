package com.jaap.datamanager.seguridad.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IClienteDAO;
import com.jaap.datamanager.proceso.models.entity.Cliente;
import com.jaap.datamanager.seguridad.models.dao.IUsuarioDAO;
import com.jaap.datamanager.seguridad.models.entity.Usuario;
import com.jaap.datamanager.seguridad.service.IUsuarioService;

@Service
public class UsuarioImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorUsuario(String usuario) {
		return this.usuarioDAO.buscarPorUsuario(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorIdUsuario(Integer idUsuario) {
		return this.usuarioDAO.buscarPorIdUsuario(idUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscarUsuariosActivos() {
		return this.usuarioDAO.buscarUsuariosActivos();
	}

	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) {
		return this.usuarioDAO.save(usuario);
	}
	
	@Override
	@Transactional
	public Map<String, Object> crearUsuarioCliente(){
		Map<String, Object> response = new HashMap<>();
		try {
			//buscar clientes activos
			List<Cliente> listaClientes = this.clienteDAO.buscarClientesPorEstado("A");
			if( listaClientes.size() == 0 ) {
				response.put("estado", "error");
				response.put("mensaje", "No hay clientes activos");
				return response;
			}
			for( Cliente cl : listaClientes ) {
				//buscar si el cliente esta registrado como usuario
				Usuario usuario = this.usuarioDAO.buscarPorIdCliente(cl.getId());
				if( usuario == null ) {//no existe cliente registrado como usuario
					//se registra el usuario
					Usuario us = new Usuario();
					us.setApellidos( cl.getApellidos() );
					us.setCargo( "S/C" );
					us.setCedula( cl.getCedula() );
					
				}
			}
		}catch(Exception ex) {
			response.put("estado", "error");
			response.put("mensaje", ex.getMessage());
		}
		return response;
	}
}