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
import com.jaap.datamanager.proceso.models.dao.IPlanillaDAO;
import com.jaap.datamanager.proceso.service.IPlanillaService;
import com.jaap.datamanager.util.CodigosEstandares;
import com.jaap.datamanager.util.ConvertirNumeroLetras;

@Service
public class PlanillaImpl implements IPlanillaService {

	@Autowired
	private IPlanillaDAO planillaDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> consultarLecturasCliente(Integer idanio, Integer idmes, Integer idCliente) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.buscarClienteLecturas(idanio, idmes, idCliente);
			List<Map<String, Object>> listMap = objectMapper.readValue(data, List.class);
			for(Map<String, Object> map : listMap) {
				respuesta = map;
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional
	public Map<String, Object> grabarPlanilla(Map<String, Object> param) {
		Map<String, Object> response = new HashMap<>();
		try {
			//consumo
			Integer lecturaanterior = Integer.parseInt( param.get("lecturaanterior").toString() );
			Integer lecturaactual = Integer.parseInt( param.get("lecturaactual").toString() );
			Integer lecturaingresada = Integer.parseInt( param.get("lectura").toString() );
			System.out.println("lectura anterior " + lecturaanterior );
			System.out.println("lectura actual " + lecturaactual );
			System.out.println("lectura ingresada " + lecturaingresada );
			if( lecturaanterior.equals(lecturaingresada) ) {
				System.out.println("lectura actual es igual a la lectura anterior");
				Integer consumominimo = Integer.parseInt( param.get("consumominimo").toString() );
				ConvertirNumeroLetras convertir = new ConvertirNumeroLetras();
				param.put("consumo", consumominimo);
				param.put("totalletras", "(" + convertir.Convertir(String.valueOf(consumominimo), true) + ")");
			}else {
				System.out.println("no son iguales");
				Integer consumo = lecturaingresada - lecturaanterior;
				ConvertirNumeroLetras convertir = new ConvertirNumeroLetras();
				param.put("consumo", consumo);
				param.put("totalletras", convertir.Convertir(String.valueOf(consumo), true));
			}
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(param);
			
			Integer resultado = this.planillaDAO.grabarPlanilla(jsonString);
			if(resultado == 1) {
				response.put("estado", "ok");
				response.put("mensaje", "Planilla grabado correctamente");
			}
		}catch(Exception ex) {
			response.put("estado", "error");
			response.put("mensaje", ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> imprimirPlanilla(Integer idplanilla) {
		List<Map<String, Object>> resultado = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarDatosPlanilla(idplanilla);
			resultado = objectMapper.readValue(data, List.class);
			System.out.println(resultado.toString());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> consultarPlanillaPorClienteAnioMes(Integer idcliente, Integer idanio, Integer idmes) {
		return this.planillaDAO.consultarPlanillaPorClienteAnioMes(idcliente, idanio, idmes);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<LinkedHashMap<String, Object>> consultarPlanillasPorCliente(Integer idcliente) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarPlanillasPorCliente(idcliente);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

	@Override
	@Transactional
	public Map<String, Object> eliminarPlanillaPorId(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			Integer res = this.planillaDAO.eliminarPlanillaPorId(id);
			if(res == 1) {
				respuesta.put("estado", "ok");
				respuesta.put("mensaje", "Planilla eliminada correctamente");
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
	public Map<String, Object> consultarDeudas() {
		Map<String, Object> retorno = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarDeudas();
			if(data != null) {
				List<LinkedHashMap<String, Object>> ret = objectMapper.readValue(data, List.class);
				for(LinkedHashMap<String, Object> obj : ret) {
					retorno.put("datos", (List<LinkedHashMap<String, Object>>) obj.get("datos"));
					retorno.put("total", obj.get("totalgeneral"));
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<LinkedHashMap<String, Object>> consultarDetallePlanilla(Integer idplanilla) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarDetallePlanilla(idplanilla);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> grabarDetallePlanilla(Map<String, Object> param) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConvertirNumeroLetras convertir = new ConvertirNumeroLetras();
			ObjectMapper objectMapper = new ObjectMapper();
			Integer idplanilla = 0;
			Double tsubtotal = 0.0, tdescuento = 0.0, ttotal = 0.0;

			//valores que ya se encuentran registrados en la base de datos+++++++++++++++++++++++++++++++
			List<Map<String, Object>> detalle = (List<Map<String, Object>>) param.get("detalle");
			for(Map<String, Object> det : detalle) {
				idplanilla = Integer.parseInt( det.get("idplanilla").toString() );
				if(Integer.parseInt(det.get("iddocumento").toString()) == CodigosEstandares.idDocumentoDescuento) {
					tdescuento = tdescuento + Double.valueOf( det.get("subtotal").toString() );
				}else {
					tsubtotal = tsubtotal + Double.valueOf( det.get("subtotal").toString() );
				}
			}
			//calculo del total de los valores que ya estan ingresados en la base de datos
			ttotal = tsubtotal - tdescuento;
			
			//carcular el total ingresado
			Double valorUnit = Double.parseDouble( param.get("valorunitario").toString() );
			Double cantidad = Double.parseDouble( param.get("cantidad").toString() );
			Double totalIngresado = valorUnit * cantidad;
			
			//Documento seleccionado
			Map<String, Object> documento = (Map<String, Object>) param.get("documento");
			//total definitivo, hay q actualizar en la cabecera
			if( Integer.parseInt( documento.get("id").toString() ) == CodigosEstandares.idDocumentoDescuento ) {
				//es un descuento que se esta ingresando
				ttotal = ttotal - totalIngresado; 
			}else {
				ttotal = ttotal + totalIngresado;
			}
			//actualizar en la cabecera el total a pagar
			Map<String, Object> jsonCabecera = new HashMap<>();
			jsonCabecera.put("totalpagar", ttotal);
			jsonCabecera.put("totalletras", "(" + convertir.Convertir(String.valueOf(ttotal), true) + ")");
			jsonCabecera.put("idplanilla", idplanilla);
			
			String jsonStringCabecera = objectMapper.writeValueAsString(jsonCabecera);
			Integer retornoActualizarCabecera = this.planillaDAO.insertarDetalles(jsonStringCabecera, "AC");
			if(retornoActualizarCabecera == 1) {//grabo con exito la actualizacion de la cabecera
				//si grabo la cabecera.. se debe grabar el detalle
				Map<String, Object> jsonDetalle = new HashMap<>();
				jsonDetalle.put("idplanilla", idplanilla);
				jsonDetalle.put("iddocumento", Integer.parseInt( documento.get("id").toString() ) );
				jsonDetalle.put("cantidad", param.get("cantidad").toString());
				jsonDetalle.put("descripcion", param.get("descripcion").toString());
				jsonDetalle.put("subtotal", totalIngresado);
				jsonDetalle.put("valorunitario", valorUnit);
				
				String jsonStringDetalle = objectMapper.writeValueAsString(jsonDetalle);
				//insertar el detalle de la planilla
				Integer retornoInsertDetalle = this.planillaDAO.insertarDetalles(jsonStringDetalle, "IND");
				if(retornoInsertDetalle == 1) {
					response.put("estado", "ok");
					response.put("mensaje", "Detalle de planilla grabada correctamente");
				}else {
					response.put("estado", "error");
					response.put("mensaje", "Error al insertar detalle de planilla");
				}
			}else {
				response.put("estado", "error");
				response.put("mensaje", "Error al actualizar datos de cabecera");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.put("estado", "error");
			response.put("mensaje", "Error al grabar detalle de planilla");
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarDeudasCliente(Integer idcliente) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultardeudascliente(idcliente);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> eliminarDetallePlanilla(Map<String, Object> param) {
		Map<String, Object> response = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ConvertirNumeroLetras convertir = new ConvertirNumeroLetras();
			Integer idplanilla = 0;
			Double tsubtotal = 0.0, tdescuento = 0.0, ttotal = 0.0, ttotalplanillaeliminar = 0.0;
			//primero debo actualizar las cantidades 
			List<Map<String, Object>> detalle = (List<Map<String, Object>>) param.get("planillas");
			for(Map<String, Object> det : detalle) {
				idplanilla = Integer.parseInt( det.get("idplanilla").toString() );
				if(Integer.parseInt(det.get("iddocumento").toString()) == CodigosEstandares.idDocumentoDescuento) {
					tdescuento = tdescuento + Double.valueOf( det.get("subtotal").toString() );
				}else {
					tsubtotal = tsubtotal + Double.valueOf( det.get("subtotal").toString() );
				}
			}
			ttotal = tsubtotal - tdescuento; 
			Map<String, Object> plaEliminar = (Map<String, Object>) param.get("planillaeliminar");
			//si la planilla a eliminar es un descuento, se suma el valor al total
			ttotalplanillaeliminar = Double.parseDouble(plaEliminar.get("subtotal").toString());
			if(Integer.parseInt(plaEliminar.get("iddocumento").toString()) == CodigosEstandares.idDocumentoDescuento) {
				ttotal = ttotal + ttotalplanillaeliminar; 
			}else {
				ttotal = ttotal - ttotalplanillaeliminar;
			}
			//actualizar en la cabecera el total a pagar
			Map<String, Object> jsonCabecera = new HashMap<>();
			jsonCabecera.put("totalpagar", ttotal);
			jsonCabecera.put("totalletras", "(" + convertir.Convertir(String.valueOf(ttotal), true) + ")");
			jsonCabecera.put("idplanilla", idplanilla);
			String jsonStringCabecera = objectMapper.writeValueAsString(jsonCabecera);
			Integer retornoActualizarCabecera = this.planillaDAO.insertarDetalles(jsonStringCabecera, "AC");
			if(retornoActualizarCabecera == 1) {//grabo con exito la actualizacion de la cabecera
				//si grabo la cabecera.. se debe eliminar el detalle
				Map<String, Object> jsonDetalle = new HashMap<>();
				jsonDetalle.put("iddetalle", plaEliminar.get("id").toString());
				String jsonStringDetalle = objectMapper.writeValueAsString(jsonDetalle);
				//insertar el detalle de la planilla
				Integer retornoInsertDetalle = this.planillaDAO.insertarDetalles(jsonStringDetalle, "ELI");
				if(retornoInsertDetalle == 1) {
					response.put("estado", "ok");
					response.put("mensaje", "Detalle de planilla eliminado correctamente");
				}else {
					response.put("estado", "error");
					response.put("mensaje", "Error al eliminar detalle de planilla");
				}
			}else {
				response.put("estado", "error");
				response.put("mensaje", "Error al actualizar datos de cabecera");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.put("estado", "error");
			response.put("mensaje", "Error al eliminar detalle de planilla");
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarReporteTomaLecturas() {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarReporteTomaLectura();
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarReporteConsolidadoConsumo(Integer idanio, Integer idmes) {
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarReporteConsolidadoConsumo(idmes, idanio);
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}

}
