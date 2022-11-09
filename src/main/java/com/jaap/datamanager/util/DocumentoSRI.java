package com.jaap.datamanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentoSRI {
	public String obtenerClaveAcceso(Date fechaComprobante, String numeroFactura, String codigoFactura, String ruc, String tipoAmbiente, String cadenaClaveAcceso, String tipoEmision) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			String claveAcceso = "";
			//fecha de emision
			claveAcceso += format.format(fechaComprobante);
			//tipo de comprobante
			claveAcceso += codigoFactura;
			//numero de ruc de la empresa
			claveAcceso += ruc;
			//tipo de ambiente
			claveAcceso += tipoAmbiente;
			//serie + numero de comprobante
			claveAcceso += numeroFactura;
			//codigo numerico
			claveAcceso += cadenaClaveAcceso;
			//tipo de emision
			claveAcceso += tipoEmision;
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