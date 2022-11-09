package com.jaap.datamanager.proceso.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Mes;

public interface IMesDAO extends CrudRepository<Mes, Integer> {

	@Query("Select m from Mes m where m.estado = 'A' order by m.id asc")
	public List<Mes> listaMesesActivos();
	
}
