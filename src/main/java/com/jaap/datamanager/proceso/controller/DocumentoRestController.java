package com.jaap.datamanager.proceso.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.proceso.service.IDocumentoService;
import com.jaap.datamanager.util.Constantes;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/documento")
public class DocumentoRestController {

	@Autowired
	private IDocumentoService documentoService;
	
	@GetMapping(value = "/consultardocumento")
	public ResponseEntity<?> validardatoscliente() {
		List<LinkedHashMap<String, Object>> lista = null;
		Map<String, Object> response = new HashMap<>();
		try {
			lista = this.documentoService.consultarDocumento();
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(lista, HttpStatus.OK);
	}
	
}
