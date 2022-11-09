package com.jaap.datamanager.proceso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IConfiguracionDAO;
import com.jaap.datamanager.proceso.models.entity.Configuracion;
import com.jaap.datamanager.proceso.service.IConfiguracionService;

@Service
public class ConfiguracionImpl implements IConfiguracionService {

	@Autowired
	private IConfiguracionDAO configuracionDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Configuracion buscarCOnfiguracion() {
		return this.configuracionDAO.buscarConfiguracion();
	}

	@Override
	@Transactional
	public Configuracion grabarCOnfiguracion(Configuracion conf) {
		return this.configuracionDAO.save(conf);
	}

}
