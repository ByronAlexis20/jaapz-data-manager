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
	
	@Query("Select c from Cliente c where c.estado = ?1")
	public List<Cliente> buscarClientesPorEstado(String estado);
	
	@Query(value = "select public.fun_buscar_cliente_planillar(?1, ?2);", nativeQuery = true)
	public String consultarClientePlanillar(Integer idanio, Integer idmes);
	
	@Query(value = "select public.fun_buscar_clientes();", nativeQuery = true)
	public String consultarClientes();
}
