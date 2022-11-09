package com.jaap.datamanager.proceso.service;

import com.jaap.datamanager.proceso.models.entity.Configuracion;

public interface IConfiguracionService {

	public Configuracion buscarCOnfiguracion();
	
	public Configuracion grabarCOnfiguracion(Configuracion conf);
	
}
