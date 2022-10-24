package com.jaap.datamanager.proceso.service;

import java.util.List;

import com.jaap.datamanager.proceso.models.entity.Anio;

public interface IAnioService {

	public List<Anio> aniosActivos();
	public Anio buscarPorId(Integer id);
	public Anio grabarAnio(Anio anio);
	
}
