package com.jaap.datamanager.proceso.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.proceso.models.dao.IFacturaDAO;
import com.jaap.datamanager.proceso.service.IFacturaService;

@Service
public class FacturaImpl implements IFacturaService {

	@Autowired
	private IFacturaDAO facturaDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> grabarFactura(Map<String, Object> param) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			System.out.println("json ingreso -->> " + mapper.writeValueAsString( param ) );
			
			List<LinkedHashMap<String, Object>> planillasPendientes = (List<LinkedHashMap<String, Object>>) param.get("planillaspendiente");
			Double abono = Double.parseDouble( param.get("abono").toString() );
			Map<String, Object> cliente = (Map<String, Object>) param.get("cliente");
			List<Map<String, Object>> planillas = new ArrayList<>(); //lista de planillas a modificar
			
			//armar el json de cabecera
			Map<String, Object> cabecera = new HashMap<>();
			List<LinkedHashMap<String, Object>> detalles = new ArrayList<>(); //lista de detalles
			cabecera.put("idcliente", cliente.get("id").toString());
			double totalpagado = 0.0;
			for(LinkedHashMap<String, Object> pl : planillasPendientes) {
				Double totalfactura = Double.parseDouble( pl.get("saldo").toString() );
				if( abono > 0) {
					if( abono >= totalfactura) {
						System.out.println("abono es mayor a cero --> " + abono);
						LinkedHashMap<String, Object> det = new LinkedHashMap<>();
						det.put("descripcion", "Consumo mes: " + pl.get("mes").toString() + "/" + pl.get("anio").toString() + " por: " + pl.get("totalpagar").toString() + "m3");
						det.put("idplanilla", pl.get("id").toString());
						det.put("cantidad", (int) Math.round( Double.parseDouble( pl.get("totalpagar").toString()) ) );
						det.put("valorunitario", 1);
						det.put("subtotal", pl.get("saldo").toString());
						Map<String, Object> detPl = new HashMap<>();
						detPl.put("idplanilla", pl.get("id").toString());
						planillas.add(detPl);
						detalles.add(det);
						totalpagado = totalpagado + totalfactura;
						abono = abono - totalfactura;
					}else {
						System.out.println("abono es menor a cero --> " + abono);
						LinkedHashMap<String, Object> det = new LinkedHashMap<>();
						det.put("descripcion", "Abono mes: " + pl.get("mes").toString() + "/" + pl.get("anio").toString() + " por: " + pl.get("totalpagar").toString() + "m3");
						det.put("idplanilla", pl.get("id").toString());
						det.put("cantidad", (int) Math.round(abono) );
						det.put("valorunitario", 1);
						det.put("subtotal", abono);
						detalles.add(det);
						totalpagado = totalpagado + abono;
						abono = 0.0;
					}
				}
			}
			cabecera.put("totalpagado", totalpagado);
			cabecera.put("usuario", param.get("usuario").toString());
			
			String jsonCabecera = mapper.writeValueAsString(cabecera);
			String jsonDetalle = mapper.writeValueAsString(detalles);
			String jsonPlanillas = mapper.writeValueAsString(planillas);
			
			System.out.println("cabecera: " + jsonCabecera);
			System.out.println("detalle: " + jsonDetalle);
			System.out.println("planillas: " + jsonPlanillas);
			
			//proceso de grabar
			Integer resFact = this.facturaDAO.grabarFactura(jsonCabecera, jsonDetalle, jsonPlanillas);
			if(resFact != 0) {
				respuesta.put("status", "ok");
				respuesta.put("mensaje", "Grabado correctamente");
				respuesta.put("idfactura", resFact);
			}else {
				respuesta.put("status", "error");
				respuesta.put("mensaje", "Error al grabar factura");
			}
			
		}catch(Exception ex) {
			System.out.println( ex.getMessage() );
			respuesta.put("status", "error");
			respuesta.put("mensaje", "Error al grabar factura");
		}
		return respuesta;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> listaFacturas(Integer idfactura) {
		List<Map<String, Object>> lista = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.facturaDAO.consultarFactura(idfactura);
			if (data != null) {
				lista = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> consultarFacturasPorCliente(Integer idcliente) {
		List<Map<String, Object>> lista = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.facturaDAO.consultarFacturasCliente(idcliente);
			if (data != null) {
				lista = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return lista;
	}

	@Override
	@Transactional
	public Map<String, Object> eliminarFacturaPorId(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			Integer res = this.facturaDAO.eliminarFacturaPorId(id);
			if(res == 1) {
				respuesta.put("estado", "ok");
				respuesta.put("mensaje", "Factura eliminada correctamente");
			}else {
				respuesta.put("estado", "error");
				respuesta.put("mensaje", "Error al eliminar");
			}
		}catch(Exception ex) {
			respuesta.put("estado", "error");
			respuesta.put("mensaje", "Error al eliminar");
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarRecaudacionDiaria(Integer dia, Integer mes, Integer anio) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.facturaDAO.consultarReporteRecaudacionDiaria(dia, mes, anio);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}
}