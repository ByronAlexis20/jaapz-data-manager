package com.jaap.datamanager.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.seguridad.models.dao.IPermisoDAO;
import com.jaap.datamanager.seguridad.models.entity.Permiso;
import com.jaap.datamanager.seguridad.service.IPermisoService;

@Service
public class PermisoImpl implements IPermisoService {

	@Autowired
	private IPermisoDAO permisoDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Permiso> buscarPorIdPerfil(Integer idPerfil) {
		return this.permisoDAO.buscarPorIdPerfil(idPerfil);
	}
	
}
