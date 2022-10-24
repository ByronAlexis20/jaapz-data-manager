package com.jaap.datamanager.proceso.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Cliente;

public interface IClienteDAO extends CrudRepository<Cliente, Integer> {

	@Query("Select c from Cliente c")
	public List<Cliente> buscarClientes();
	
	@Query("Select c from Cliente c where c.cedula = ?1")
	public List<Cliente> buscarPorCedula(String cedula);
	
	@Query("Select c from Cliente c where c.cedula = ?1 and c.id <> ?2")
	public List<Cliente> buscarPorCedula(String cedula, Integer id);
	
}
