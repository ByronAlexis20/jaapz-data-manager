package com.jaap.datamanager.proceso.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IFacturaService {

	public Map<String, Object> grabarFactura(Map<String, Object> pram);
	
	public List<Map<String, Object>> listaFacturas(Integer idfactura);
	
	public List<Map<String, Object>> consultarFacturasPorCliente(Integer idcliente);
	
	public Map<String, Object> eliminarFacturaPorId(Integer id);
	
	public List<LinkedHashMap<String, Object>> consultarRecaudacionDiaria(Integer dia, Integer mes, Integer anio);
}
