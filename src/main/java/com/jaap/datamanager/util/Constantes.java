package com.jaap.datamanager.util;

public class Constantes {
	
	public static final String urlServidorFront = "*";
	
	public static final String mensajeErrorConsulta = "No se ha encontrado la consulta";
	public static final String mensajeNoDatos = "No se encontraron datos";
	public static final String mensajeErrorGrabar = "Error al grabar los datos";
	public static final String mensajeOkGrabar = "Datos grabados con éxito";
	
	public static String rutaArchivos = FuncionesGenerales.class.getResource("/static/").toString();
	
	//variables para la facturacion electronica
	public static final String ambiente = "1";//Pruebas
	//public static final String ambiente = "2";//Produccion
	
	public static final String tipoEmision = "1";//Emision normal
	public static final String codigoDocumento = "01";//Factura
	
	public static final String tipoIdentificacionCompradorRuc 		= "04";//ruc
	public static final String tipoIdentificacionCompradorCedula 	= "05";//cedula
	public static final String tipoIdentificacionCompradorPasaporte = "06";//cedula
	public static final String tipoIdentificacionCompradorConsFinal = "07";//cedula
	public static final String tipoIdentificacionCompradorExterior 	= "08";//cedula
	
	public static final String codigoImpuestoIva = "2";
	
	public static final String codigoFormaPagoSinSistemaFinanciero = "01";
	
	public static final String direccionXmlGenerados = "";
	public static final String direccionXmlFirmados = "";
	public static final String direccionXmlAutorizados = "";
	public static final String direccionXmlNoAutorizados = "";
	public static final String direccionXmlEnviados = "";
	
	public static String cadenaCaracteresClaveAcceso = "12345678";
}
