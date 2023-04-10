package com.jaap.datamanager.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.jaap.datamanager.proceso.models.entity.Configuracion;
import com.jaap.datamanager.seguridad.models.entity.Empresa;

public class GenerarFacturaXml {

	@SuppressWarnings("unchecked")
	public static void generarXmlFactura(Map<String, Object> datosFactura) throws IOException {
		// obtener datos
		Empresa empresa = (Empresa) datosFactura.get("empresa");
		Map<String, Object> dataFactura = (Map<String, Object>) datosFactura.get("factura");
		Configuracion configuracion = (Configuracion) datosFactura.get("configuracion");
		
		String rutaGenerados = configuracion.getRutagenerados() + datosFactura.get("claveacceso").toString() + ".xml";
		
		OutputStream out = new FileOutputStream( rutaGenerados );
		
		System.out.println("ruta generado -->> " + rutaGenerados);
		
		Document doc = new Document();
		Element factura = new Element("factura");
		factura.setAttribute("id", "comprobante");
		factura.setAttribute("version", "1.0.0");
		
		doc.setRootElement(factura);

		Element infoTributaria = new Element("infoTributaria");
		
		Element ambiente = new Element("ambiente");
		ambiente.setText( Constantes.ambiente );
		infoTributaria.addContent(ambiente);
		
		Element tipoEmision = new Element("tipoEmision");
		tipoEmision.setText( Constantes.tipoEmision );
		infoTributaria.addContent(tipoEmision);
		
		Element razonSocial = new Element("razonSocial");
		razonSocial.setText( empresa.getRazonSocial() );
		infoTributaria.addContent(razonSocial);
		
		/*Element nombreComercial = new Element("nombreComercial");
		nombreComercial.setText("Empresa agua");
		infoTributaria.addContent(nombreComercial);*/
		
		Element ruc = new Element("ruc");
		ruc.setText( empresa.getRuc() );
		infoTributaria.addContent(ruc);
		
		Element claveAcceso = new Element("claveAcceso");
		claveAcceso.setText(datosFactura.get("claveacceso").toString());
		infoTributaria.addContent(claveAcceso);
		
		Element codDoc = new Element("codDoc");
		codDoc.setText(Constantes.codigoDocumento);
		infoTributaria.addContent(codDoc);
		
		Element estab = new Element("estab");
		estab.setText( empresa.getNumeroestablecimiento() );
		infoTributaria.addContent(estab);
		
		Element ptoEmi = new Element("ptoEmi");
		ptoEmi.setText( empresa.getNumeroestablecimiento());
		infoTributaria.addContent(ptoEmi);
		
		Element secuencial = new Element("secuencial");
		secuencial.setText( datosFactura.get("secuencial").toString() );
		infoTributaria.addContent(secuencial);
		
		Element dirMatriz = new Element("dirMatriz");
		dirMatriz.setText( empresa.getDireccion() );
		infoTributaria.addContent(dirMatriz);
		
		doc.getRootElement().addContent(infoTributaria);

		Element infoFactura = new Element("infoFactura");

		Element fechaEmision = new Element("fechaEmision");
		fechaEmision.setText( dataFactura.get("fecha").toString() );
		infoFactura.addContent(fechaEmision);
		
		Element dirEstablecimiento = new Element("dirEstablecimiento");
		dirEstablecimiento.setText("SANTA ELENA / SANTA ELENA / CHANDUY / PRINCIPAL S/N");
		infoFactura.addContent(dirEstablecimiento);
		
		Element obligadoContabilidad = new Element("obligadoContabilidad");
		obligadoContabilidad.setText("NO");
		infoFactura.addContent(obligadoContabilidad);
		
		Element tipoIdentificacionComprador = new Element("tipoIdentificacionComprador");
		if( dataFactura.get("identificacion").toString().length() == 10 ) {
			tipoIdentificacionComprador.setText( Constantes.tipoIdentificacionCompradorCedula );
		}else {
			if( dataFactura.get("identificacion").toString().length() == 13 ) {
				tipoIdentificacionComprador.setText( Constantes.tipoIdentificacionCompradorRuc );
			}
		}
		infoFactura.addContent(tipoIdentificacionComprador);
				
		Element razonSocialComprador = new Element("razonSocialComprador");
		razonSocialComprador.setText( dataFactura.get("cliente").toString() );
		infoFactura.addContent(razonSocialComprador);
		
		Element identificacionComprador = new Element("identificacionComprador");
		identificacionComprador.setText( dataFactura.get("identificacion").toString() );
		infoFactura.addContent(identificacionComprador);
		
		/*Element direccionComprador = new Element("direccionComprador");
		direccionComprador.setText("2400027757001");
		infoFactura.addContent(direccionComprador);*/
		
		Element totalSinImpuestos = new Element("totalSinImpuestos");
		totalSinImpuestos.setText( dataFactura.get("totalpagar").toString() );
		infoFactura.addContent(totalSinImpuestos);
		System.out.println("TOTAL DESCUENTO: " + dataFactura.get("descuento").toString());
		Element totalDescuento = new Element("totalDescuento");
		totalDescuento.setText( dataFactura.get("descuento").toString() );
		infoFactura.addContent(totalDescuento);
		
		Element totalConImpuesto = new Element("totalConImpuestos");
		
		Element totalImpuesto = new Element("totalImpuesto");
		
		Element codigo = new Element("codigo");
		codigo.setText( Constantes.codigoImpuestoIva );
		totalImpuesto.addContent(codigo);
		
		Element codigoPorcentaje = new Element("codigoPorcentaje");
		codigoPorcentaje.setText("0");
		totalImpuesto.addContent(codigoPorcentaje);
		
		Element baseImponible = new Element("baseImponible");
		baseImponible.setText( dataFactura.get("totalpagar").toString() );
		totalImpuesto.addContent(baseImponible);
		
		Element tarifa = new Element("tarifa");
		tarifa.setText("0.00");
		totalImpuesto.addContent(tarifa);
		
		Element valor = new Element("valor");
		valor.setText("0.00");
		totalImpuesto.addContent(valor);
		
		totalConImpuesto.addContent(totalImpuesto);
		
		infoFactura.addContent(totalConImpuesto);
		
		Element propina = new Element("propina");
		propina.setText("0.00");
		infoFactura.addContent(propina);
		
		Element importeTotal = new Element("importeTotal");
		importeTotal.setText( dataFactura.get("totalpagar").toString() );
		infoFactura.addContent(importeTotal);
		
		Element moneda = new Element("moneda");
		moneda.setText("DOLAR");
		infoFactura.addContent(moneda);
		
		Element pagos = new Element("pagos");
		
		Element pago = new Element("pago");
		
		Element formaPago = new Element("formaPago");
		formaPago.setText( Constantes.codigoOtrosUtilizacionSistemaFinanciero );
		pago.addContent(formaPago);
		
		Element total = new Element("total");
		total.setText( dataFactura.get("totalpagar").toString() );
		pago.addContent(total);
		
		pagos.addContent(pago);
		
		infoFactura.addContent(pagos);
		
		doc.getRootElement().addContent(infoFactura);
		
		//detalles de la factura
		List<Map<String, Object>> detalleFactura = (List<Map<String, Object>>) dataFactura.get("detalles");

		Element detalles = new Element("detalles");
		Integer item = 0;
		for( Map<String, Object> det : detalleFactura ) {
			Element detalle = new Element("detalle");
			
			Element codigoPrincipal = new Element("codigoPrincipal");
			codigoPrincipal.setText("0001");
			detalle.addContent(codigoPrincipal);
			
			Element descripcion = new Element("descripcion");
			descripcion.setText( det.get("descripcion").toString().toUpperCase() );
			detalle.addContent(descripcion);
			
			Element cantidad = new Element("cantidad");
			cantidad.setText( det.get("cantidad").toString() );
			detalle.addContent(cantidad);
			
			Element precioUnitario = new Element("precioUnitario");
			precioUnitario.setText( det.get("valorunitario").toString() );
			detalle.addContent(precioUnitario);
			
			Element descuento = new Element("descuento");
			if( item == 0 ) {
				descuento.setText( dataFactura.get("descuento").toString() );
				System.out.println("DESCUENTO: " + dataFactura.get("descuento").toString());
			}else {
				descuento.setText( "0" );
				System.out.println("NO TIENE DESCUENTO");
			}
			detalle.addContent(descuento);
			
			Element precioTotalSinImpuesto = new Element("precioTotalSinImpuesto");
			if( item == 0 ) {
				System.out.println("Subtotal: " + Double.parseDouble( det.get("subtotal").toString() ));
				System.out.println("Descuento" + Double.parseDouble( dataFactura.get("descuento").toString() ));
				System.out.println("Total: " + String.valueOf( Double.parseDouble( det.get("subtotal").toString() ) -  Double.parseDouble( dataFactura.get("descuento").toString() ) ));
				precioTotalSinImpuesto.setText( String.valueOf( Double.parseDouble( det.get("subtotal").toString() ) -  Double.parseDouble( dataFactura.get("descuento").toString() ) ));
			}else {
				precioTotalSinImpuesto.setText( det.get("subtotal").toString() );
			}
			detalle.addContent(precioTotalSinImpuesto);
			
			Element impuestos = new Element("impuestos");
			
			Element impuesto = new Element("impuesto");
			
			Element codigoImp = new Element("codigo");
			codigoImp.setText( Constantes.codigoImpuestoIva );
			impuesto.addContent(codigoImp);
			
			Element codigoPorcentajeImp = new Element("codigoPorcentaje");
			codigoPorcentajeImp.setText("0");
			impuesto.addContent(codigoPorcentajeImp);
			
			Element tarifaImp = new Element("tarifa");
			tarifaImp.setText("0.00");
			impuesto.addContent(tarifaImp);
			
			Element baseImponibleImp = new Element("baseImponible");
			if( item == 0 ) {
				baseImponibleImp.setText( String.valueOf( Double.parseDouble( det.get("subtotal").toString() ) -  Double.parseDouble( dataFactura.get("descuento").toString() ) ));
			}else {
				baseImponibleImp.setText( det.get("subtotal").toString() );
			}
			impuesto.addContent(baseImponibleImp);
			
			Element valorImp = new Element("valor");
			valorImp.setText("0.00");
			impuesto.addContent(valorImp);
			
			impuestos.addContent( impuesto );
			
			detalle.addContent( impuestos );
			
			detalles.addContent(detalle);
			item ++;
		}
		
		doc.getRootElement().addContent(detalles);
		XMLOutputter xmlOutputter = new XMLOutputter();
		// pretty print
		xmlOutputter.setFormat(Format.getPrettyFormat());
		xmlOutputter.output(doc, out);
	}

}
