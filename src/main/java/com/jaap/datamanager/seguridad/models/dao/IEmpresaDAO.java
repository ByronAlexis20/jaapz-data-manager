package com.jaap.datamanager.seguridad.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.seguridad.models.entity.Empresa;

public interface IEmpresaDAO extends CrudRepository<Empresa, Integer> {

	@Query("Select e from Empresa e where e.estado = 'A'")
	public Empresa buscarEmpresa();
	
}
