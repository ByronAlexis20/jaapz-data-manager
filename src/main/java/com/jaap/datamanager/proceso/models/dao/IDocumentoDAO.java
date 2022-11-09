package com.jaap.datamanager.proceso.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Documento;

public interface IDocumentoDAO extends CrudRepository<Documento, Integer> {

	@Query("Select d from Documento d where d.estado = 'A'")
	public List<Documento> buscarDocumentosActivos();
	
	@Query("Select d from Documento d where d.id = ?1")
	public Documento buscarDocumentoPorId(Integer id);
	
	
}
