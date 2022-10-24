package com.jaap.datamanager.seguridad.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.seguridad.models.entity.Perfil;
import com.jaap.datamanager.seguridad.service.IPerfilService;
import com.jaap.datamanager.util.Constantes;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzseguridad/perfil")
public class PerfilRestController {

	@Autowired
	private IPerfilService perfilService;
	
	@GetMapping(value = "/buscaractivos")
	public ResponseEntity<?> buscarActivos() {
		List<Perfil> data = null;
		Map<String, Object> response = new HashMap<>();
		try {
			data = perfilService.buscarPerfilesActivos();
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (data == null || data.size() == 0) {
			response.put("mensaje: ", "No hay datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Perfil>>(data, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscartodosperfiles")
	public ResponseEntity<?> buscarTodosPerfiles() {
		List<Perfil> data = null;
		Map<String, Object> response = new HashMap<>();
		try {
			data = perfilService.buscarTodosPerfiles();
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (data == null || data.size() == 0) {
			response.put("mensaje: ", "No hay datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Perfil>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@Valid @RequestBody Perfil per, BindingResult result) {
		Perfil data = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			
			data = this.perfilService.grabarPerfil(per);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al grabar");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Grabado correctamente");
		response.put("usuario", data);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
