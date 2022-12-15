package com.jaap.datamanager.proceso.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.jaap.datamanager.proceso.service.IFacturaService;
import com.jaap.datamanager.seguridad.service.IEmpresaService;
import com.jaap.datamanager.util.Constantes;
import com.jaap.datamanager.util.FuncionesGenerales;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RequestMapping("/jaapzproceso/factura")
public class FacturaRestController {

	@Autowired
	private IFacturaService facturaService;
	
	@Autowired
	private IEmpresaService empresaService;
	
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
	
	@GetMapping(value = "/consultarfacturacliente/{idcliente}")
	public ResponseEntity<?> consultarfacturacliente( @PathVariable Integer idcliente ) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Map<String, Object>> lista = this.facturaService.consultarFacturasPorCliente(idcliente);
			if(lista.size() > 0) {
				response.put("status", "ok");
				response.put("data", lista);	
			}else {
				response.put("status", "error");
				response.put("data", "No hay datos de facturas");
			}
		} catch (DataAccessException e) {
			response.put("estado", "error");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/imprimirfactura/{idfactura}")
	public ResponseEntity<byte[]> imprimirplanillaconsumo( @PathVariable Integer idfactura ) throws JsonMappingException, JsonProcessingException {
		
		List<Map<String, Object>> listaFacturas = this.facturaService.listaFacturas(idfactura);
		Map<String, Object> cabecera = new HashMap<>();
		List<Map<String, Object>> detalle = new ArrayList<>();
		for(Map<String, Object> fac : listaFacturas) {
			detalle = (List<Map<String, Object>>) fac.get("detalle");
			cabecera = fac;
		}
		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		String hora = formato.format(new Date());
		
		FuncionesGenerales genera = new FuncionesGenerales();
		Map<String, Object> empresa = empresaService.consultarDatosEmpresa();
		Map<String, Object> param = new HashMap<>();
		param.put("cedula", cabecera.get("cedula").toString());
		param.put("nombre", cabecera.get("nombres").toString());
		param.put("fecha", cabecera.get("fecha").toString());
		param.put("numerocomprobante", cabecera.get("numerocomprobante").toString());
		param.put("numeroautorizacion", "");
		param.put("valorpagado", cabecera.get("totalpagado").toString());
		param.put("valortotalplanilla", cabecera.get("valorplanillas").toString());
		param.put("totaldeuda", cabecera.get("totaldeuda").toString());
		param.put("valorabonado", cabecera.get("valoresabonados").toString());
		param.put("valorespendientes", cabecera.get("valorespendienteplanilla").toString());
		param.put("usuario", cabecera.get("usuariocrea").toString());
		param.put("hora", hora);
		param.put("saldopendiente", cabecera.get("saldopendiente").toString());
		param.put("facturaspendientes", cabecera.get("facturaspendientes").toString());
		param.put("ruc", empresa.get("ruc").toString());
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(detalle, false);
		byte[] bytes = genera.generarReportePDF("rptFactura", param, source);
		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename("rptFactura" + ".pdf").build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8").headers(headers)
				.body(bytes);

	}

	@GetMapping(value = "/eliminarfacturaporid/{id}")
	public ResponseEntity<?> eliminarplanillaporid(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			response = this.facturaService.eliminarFacturaPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje: ", "Error al buscar");
			response.put("error: ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/imprimirrecaudaciondiaria/{fecha}")
	public ResponseEntity<byte[]> imprimirRecaudacionDiaria( @PathVariable Date fecha ) throws JsonMappingException, JsonProcessingException {
		SimpleDateFormat formatoDia = new SimpleDateFormat("dd");
		SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
		SimpleDateFormat formatoAnio = new SimpleDateFormat("yyyy");
		
		Integer dia = Integer.parseInt(formatoDia.format(fecha));
		Integer mes = Integer.parseInt(formatoMes.format(fecha));
		Integer anio = Integer.parseInt(formatoAnio.format(fecha));
		
		List<LinkedHashMap<String, Object>> listaFacturas = this.facturaService.consultarRecaudacionDiaria(dia, mes, anio);
		double totalRecaudado = 0.0;
		for(LinkedHashMap<String, Object> obj : listaFacturas) {
			totalRecaudado = totalRecaudado + Double.parseDouble(obj.get("totalpagado").toString());
		}
		double total = Math.round(totalRecaudado * 100.0) / 100.0;
		
		Map<String, Object> params = empresaService.consultarDatosEmpresa();
		params.put("fecha", FuncionesGenerales.fechaString(fecha));
		params.put("nombrereporte", "PAGOS DE USUARIO POR FECHA");
		params.put("totalrecaudado", "$ " + total);
		FuncionesGenerales genera = new FuncionesGenerales();
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaFacturas, false);
		byte[] bytes = genera.generarReportePDF("rptRecaudacionDiaria", params, source);
		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename("rptRecaudacionDiaria" + ".pdf").build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8").headers(headers)
				.body(bytes);

	}
	
}
