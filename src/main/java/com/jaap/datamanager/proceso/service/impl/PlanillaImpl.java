package com.jaap.datamanager.proceso.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.proceso.models.dao.IPlanillaDAO;
import com.jaap.datamanager.proceso.service.IPlanillaService;

@Service
public class PlanillaImpl implements IPlanillaService {

	@Autowired
	private IPlanillaDAO planillaDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> consultarLecturasCliente(Integer idanio, Integer idmes, Integer idCliente) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.buscarClienteLecturas(idanio, idmes, idCliente);
			List<Map<String, Object>> listMap = objectMapper.readValue(data, List.class);
			for(Map<String, Object> map : listMap) {
				respuesta = map;
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional
	public Map<String, Object> grabarPlanilla(Map<String, Object> param) {
		Map<String, Object> response = new HashMap<>();
		try {
			JSONObject obj = new JSONObject(param);
			Integer resultado = this.planillaDAO.grabarPlanilla(obj.toString());
			if(resultado == 1) {
				response.put("estado", "ok");
				response.put("mensaje", "Planilla grabado correctamente");
			}
		}catch(Exception ex) {
			response.put("estado", "error");
			response.put("mensaje", ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> imprimirPlanilla(Integer idplanilla) {
		List<Map<String, Object>> resultado = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarDatosPlanilla(idplanilla);
			resultado = objectMapper.readValue(data, List.class);
			System.out.println(resultado.toString());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> consultarPlanillaPorClienteAnioMes(Integer idcliente, Integer idanio, Integer idmes) {
		return this.planillaDAO.consultarPlanillaPorClienteAnioMes(idcliente, idanio, idmes);
	}

}
