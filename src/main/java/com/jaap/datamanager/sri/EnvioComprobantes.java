package com.jaap.datamanager.sri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class EnvioComprobantes {

	public Map<String, Object> enviarComprobante( Integer idplanilla, String direccion ){
		Map<String, Object> response = new HashMap<>();
		try {
			RecepcionComprobantes rec = new RecepcionComprobantes();
			File file = new File(direccion);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			
			org.jdom2.Document doc = rec.recepcionComprobantes(fileContent);
			System.out.println(doc.getRootElement().getChild("estado").getValue());
			if(doc.getRootElement().getChild("estado").getValue().equals("RECIBIDA")) {
				response.put("estado", "ok");
				response.put("estadoenvio", doc.getRootElement().getChild("estado").getValue());
				response.put("mensaje", "");
			}else {
				response.put("estado", "error");
				response.put("estadoenvio", "DEVUELTA");
				
				String mensaje = "";
				response.put("estado", "ok");
				for( Element el : doc.getRootElement().getChild("comprobantes").getChildren()) {
					for( Element me : el.getChild("mensajes").getChildren()) {
						mensaje = "Identificador: " + me.getChild("identificador").getValue() + ", Mensaje: " + me.getChild("mensaje").getValue();
						mensaje = mensaje + ", Tipo: " + me.getChild("tipo").getValue() + "\n";
					}
				}
				response.put("mensaje", mensaje);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.put("estado", "error");
			response.put("mensaje", ex.getMessage());
		}
		return response;
	}
	
	public Map<String, Object> autorizacionComprobante( String claveacceso, String rutaautorizado ){
		Map<String, Object> response = new HashMap<>();
		try {
			AutorizacionComprobante pru = new AutorizacionComprobante();
			org.jdom2.Document doc = pru.autorizarComprobantes( claveacceso );
			if( doc == null ) {
				response.put("estado", "error");
				response.put("mensaje", "Error al autorizar comprobante");
				return response;
			}
			
			if(doc.getRootElement().getChild("estado").getValue().equals("AUTORIZADO")) {
				OutputStream out = new FileOutputStream( rutaautorizado + claveacceso + ".xml" );
				XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.setFormat(Format.getPrettyFormat());
				xmlOutputter.output(doc, out);
				response.put("estado", "ok");
				response.put("mensaje", "");
				response.put("status", "AUTORIZADO");
			}else {
				String mensaje = "";
				response.put("estado", "ok");
				for( Element el : doc.getRootElement().getChild("mensajes").getChildren()) {
					mensaje = "Identificador: " + el.getChild("identificador").getValue() + ", Mensaje: " + el.getChild("mensaje").getValue();
					mensaje = mensaje + ", Informacion adicional: " + el.getChild("informacionAdicional").getValue();
					mensaje = mensaje + ", Tipo: " + el.getChild("tipo").getValue() + "\n";
				}
				response.put("mensaje", mensaje);
				response.put("status", "ERROR!");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.put("estado", "ok");
			response.put("mensaje", ex.getMessage());
		}
		return response;
	}
}
