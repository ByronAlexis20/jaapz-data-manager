package com.jaap.datamanager.seguridad.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.seguridad.models.entity.Permiso;

public interface IPermisoDAO extends CrudRepository<Permiso, Integer> {
	
	@Query("Select p from Permiso p where p.estado = 'A' and p.perfil.id = ?1")
	public List<Permiso> buscarPorIdPerfil(Integer idPerfil);
	

}
