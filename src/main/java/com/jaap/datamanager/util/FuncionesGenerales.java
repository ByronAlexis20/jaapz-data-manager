package com.jaap.datamanager.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class FuncionesGenerales {
	
	public byte[] generarReportePDF(String nomJasper, Map<String, Object> parametros, JRDataSource dataReporte) {
		byte[] bytes = null;
		JasperReport jasperReport = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
			jasperReport = (JasperReport) JRLoader.loadObject(loadJasperFile(nomJasper));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataReporte);
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public URL loadJasperFile(String nomJasper) {
		try {
			URL uri = new URL(Constantes.rutaArchivos + "reportes/" + nomJasper + ".jasper");
			return uri;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int calcularDiasTranscurridos() throws ParseException {
		LocalDate date = LocalDate.now();
		int anio = date.getYear();
		String fechaInicio = anio + "-" + "01-01";
		SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInicio = fecha.parse(fechaInicio);
		
		Date fechaactual = new Date(System.currentTimeMillis());
		
		int milisecondsByDay = 86400000;
		int dias = (int) ((fechaactual.getTime() - dateInicio.getTime()) / milisecondsByDay);
		return dias + 1;
	}
	
	public static String obtenerMesPorFechaDate(Date fecha) {
		String mes = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM");
			String mesString = formatter.format(fecha);
			switch (mesString) {
				case "01": {
					mes = "Enero";
					break;
				}
				case "02": {
					mes = "Febrero";
					break;
				}
				case "03": {
					mes = "Marzo";
					break;
				}
				case "04": {
					mes = "Abril";
					break;		
				}
				case "05": {
					mes = "Mayo";
					break;
				}
				case "06": {
					mes = "Junio";
					break;
				}
				case "07": {
					mes = "Julio";
					break;
				}
				case "08": {
					mes = "Agosto";
					break;
				}
				case "09": {
					mes = "Septiembre";
					break;
				}
				case "10": {
					mes = "Octubre";
					break;
				}
				case "11": {
					mes = "Noviembre";
					break;
				}
				case "12": {
					mes = "Diciembre";
					break;
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return mes;
	}
	
	public static String fechaString(Date fecha) {
		String fechaStr = "";
		try {
			SimpleDateFormat formatoDia = new SimpleDateFormat("dd");
			SimpleDateFormat formatoAnio = new SimpleDateFormat("yyyy");
			SimpleDateFormat formatoDiaString = new SimpleDateFormat("EEEE");
			
			String dia = formatoDia.format(fecha);
			String anio = formatoAnio.format(fecha);
			String diaStr = formatoDiaString.format(fecha);
			
			String diaEspanol = "";
			if(diaStr.toLowerCase().equals("monday")) {
				diaEspanol = "Lunes";
			}else {
				if(diaStr.toLowerCase().equals("tuesday")) {
					diaEspanol = "Martes";
				}else {
					if(diaStr.toLowerCase().equals("wednesday")) {
						diaEspanol = "Miércoles";
					}else {
						if(diaStr.toLowerCase().equals("thursday")) {
							diaEspanol = "Jueves";
						}else {
							if(diaStr.toLowerCase().equals("friday")) {
								diaEspanol = "Viernes";
							}else {
								if(diaStr.toLowerCase().equals("saturday")) {
									diaEspanol = "Sábado";
								}else {
									if(diaStr.toLowerCase().equals("sunday")) {
										diaEspanol = "Domingo";
									}else {
										diaEspanol = diaStr;
									}
										
								}
							}
						}
					}
				}
			}
			fechaStr = fechaStr + diaEspanol + ", " + dia + " de " + obtenerMesPorFechaDate(fecha) + " de " + anio;
		}catch(Exception ex) {
			
		}
		return fechaStr;
	}
	
	public String obtenerClaveAcceso(Date fechaComprobante, String numeroFactura, String ruc) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			String claveAcceso = "";
			//fecha de emision
			claveAcceso += format.format(fechaComprobante);
			//tipo de comprobante
			claveAcceso += CodigosEstandares.codigoFacturaSRI;
			//numero de ruc de la empresa
			claveAcceso += ruc;
			//tipo de ambiente
			claveAcceso += Constantes.ambiente;
			//serie + numero de comprobante
			claveAcceso += "001001" + numeroFactura;
			//codigo numerico
			claveAcceso += Constantes.cadenaCaracteresClaveAcceso;
			//tipo de emision
			claveAcceso += Constantes.tipoEmision;
			//digito verificador
			claveAcceso += String.valueOf(digitoVerificador(claveAcceso)); 
			return claveAcceso;
		}catch(Exception ex) {
			return "";
		}
	}
	
	private Integer digitoVerificador(String numero) {
		Integer digito = 0;
		String numeroInvetido = "";
		for (int x = numero.length()-1 ; x >= 0 ; x--) {
			numeroInvetido = numeroInvetido + numero.charAt(x);
		}
		char[] digits = numeroInvetido.toCharArray();
		
		int a = 2;
		Integer sumaResultado = 0;
		for(int i = 0; i < digits.length; i++) {
			Integer dig = Integer.parseInt(String.valueOf(digits[i]));
			sumaResultado = sumaResultado + (dig * a);
			if(a == 7) {
				a = 1;
			}
			a++;
        }
		Integer mod = sumaResultado % 11;
		digito = 11 - mod;
		if(digito == 11)
			digito = 0;
		else if(digito == 10)
			digito = 1;
		return digito;
	}
}
