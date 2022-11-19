package com.jaap.datamanager.proceso.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.proceso.service.IFacturaService;
import com.jaap.datamanager.util.Constantes;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/factura")
public class FacturaRestController {

	@Autowired
	private IFacturaService facturaService;
	
	@PostMapping(value = "/grabarfactura")
	public ResponseEntity<?> grabarPlanilla( @RequestBody Map<String, Object> param ) {
		Map<String, Object> response = null;
		try {
			response = this.facturaService.grabarFactura(param);
		} catch (DataAccessException e) {
			response.put("estado", "error");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
