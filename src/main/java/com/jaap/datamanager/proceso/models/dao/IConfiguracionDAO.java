package com.jaap.datamanager.proceso.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Configuracion;

public interface IConfiguracionDAO extends CrudRepository<Configuracion, Integer> {

	@Query("Select c from Configuracion c where c.estado = 'A'")
	public Configuracion buscarConfiguracion();
	
}
