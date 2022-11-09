package com.jaap.datamanager.seguridad.service;

import java.util.Map;

import com.jaap.datamanager.seguridad.models.entity.Empresa;

public interface IEmpresaService {

	public Empresa grabarEmpresa(Empresa emp);
	
	public Empresa buscarEmpresa();
	
	public Map<String, Object> consultarDatosEmpresa();
	
}
