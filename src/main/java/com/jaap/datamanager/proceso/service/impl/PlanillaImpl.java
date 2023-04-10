package com.jaap.datamanager.proceso.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaap.datamanager.mail.Hilo2;
import com.jaap.datamanager.proceso.models.dao.IConfiguracionDAO;
import com.jaap.datamanager.proceso.models.dao.IPlanillaDAO;
import com.jaap.datamanager.proceso.models.entity.Configuracion;
import com.jaap.datamanager.proceso.service.IPlanillaService;
import com.jaap.datamanager.seguridad.models.dao.IEmpresaDAO;
import com.jaap.datamanager.seguridad.models.entity.Empresa;
import com.jaap.datamanager.sri.EnvioComprobantes;
import com.jaap.datamanager.util.CodigosEstandares;
import com.jaap.datamanager.util.ConvertirNumeroLetras;
import com.jaap.datamanager.util.FuncionesGenerales;
import com.jaap.datamanager.util.GenerarFacturaXml;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PlanillaImpl implements IPlanillaService {

	@Autowired
	private IPlanillaDAO planillaDAO;
	
	@Autowired
	private IEmpresaDAO empresaDAO;
	
	@Autowired
	private IConfiguracionDAO configuracionDAO;
	
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
			if(resultado != 0) {
				
				//this.generarXmlFactura(resultado);
				
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
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Transactional
	private Map<String, Object> generarXmlFactura(Integer id){
		Map<String, Object> response = new HashMap<>();
		try {
			Configuracion conf = this.configuracionDAO.buscarConfiguracion();
			FuncionesGenerales fun = new FuncionesGenerales();
			ObjectMapper mapper = new ObjectMapper();
			Empresa empresa = this.empresaDAO.buscarEmpresa();
			GenerarFacturaXml facturaXml = new GenerarFacturaXml();
			Map<String, Object> datos = new HashMap<>();
			datos.put("empresa", empresa);
			
			String datoFactura = this.planillaDAO.consultarDatosPlanillaFacturaElectronica(id);
			String fechaFactura = "";
			String numeroFactura = "";
			if(datoFactura != null) {
				List<LinkedHashMap<String, Object>> factura = mapper.readValue(datoFactura, List.class);
				for(LinkedHashMap<String, Object> map : factura) {
					datos.put("factura", map);
					fechaFactura = map.get("fecha").toString();
					numeroFactura = "000000000" + map.get("numerofactura").toString();
				}
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			//clave de acceso
			numeroFactura = numeroFactura.substring(numeroFactura.length() - 9, numeroFactura.length());
			String claveAcceso = fun.obtenerClaveAcceso(formatter.parse(fechaFactura), numeroFactura, empresa.getRuc());
			datos.put("claveacceso", claveAcceso);
			datos.put("secuencial", numeroFactura);
			datos.put("configuracion", conf);
			
			facturaXml.generarXmlFactura(datos);
			
			//actualizar la clave de acceso
			Integer i = this.planillaDAO.actualizarClaveAcceso(id, claveAcceso);
			if( i == 1) {
				System.out.println("Actualizado la clave de acceso");
			}
			//firmar el archivo xml
			jaapz.sri.firmar.XAdESBESSignature.firmar(conf.getRutagenerados() + claveAcceso + ".xml" , conf.getRutafirma(), conf.getClavefirma(), conf.getRutafirmados(), claveAcceso + ".xml");
			
		}catch(Exception ex) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarPlanillaPorAnioMes(Integer idanio, Integer idmes){
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> mpBus = new HashMap<>();
			mpBus.put("idmes", idmes);
			mpBus.put("idanio", idanio);
			String jsonBus = objectMapper.writeValueAsString(mpBus);
			String data = this.planillaDAO.procesoPlanila(jsonBus, "CPAF");
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
	public List<LinkedHashMap<String, Object>> consultarPlanillaFirmadaPorAnioMes(Integer idanio, Integer idmes){
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> mpBus = new HashMap<>();
			mpBus.put("idmes", idmes);
			mpBus.put("idanio", idanio);
			String jsonBus = objectMapper.writeValueAsString(mpBus);
			String data = this.planillaDAO.procesoPlanila(jsonBus, "CPFI");
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
	public List<LinkedHashMap<String, Object>> consultarPlanillaRecibidadPorAnioMes(Integer idanio, Integer idmes){
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> mpBus = new HashMap<>();
			mpBus.put("idmes", idmes);
			mpBus.put("idanio", idanio);
			String jsonBus = objectMapper.writeValueAsString(mpBus);
			String data = this.planillaDAO.procesoPlanila(jsonBus, "CPAT");
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
	public Map<String, Object> firmarDocumentosMasivos(List<Map<String, Object>> param){
		Map<String, Object> retorno = new HashMap<>();
		try {
			for(Map<String, Object> dat : param) {
				System.out.println(dat.get("id"));
				this.generarXmlFactura(Integer.parseInt(dat.get("id").toString()));
			}
			retorno.put("estado", "ok");
			retorno.put("mensaje", "Archivos firmados correctamente");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			retorno.put("estado", "error");
			retorno.put("mensaje", "Error al firmar documentos");
		}
		return retorno;
	}
	
	@Override
	@Transactional
	public Map<String, Object> enviarcomprobantes(List<Map<String, Object>> param){
		Map<String, Object> retorno = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			EnvioComprobantes prueba = new EnvioComprobantes();
			Configuracion conf = this.configuracionDAO.buscarConfiguracion();
			for(Map<String, Object> dat : param) {
				if( dat.get("estadoenviado").toString().equals("NO ENVIADO") ) {
					Map<String, Object> res = prueba.enviarComprobante(Integer.parseInt( dat.get("id").toString() ), conf.getRutafirmados() + dat.get("claveacceso").toString() + ".xml");
					System.out.println(res.toString());
					
					Map<String, Object> mpBus = new HashMap<>();
					mpBus.put("estado", res.get("estadoenvio").toString());
					mpBus.put("mensaje", res.get("mensaje").toString());
					mpBus.put("id", Integer.parseInt( dat.get("id").toString() ));
					String jsonBus = objectMapper.writeValueAsString(mpBus);
					this.planillaDAO.procesoPlanila(jsonBus, "ACEE");
				}
			}
			retorno.put("estado", "ok");
			retorno.put("mensaje", "Archivos firmados correctamente");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			retorno.put("estado", "error");
			retorno.put("mensaje", "Error al firmar documentos");
		}
		return retorno;
	}
	
	@Override
	@Transactional
	public Map<String, Object> autorizarcomprobantes(List<Map<String, Object>> param){
		Map<String, Object> retorno = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			EnvioComprobantes prueba = new EnvioComprobantes();
			Configuracion conf = this.configuracionDAO.buscarConfiguracion();
			for(Map<String, Object> dat : param) {
				System.out.println(dat.get("claveacceso").toString());
				Map<String, Object> res = prueba.autorizacionComprobante( dat.get("claveacceso").toString(), conf.getRutaautorizados() );
				System.out.println(res.toString());
				//ACTUALIZAR ESTADO DE AUTORIZACION
				if(res.get("estado").toString().equals("ok")) {
					Map<String, Object> mpBus = new HashMap<>();
					mpBus.put("estado", res.get("status").toString());
					mpBus.put("mensaje", res.get("mensaje").toString());
					mpBus.put("id", Integer.parseInt( dat.get("id").toString() ));
					String jsonBus = objectMapper.writeValueAsString(mpBus);
					this.planillaDAO.procesoPlanila(jsonBus, "ACAS");
					
					this.enviarCorreoDesdeAutorizar( Integer.parseInt( dat.get("id").toString() ));
				}
			}
			retorno.put("estado", "ok");
			retorno.put("mensaje", "Archivos autorizados correctamente");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			retorno.put("estado", "error");
			retorno.put("mensaje", "Error al firmar documentos");
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	private void enviarCorreoDesdeAutorizar( Integer id ) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarPlanillaEnviar(id);
			if( data != null ) {
				Empresa empresa = this.empresaDAO.buscarEmpresa();
				Configuracion conf = this.configuracionDAO.buscarConfiguracion();
				List<Map<String, Object>> lista = objectMapper.readValue(data, List.class);
				for(Map<String, Object> dat : lista ) {
					
					if( dat.get("email") != null ) {
						Map<String, Object> parametros = new HashMap<>();
						parametros.put("RUC", empresa.getRuc());
						parametros.put("NUM_AUT", dat.get("claveacceso").toString());
						parametros.put("TIPO_EMISION", "NORMAL");
						parametros.put("CLAVE_ACC", dat.get("claveacceso").toString());
						parametros.put("RAZON_SOCIAL", empresa.getRazonSocial());
						parametros.put("DIR_MATRIZ", empresa.getDireccion());
						parametros.put("LLEVA_CONTABILIDAD", "NO");
						parametros.put("RS_COMPRADOR", dat.get("cliente").toString());
						parametros.put("RUC_COMPRADOR", dat.get("cedula").toString());
						parametros.put("FECHA_EMISION", dat.get("fecha").toString());
						parametros.put("NUM_FACT", dat.get("factura").toString());
						parametros.put("AMBIENTE", "PRODUCCIÓN");
						parametros.put("REGIMEN_RIMPE", "RIMPE - EMPRENDEDOR");
						
						parametros.put("DIR_CLIENTE", dat.get("direccion")==null?"":dat.get("direccion").toString() );
						parametros.put("EMAIL_CLIENTE", dat.get("email")==null?"":dat.get("email").toString() );
						parametros.put("TELF_CLIENTE", dat.get("telefono")==null?"":dat.get("telefono").toString() );
						parametros.put("NUMERO_MEDIDOR", dat.get("numeromedidor")==null?"":dat.get("numeromedidor").toString() );
						
						parametros.put("SUBTOTAL_12", "0.00");
						parametros.put("SUBTOTAL_0", dat.get("subtotal").toString());
						parametros.put("SUBTOTAL_NO_IVA", "0.00");
						parametros.put("SUBTOTAL_IVA", "0.00");
						parametros.put("SUBTOTAL_SIN_IMPUESTOS", dat.get("subtotal").toString());
						parametros.put("TOTAL_DESCUENTO", dat.get("descuento").toString());
						parametros.put("ICE", "0.00");
						parametros.put("IVA12", "0.00");
						parametros.put("DEVOLUCION_IVA", "0.00");
						parametros.put("ISBPNR", "0.00");
						parametros.put("PROPINA", "0.00");
						parametros.put("VALOR_TOTAL", dat.get("totalpagar").toString());
						parametros.put("TOTAL_SIN_SUBSIDIO", "0.00");
						parametros.put("AHORRO_SUBSIDIO", "0.00");
						
						List<Map<String, Object>> detalles = (List<Map<String, Object>>) dat.get("detalle");
						
						List<Map<String, Object>> dataFinal = new ArrayList<>();
						
						for(Map<String, Object> det : detalles ) {
							Map<String, Object> detalle = new HashMap<>();
							detalle.put("codigoPrincipal", det.get("codigo").toString());
							detalle.put("codigoAuxiliar", "");
							detalle.put("cantidad", det.get("cantidad").toString());
							detalle.put("descripcion", det.get("descripcion").toString().toUpperCase());
							detalle.put("precioUnitario", det.get("valorunitario").toString());
							detalle.put("descuento", "0.00");
							detalle.put("precioTotalSinImpuesto", det.get("subtotal").toString());
							dataFinal.add(detalle);
						}
						
						JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataFinal, false);
						FuncionesGenerales genera = new FuncionesGenerales();
						byte[] bytes = genera.generarReportePDF("factura", parametros, source);
						String asunto = "Factura de consumo de agua JAAPZ";
						String mensaje = "Estimado/a: " + dat.get("cliente").toString() + "\n";
						mensaje = mensaje + "Se ha generado su factura electrónica. \n";
						mensaje = mensaje + "No: " + dat.get("factura").toString() + ". \n";
						mensaje = mensaje + "Fecha de emisión: " + dat.get("fecha").toString() + ". \n";
						mensaje = mensaje + "Total: " + dat.get("totalpagar").toString() + ". \n\n\n";
						mensaje = mensaje + "El documento pdf y xml de su factura se encuentra adjunto a este correo. \n\n";
						
						mensaje = mensaje + "Atentamente: \n";
						mensaje = mensaje + "La directiva \n";
						mensaje = mensaje + "JAAP Zapotal \n";
						//agregar informacion para ver la factura desde el sistema
						this.enviarcorreo(dat.get("email").toString(), asunto, mensaje, bytes, conf.getRutaautorizados() + dat.get("claveacceso").toString() + ".xml", dat.get("claveacceso").toString() + ".xml");
					}
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public byte[] generarFactura( Integer id ) {
		System.out.println("id: " + id);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String data = this.planillaDAO.consultarPlanillaEnviar(id);
			if( data != null ) {
				Empresa empresa = this.empresaDAO.buscarEmpresa();
				List<Map<String, Object>> lista = objectMapper.readValue(data, List.class);
				for(Map<String, Object> dat : lista ) {
					Map<String, Object> parametros = new HashMap<>();
					parametros.put("RUC", empresa.getRuc());
					parametros.put("NUM_AUT", dat.get("claveacceso").toString());
					parametros.put("TIPO_EMISION", "NORMAL");
					parametros.put("CLAVE_ACC", dat.get("claveacceso").toString());
					parametros.put("RAZON_SOCIAL", empresa.getRazonSocial());
					parametros.put("DIR_MATRIZ", empresa.getDireccion());
					parametros.put("LLEVA_CONTABILIDAD", "NO");
					parametros.put("RS_COMPRADOR", dat.get("cliente").toString());
					parametros.put("RUC_COMPRADOR", dat.get("cedula").toString());
					parametros.put("FECHA_EMISION", dat.get("fecha").toString());
					parametros.put("NUM_FACT", dat.get("factura").toString());
					parametros.put("AMBIENTE", "PRODUCCIÓN");
					parametros.put("REGIMEN_RIMPE", "RIMPE - EMPRENDEDOR");
					
					parametros.put("DIR_CLIENTE", dat.get("direccion")==null?"":dat.get("direccion").toString() );
					parametros.put("EMAIL_CLIENTE", dat.get("email")==null?"":dat.get("email").toString() );
					parametros.put("TELF_CLIENTE", dat.get("telefono")==null?"":dat.get("telefono").toString() );
					parametros.put("NUMERO_MEDIDOR", dat.get("numeromedidor")==null?"":dat.get("numeromedidor").toString() );
					
					parametros.put("SUBTOTAL_12", "0.00");
					parametros.put("SUBTOTAL_0", dat.get("subtotal").toString());
					parametros.put("SUBTOTAL_NO_IVA", "0.00");
					parametros.put("SUBTOTAL_IVA", "0.00");
					parametros.put("SUBTOTAL_SIN_IMPUESTOS", dat.get("subtotal").toString());
					parametros.put("TOTAL_DESCUENTO", dat.get("descuento").toString());
					parametros.put("ICE", "0.00");
					parametros.put("IVA12", "0.00");
					parametros.put("DEVOLUCION_IVA", "0.00");
					parametros.put("ISBPNR", "0.00");
					parametros.put("PROPINA", "0.00");
					parametros.put("VALOR_TOTAL", dat.get("totalpagar").toString());
					parametros.put("TOTAL_SIN_SUBSIDIO", "0.00");
					parametros.put("AHORRO_SUBSIDIO", "0.00");
					
					List<Map<String, Object>> detalles = (List<Map<String, Object>>) dat.get("detalle");
					
					List<Map<String, Object>> dataFinal = new ArrayList<>();
					
					for(Map<String, Object> det : detalles ) {
						Map<String, Object> detalle = new HashMap<>();
						detalle.put("codigoPrincipal", det.get("codigo").toString());
						detalle.put("codigoAuxiliar", "");
						detalle.put("cantidad", det.get("cantidad").toString());
						detalle.put("descripcion", det.get("descripcion").toString().toUpperCase());
						detalle.put("precioUnitario", det.get("valorunitario").toString());
						detalle.put("descuento", "0.00");
						detalle.put("precioTotalSinImpuesto", det.get("subtotal").toString());
						dataFinal.add(detalle);
					}
					
					JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataFinal, false);
					FuncionesGenerales genera = new FuncionesGenerales();
					byte[] bytes = genera.generarReportePDF("factura", parametros, source);
					
					//armar el mensaje
					/*
					if( dat.get("email") != null ) {
						String asunto = "Factura mes " + dat.get("mes").toString() + " del año " + dat.get("anio").toString() + " - JAAPZ";
						String mensaje = "Estimado/a: " + dat.get("cliente").toString() + "\n";
						mensaje = mensaje + "Se ha generado su factura electrónica. \n";
						mensaje = mensaje + "No: " + dat.get("factura").toString() + ". \n";
						mensaje = mensaje + "Fecha de emisión: " + dat.get("fecha").toString() + ". \n";
						mensaje = mensaje + "Total mensual: " + dat.get("totalpagar").toString() + ". \n\n\n";
						mensaje = mensaje + "El documento pdf se encuentra adjunto a este correo \n\n";
						
						mensaje = mensaje + "Atentamente: \n";
						mensaje = mensaje + "La directiva \n";
						mensaje = mensaje + "JAAP Zapotal \n";
						
						this.enviarcorreo(dat.get("email").toString(), asunto, mensaje, bytes, conf.getRutaautorizados() + dat.get("claveacceso").toString() + ".xml", dat.get("claveacceso").toString() + ".xml");
					}*/
					return bytes;
				}
			}	
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return null;
	}
	
	private void enviarcorreo(String correo, String asunto, String mensaje, byte[] archivo, String xml, String nombre) {
		String adjunto = "";
		String destinatario;
		int servidor;
		String[] destinatarios;
		destinatario = correo;
		destinatarios = destinatario.split(";");
		servidor = 1;
		
		Hilo2 miHilo = new Hilo2(adjunto, archivo, destinatarios, servidor, destinatario, asunto, mensaje, xml, nombre);
		miHilo.enviarCorreoSolicitud();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> enviarCorreoMasivos(List<Map<String, Object>> param){
		Map<String, Object> response = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			for( Map<String, Object> lst : param ) {
				Integer id = Integer.parseInt( lst.get("id").toString() );
				String data = this.planillaDAO.consultarPlanillaEnviar(id);
				if( data != null ) {
					Empresa empresa = this.empresaDAO.buscarEmpresa();
					Configuracion conf = this.configuracionDAO.buscarConfiguracion();
					List<Map<String, Object>> lista = objectMapper.readValue(data, List.class);
					for(Map<String, Object> dat : lista ) {
						
						if( dat.get("email") != null ) {
							Map<String, Object> parametros = new HashMap<>();
							parametros.put("RUC", empresa.getRuc());
							parametros.put("NUM_AUT", dat.get("claveacceso").toString());
							parametros.put("TIPO_EMISION", "NORMAL");
							parametros.put("CLAVE_ACC", dat.get("claveacceso").toString());
							parametros.put("RAZON_SOCIAL", empresa.getRazonSocial());
							parametros.put("DIR_MATRIZ", empresa.getDireccion());
							parametros.put("LLEVA_CONTABILIDAD", "NO");
							parametros.put("RS_COMPRADOR", dat.get("cliente").toString());
							parametros.put("RUC_COMPRADOR", dat.get("cedula").toString());
							parametros.put("FECHA_EMISION", dat.get("fecha").toString());
							parametros.put("NUM_FACT", dat.get("factura").toString());
							parametros.put("AMBIENTE", "PRODUCCIÓN");
							parametros.put("REGIMEN_RIMPE", "RIMPE - EMPRENDEDOR");
							
							parametros.put("DIR_CLIENTE", dat.get("direccion")==null?"":dat.get("direccion").toString() );
							parametros.put("EMAIL_CLIENTE", dat.get("email")==null?"":dat.get("email").toString() );
							parametros.put("TELF_CLIENTE", dat.get("telefono")==null?"":dat.get("telefono").toString() );
							parametros.put("NUMERO_MEDIDOR", dat.get("numeromedidor")==null?"":dat.get("numeromedidor").toString() );
							
							parametros.put("SUBTOTAL_12", "0.00");
							parametros.put("SUBTOTAL_0", dat.get("subtotal").toString());
							parametros.put("SUBTOTAL_NO_IVA", "0.00");
							parametros.put("SUBTOTAL_IVA", "0.00");
							parametros.put("SUBTOTAL_SIN_IMPUESTOS", dat.get("subtotal").toString());
							parametros.put("TOTAL_DESCUENTO", dat.get("descuento").toString());
							parametros.put("ICE", "0.00");
							parametros.put("IVA12", "0.00");
							parametros.put("DEVOLUCION_IVA", "0.00");
							parametros.put("ISBPNR", "0.00");
							parametros.put("PROPINA", "0.00");
							parametros.put("VALOR_TOTAL", dat.get("totalpagar").toString());
							parametros.put("TOTAL_SIN_SUBSIDIO", "0.00");
							parametros.put("AHORRO_SUBSIDIO", "0.00");
							
							List<Map<String, Object>> detalles = (List<Map<String, Object>>) dat.get("detalle");
							
							List<Map<String, Object>> dataFinal = new ArrayList<>();
							
							for(Map<String, Object> det : detalles ) {
								Map<String, Object> detalle = new HashMap<>();
								detalle.put("codigoPrincipal", det.get("codigo").toString());
								detalle.put("codigoAuxiliar", "");
								detalle.put("cantidad", det.get("cantidad").toString());
								detalle.put("descripcion", det.get("descripcion").toString().toUpperCase());
								detalle.put("precioUnitario", det.get("valorunitario").toString());
								detalle.put("descuento", "0.00");
								detalle.put("precioTotalSinImpuesto", det.get("subtotal").toString());
								dataFinal.add(detalle);
							}
							
							JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataFinal, false);
							FuncionesGenerales genera = new FuncionesGenerales();
							byte[] bytes = genera.generarReportePDF("factura", parametros, source);
							String asunto = "Factura mes " + dat.get("mes").toString() + " del año " + dat.get("anio").toString() + " - JAAPZ";
							String mensaje = "Estimado/a: " + dat.get("cliente").toString() + "\n";
							mensaje = mensaje + "Se ha generado su factura electrónica. \n";
							mensaje = mensaje + "No: " + dat.get("factura").toString() + ". \n";
							mensaje = mensaje + "Fecha de emisión: " + dat.get("fecha").toString() + ". \n";
							mensaje = mensaje + "Total mensual: " + dat.get("totalpagar").toString() + ". \n\n\n";
							mensaje = mensaje + "El documento pdf y xml de su factura se encuentra adjunto a este correo. \n\n";
							
							mensaje = mensaje + "Atentamente: \n";
							mensaje = mensaje + "La directiva \n";
							mensaje = mensaje + "JAAP Zapotal \n";
							System.out.println("Correo a enviar: " + dat.get("email").toString());
							System.out.println("clave acceso: " + conf.getRutaautorizados() + dat.get("claveacceso").toString() + ".xml");
							//agregar informacion para ver la factura desde el sistema
							//this.enviarcorreo(dat.get("email").toString(), asunto, mensaje, bytes, conf.getRutaautorizados() + dat.get("claveacceso").toString() + ".xml", dat.get("claveacceso").toString() + ".xml");
						}
					}
				}	
			}
			response.put("estado", "ok");
			response.put("mensaje", "Correos enviado satisfactoriamente");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.put("estado", "error");
			response.put("mensaje", "Hubo un error al enviar correo");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LinkedHashMap<String, Object>> consultarPlanillaFirmadaPorAnioMesEstado(Integer idanio, Integer idmes, String estado){
		List<LinkedHashMap<String, Object>> retorno = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> mpBus = new HashMap<>();
			mpBus.put("idmes", idmes);
			mpBus.put("idanio", idanio);
			mpBus.put("estado", estado);
			String jsonBus = objectMapper.writeValueAsString(mpBus);
			String data = this.planillaDAO.procesoPlanila(jsonBus, "CPFIE");
			if(data != null) {
				retorno = objectMapper.readValue(data, List.class);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retorno;
	}
	
}