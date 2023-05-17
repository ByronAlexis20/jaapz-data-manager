package com.jaap.datamanager.seguridad.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IClienteDAO;
import com.jaap.datamanager.proceso.models.entity.Cliente;
import com.jaap.datamanager.seguridad.models.dao.IUsuarioDAO;
import com.jaap.datamanager.seguridad.models.entity.Perfil;
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
			PasswordEncoder encoder = new BCryptPasswordEncoder();
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
					//verificar si existe usuario con esa cedula
					Usuario us = this.usuarioDAO.buscarPorUsuario(cl.getCedula());
					if( us == null ) {
						//se registra el usuario
						Usuario usu = new Usuario();
						usu.setApellidos( cl.getApellidos() );
						usu.setCargo( "S/C" );
						usu.setCedula( cl.getCedula() );
						usu.setClave( encoder.encode( cl.getCedula() ) );
						usu.setCliente(cl);
						usu.setDireccion(cl.getDireccion());
						usu.setEstado( "A" );
						usu.setNombres( cl.getNombres() );
						usu.setPerfil( new Perfil( 4 ) ); //4 e el perfil cliente
						usu.setUsuario( cl.getCedula() );
						usu.setTelefono( cl.getTelefono() );
						
						this.usuarioDAO.save( usu );
					}
				}
			}
			response.put("estado", "ok");
			response.put("mensaje", "Usuarios clientes creados correctamente");
		}catch(Exception ex) {
			response.put("estado", "error");
			response.put("mensaje", ex.getMessage());
		}
		return response;
	}
}