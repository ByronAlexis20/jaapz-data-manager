package com.jaap.datamanager.proceso.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.proceso.models.dao.IPlanillaDAO;
import com.jaap.datamanager.proceso.service.IDashboardService;

@Service
public class DashboardImpl implements IDashboardService {

	@Autowired
	private IPlanillaDAO planillaDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> consultarDashboard(String usuario) {
		Map<String, Object> response = new HashMap<>();
		try {
			Calendar fecha = Calendar.getInstance();
	        int anio = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        System.out.println("dia: " + dia);
	        System.out.println("mes: " + mes);
	        System.out.println("anio: " + anio);
	        ObjectMapper mapper = new ObjectMapper();
	        String data = this.planillaDAO.consultarDatosDashboard(dia, mes, anio, usuario);
	        if( data != null ) {
	        	List<Map<String, Object>> list = mapper.readValue(data, List.class);
	        	for(Map<String, Object> map : list) {
	        		response = map;
	        	}
	        }
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return response;
	}

}
