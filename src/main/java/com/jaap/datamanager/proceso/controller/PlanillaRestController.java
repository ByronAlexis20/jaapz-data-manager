package com.jaap.datamanager.proceso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jaap.datamanager.proceso.service.IPlanillaService;
import com.jaap.datamanager.seguridad.service.IEmpresaService;
import com.jaap.datamanager.util.Constantes;
import com.jaap.datamanager.util.FuncionesGenerales;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/planilla")
public class PlanillaRestController {

	@Autowired
	private IPlanillaService planillaService;
	
	@Autowired
	private IEmpresaService empresaService;
	
	@GetMapping(value = "/consultarlecturascliente/{idanio}/{idmes}/{idcliente}")
	public ResponseEntity<?> consultarlecturascliente(@PathVariable Integer idanio, @PathVariable Integer idmes, @PathVariable Integer idcliente) {
		Map<String, Object> response = null;
		try {
			response = this.planillaService.consultarLecturasCliente(idanio, idmes, idcliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/grabarPlanilla")
	public ResponseEntity<?> grabarPlanilla( @RequestBody Map<String, Object> param ) {
		Map<String, Object> response = null;
		try {
			response = this.planillaService.grabarPlanilla(param);
		} catch (DataAccessException e) {
			response.put("estado", "error");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/imprimirplanillaconsumo/{idcliente}/{idanio}/{idmes}")
	public ResponseEntity<byte[]> imprimirplanillaconsumo(@PathVariable Integer idcliente, @PathVariable Integer idanio, @PathVariable Integer idmes) throws JsonMappingException, JsonProcessingException {
		
		List<Object[]> buscarPlanilla = this.planillaService.consultarPlanillaPorClienteAnioMes(idcliente, idanio, idmes);
		Integer idplanilla = 0;
		for(Object[] obj : buscarPlanilla) {
			idplanilla = Integer.parseInt(obj[0].toString());
		}
		
		List<Map<String, Object>> dataFinal = new ArrayList<>();
		dataFinal = this.planillaService.imprimirPlanilla(idplanilla);
		FuncionesGenerales genera = new FuncionesGenerales();
		
		Map<String, Object> params = empresaService.consultarDatosEmpresa();
		String numeroPlanilla = "0000000" + idplanilla;
		numeroPlanilla = numeroPlanilla.substring(numeroPlanilla.length() - 4, numeroPlanilla.length());
		params.put("noplanilla", "No. " + numeroPlanilla);
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataFinal, false);
		byte[] bytes = genera.generarReportePDF("rptPlanillaConsumo", params, source);
		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename("rptPlanillaConsumo" + ".pdf").build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8").headers(headers)
				.body(bytes);

	}
	
	@GetMapping(value = "/consultarplanillasporcliente/{idcliente}")
	public ResponseEntity<?> validardatoscliente(@PathVariable Integer idcliente) {
		List<LinkedHashMap<String, Object>> listaClientes = null;
		Map<String, Object> response = new HashMap<>();
		try {
			listaClientes = this.planillaService.consultarPlanillasPorCliente(idcliente);
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(listaClientes, HttpStatus.OK);
	}
	
	@GetMapping(value = "/eliminarplanillaporid/{id}")
	public ResponseEntity<?> eliminarplanillaporid(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			response = this.planillaService.eliminarPlanillaPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/imprimirdeudas")
	public ResponseEntity<byte[]> imprimirDeudas() throws JsonMappingException, JsonProcessingException {
		List<LinkedHashMap<String, Object>> dataFinal = new ArrayList<>();
		dataFinal = this.planillaService.consultarDeudas();
		FuncionesGenerales genera = new FuncionesGenerales();
		
		Map<String, Object> params = empresaService.consultarDatosEmpresa();
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataFinal, false);
		byte[] bytes = genera.generarReportePDF("rptDeudas", params, source);
		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename("rptDeudas" + ".pdf").build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8").headers(headers)
				.body(bytes);

	}
}
