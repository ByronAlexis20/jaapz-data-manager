package com.jaap.datamanager.proceso.service;

import java.util.List;
import java.util.Map;

public interface IPlanillaService {

	public Map<String, Object> consultarLecturasCliente(Integer idanio, Integer idmes, Integer idCliente);
	
	public Map<String, Object> grabarPlanilla(Map<String, Object> param);
	
	public List<Map<String, Object>> imprimirPlanilla(Integer idplanilla);
	
	public List<Object[]> consultarPlanillaPorClienteAnioMes(Integer idcliente, Integer idanio, Integer idmes);
}
