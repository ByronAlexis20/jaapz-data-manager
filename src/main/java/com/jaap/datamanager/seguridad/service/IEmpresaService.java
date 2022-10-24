package com.jaap.datamanager.seguridad.service;

import com.jaap.datamanager.seguridad.models.entity.Empresa;

public interface IEmpresaService {

	public Empresa grabarEmpresa(Empresa emp);
	
	public Empresa buscarEmpresa();
	
}
