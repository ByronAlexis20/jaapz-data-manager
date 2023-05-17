package com.jaap.datamanager.seguridad.service;

import java.util.List;
import java.util.Map;

import com.jaap.datamanager.seguridad.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario buscarPorUsuario(String usuario);
	
	public Usuario buscarPorIdUsuario(Integer idUsuario);
	
	public List<Usuario> buscarUsuariosActivos();
	
	public Usuario guardar(Usuario usuario);
	
	public Map<String, Object> crearUsuarioCliente();
}
