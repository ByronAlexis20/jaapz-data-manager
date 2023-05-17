package com.jaap.datamanager.seguridad.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.seguridad.models.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Integer> {

	public Optional<Usuario> findByUsuario(String usuario);

	@Query("Select u from Usuario u where u.estado = 'A' and u.usuario = ?1")
	public Usuario buscarPorUsuario(String usuario);
	
	@Query("Select u from Usuario u where u.estado = 'A' and u.id = ?1")
	public Usuario buscarPorIdUsuario(Integer idUsuario);
	
	@Query("Select u from Usuario u where u.estado = 'A' order by u.estado desc")
	public List<Usuario> buscarUsuariosActivos();
	
	@Query("Select u from Usuario u where u.cliente.id = ?1 and u.estado = 'A'")
	public Usuario buscarPorIdCliente(Long idUsuario);
	
}
