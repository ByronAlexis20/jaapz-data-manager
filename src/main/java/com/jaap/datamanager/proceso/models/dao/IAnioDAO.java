package com.jaap.datamanager.proceso.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Anio;

public interface IAnioDAO extends CrudRepository<Anio, Integer> {

	@Query("Select a from Anio a where a.estado = 'A' order by a.anio desc")
	public List<Anio> buscarAnios();
	
	@Query("Select a from Anio a where a.estado = 'A' and a.id = ?1")
	public Anio buscarAnioPorId(Integer id);
	
}
