package com.jaap.datamanager.proceso.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.service.IFacturaService;

@Service
public class FacturaImpl implements IFacturaService {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> grabarFactura(Map<String, Object> param) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			List<LinkedHashMap<String, Object>> retorno = (List<LinkedHashMap<String, Object>>) param.get("planillaspendiente");
			
		}catch(Exception ex) {
			respuesta.put("status", "error");
			respuesta.put("mensaje", "Error al grabar factura");
		}
		return respuesta;
	}

}
