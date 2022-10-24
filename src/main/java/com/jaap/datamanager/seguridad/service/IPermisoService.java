package com.jaap.datamanager.seguridad.service;

import java.util.List;

import com.jaap.datamanager.seguridad.models.entity.Permiso;

public interface IPermisoService {

	public List<Permiso> buscarPorIdPerfil(Integer idPerfil);
	
}
