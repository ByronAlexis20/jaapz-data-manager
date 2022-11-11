package com.jaap.datamanager.proceso.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.proceso.models.entity.Cliente;
import com.jaap.datamanager.proceso.service.IClienteService;
import com.jaap.datamanager.util.Constantes;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/cliente")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/buscarclientes")
	public ResponseEntity<?> buscarclientes() {
		List<Cliente> data = null;
		Map<String, Object> response = new HashMap<>();
		try {
			data = this.clienteService.buscarClientes();
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (data == null || data.size() == 0) {
			response.put("mensaje: ", "No hay datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Cliente>>(data, HttpStatus.OK);
	}
	
	@GetMapping(value = "/validardatoscliente/{cedula}/{id}")
	public ResponseEntity<?> validardatoscliente(@PathVariable String cedula, @PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Boolean bandera = this.clienteService.validarCedula(cedula,id);
			if(bandera == true) {
				response.put("mensaje", "Ya existe un cliente con el número de identificacion " + cedula);
				response.put("status", "error");
			}else {
				response.put("mensaje", "No existe cliente con el numero de identificacion escrito");
				response.put("status", "ok");
			}
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@Valid @RequestBody Cliente cli, BindingResult result) {
		Cliente data = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			
			data = this.clienteService.grabarCliente(cli);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al grabar");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Grabado correctamente");
		response.put("usuario", data);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/buscarClientesActivos")
	public ResponseEntity<?> buscarClientesActivos() {
		List<Cliente> listaClientes = null;
		Map<String, Object> response = new HashMap<>();
		try {
			listaClientes = this.clienteService.buscarClientesPorEstado("A");
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Cliente>>(listaClientes, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscarClientesPlanillar/{idanio}/{idmes}")
	public ResponseEntity<?> buscarClientesPlanillar(@PathVariable Integer idanio, @PathVariable Integer idmes) {
		List<LinkedHashMap<String, Object>> listaClientes = null;
		Map<String, Object> response = new HashMap<>();
		try {
			listaClientes = this.clienteService.buscarClientesPlanillar(idanio, idmes);
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(listaClientes, HttpStatus.OK);
	}
	
	@GetMapping(value = "/consultarClientes")
	public ResponseEntity<?> consultarClientes() {
		List<LinkedHashMap<String, Object>> listaClientes = null;
		Map<String, Object> response = new HashMap<>();
		try {
			listaClientes = this.clienteService.consultarClientes();
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(listaClientes, HttpStatus.OK);
	}
}