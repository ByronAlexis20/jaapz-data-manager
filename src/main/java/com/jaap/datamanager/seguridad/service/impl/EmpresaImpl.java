package com.jaap.datamanager.seguridad.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.seguridad.models.dao.IEmpresaDAO;
import com.jaap.datamanager.seguridad.models.entity.Empresa;
import com.jaap.datamanager.seguridad.service.IEmpresaService;

@Service
public class EmpresaImpl implements IEmpresaService {

	@Autowired
	private IEmpresaDAO empresaDAO;
	
	@Override
	@Transactional
	public Empresa grabarEmpresa(Empresa emp) {
		return this.empresaDAO.save(emp);
	}

	@Override
	@Transactional
	public Empresa buscarEmpresa() {
		return this.empresaDAO.buscarEmpresa();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> consultarDatosEmpresa() {
		Map<String, Object> resultado = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.empresaDAO.consultarDatosEmpresa();
			List<Map<String, Object>> list = objectMapper.readValue(data, List.class);
			for(Map<String, Object> obj : list) {
				resultado = obj;
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return resultado;
	}

}
