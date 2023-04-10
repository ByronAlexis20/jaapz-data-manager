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
	
	public Map<String, Object> eliminarDetallePlanilla(Map<String, Object> param);
	
	public List<LinkedHashMap<String, Object>> consultarReporteTomaLecturas();
	
	public List<LinkedHashMap<String, Object>> consultarReporteConsolidadoConsumo(Integer idanio, Integer idmes);
	
	public List<LinkedHashMap<String, Object>> consultarPlanillaPorAnioMes(Integer idanio, Integer idmes);
	
	public List<LinkedHashMap<String, Object>> consultarPlanillaFirmadaPorAnioMes(Integer idanio, Integer idmes);
	
	public List<LinkedHashMap<String, Object>> consultarPlanillaFirmadaPorAnioMesEstado(Integer idanio, Integer idmes, String estado);
	
	public List<LinkedHashMap<String, Object>> consultarPlanillaRecibidadPorAnioMes(Integer idanio, Integer idmes);
	
	public Map<String, Object> firmarDocumentosMasivos(List<Map<String, Object>> param);
	
	public Map<String, Object> enviarCorreoMasivos(List<Map<String, Object>> param);
	
	public Map<String, Object> enviarcomprobantes(List<Map<String, Object>> param);
	
	public Map<String, Object> autorizarcomprobantes(List<Map<String, Object>> param);
	
	public byte[] generarFactura( Integer id );

}
