package com.jaap.datamanager.proceso.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.proceso.service.IDashboardService;
import com.jaap.datamanager.util.Constantes;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/dashboard")
public class DashboardRestController {

	@Autowired
	private IDashboardService dashboardService;
	
	@GetMapping(value = "/consultarDashboard/{usuario}")
	public ResponseEntity<?> consultarDashboard( @PathVariable String usuario ) {
		Map<String, Object> response = null;
		try {
			response = this.dashboardService.consultarDashboard( usuario );
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
