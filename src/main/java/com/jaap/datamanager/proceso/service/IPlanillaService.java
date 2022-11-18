package com.jaap.datamanager.proceso.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IPlanillaService {

	public Map<String, Object> consultarLecturasCliente(Integer idanio, Integer idmes, Integer idCliente);
	
	public Map<String, Object> grabarPlanilla(Map<String, Object> param);
	
	public List<Map<String, Object>> imprimirPlanilla(Integer idplanilla);
	
	public List<Object[]> consultarPlanillaPorClienteAnioMes(Integer idcliente, Integer idanio, Integer idmes);
	
	public List<LinkedHashMap<String, Object>> consultarPlanillasPorCliente(Integer idcliente);
	
	public Map<String, Object> eliminarPlanillaPorId(Integer id);
	
	public Map<String, Object> consultarDeudas();
	
	public List<LinkedHashMap<String, Object>> consultarDetallePlanilla(Integer idplanilla);
	
	public Map<String, Object> grabarDetallePlanilla(Map<String, Object> param);
	
	public List<LinkedHashMap<String, Object>> consultarDeudasCliente(Integer idcliente);
}
