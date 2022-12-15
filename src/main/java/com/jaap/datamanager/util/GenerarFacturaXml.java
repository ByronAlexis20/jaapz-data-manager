package com.jaap.datamanager.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerarFacturaXml {

	public Document inicializarDocumento() throws ParserConfigurationException{
        Document documento;
        // Creamos los objectos de creacion de Documentos XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor = docFactory.newDocumentBuilder();
        
        documento = constructor.newDocument();
        
        return documento;        
    }
	
    public void escribirArchivo(Document documento, String fileName) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Archivo donde almacenaremos el XML
        File archivo = new File(fileName);

        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual almacena en el archivo indicado
        StreamResult result = new StreamResult(archivo);
        // Transformamos de ña fuente DOM a el resultado, lo que almacena todo en el archivo
        transformer.transform(source, result);
    }
	
    @SuppressWarnings("unchecked")
	public Document crearDocumento( Map<String, Object> params ) throws ParserConfigurationException {
        Document documento = this.inicializarDocumento();
        
        // Creamos el elemento principal
        Element factura = documento.createElement("factura");
        factura.setAttribute("id", "comprobante");
        factura.setAttribute("version", "1.0.0");
        // Hacemos el elemento entrada descender directo del nodo XML principal
        documento.appendChild(factura);
        
        //Creamos el primer elemento de factura infoTributaria
        //********************************************* INFORMACION TRIBUTARIA **********************************************************
        Map<String, Object> infoTrib = (Map<String, Object>) params.get("informaciontributaria");
        Element infoTributaria = documento.createElement("infoTributaria");
        
        Element ambiente = documento.createElement( "ambiente" );
        ambiente.setTextContent( infoTrib.get("ambiente").toString() );
        infoTributaria.appendChild(ambiente);
        
        Element tipoemision = documento.createElement( "tipoEmision" );
        tipoemision.setTextContent( infoTrib.get("tipoemision").toString() );
        infoTributaria.appendChild(tipoemision);
        
        Element razonsocial = documento.createElement( "razonSocial" );
        razonsocial.setTextContent( infoTrib.get("razonsocial").toString() );
        infoTributaria.appendChild(razonsocial);
        
        Element nombrecomercial = documento.createElement( "nombreComercial" );
        nombrecomercial.setTextContent( infoTrib.get("nombrecomercial").toString() );
        infoTributaria.appendChild(nombrecomercial);
        
        Element ruc = documento.createElement( "ruc" );
        ruc.setTextContent( infoTrib.get("ruc").toString() );
        infoTributaria.appendChild(ruc);
        
        Element claveacceso = documento.createElement( "claveAcceso" );
        claveacceso.setTextContent( infoTrib.get("claveacceso").toString() );
        infoTributaria.appendChild(claveacceso);
        
        Element codigodocumento = documento.createElement( "codDoc" );
        codigodocumento.setTextContent( infoTrib.get("codigodocumento").toString() );
        infoTributaria.appendChild(codigodocumento);
        
        Element establecimiento = documento.createElement( "estab" );
        establecimiento.setTextContent( infoTrib.get("establecimiento").toString() );
        infoTributaria.appendChild(establecimiento);
        
        Element puntoemision = documento.createElement( "ptoEmi" );
        puntoemision.setTextContent( infoTrib.get("puntoemision").toString() );
        infoTributaria.appendChild(puntoemision);
        
        Element secuencial = documento.createElement( "secuencial" );
        secuencial.setTextContent( infoTrib.get("secuencial").toString() );
        infoTributaria.appendChild(secuencial);
        
        Element direccionJunta = documento.createElement( "dirMatriz" );
        direccionJunta.setTextContent( infoTrib.get("direccionjunta").toString() );
        infoTributaria.appendChild(direccionJunta);
        
        factura.appendChild(infoTributaria);
        //****************************************************************************************************************************
        //********************************************* INFORMACION FACTURA **********************************************************
        Map<String, Object> infoFac = (Map<String, Object>) params.get("informacionfactura");
        Element infoFactura = documento.createElement("infoTributaria");
        
        Element fechaEmision = documento.createElement( "fechaEmision" );
        fechaEmision.setTextContent( infoFac.get("fechaemision").toString() );
        infoFactura.appendChild(fechaEmision);
        
        Element direccionEstablecimiento = documento.createElement( "dirEstablecimiento" );
        direccionEstablecimiento.setTextContent( infoFac.get("direccionestablecimiento").toString() );
        infoFactura.appendChild(direccionEstablecimiento);
        
        Element obligadoContabilidad = documento.createElement( "obligadoContabilidad" );
        obligadoContabilidad.setTextContent( infoFac.get("obligadocontabilidad").toString() );
        infoFactura.appendChild(obligadoContabilidad);
        
        Element tipoIdentificacionComprador = documento.createElement( "tipoIdentificacionComprador" );
        tipoIdentificacionComprador.setTextContent( infoFac.get("tipoidentificacioncomprador").toString() );
        infoFactura.appendChild(tipoIdentificacionComprador);
        
        Element razonSocialComprador = documento.createElement( "razonSocialComprador" );
        razonSocialComprador.setTextContent( infoFac.get("razonsocialcomprador").toString() );
        infoFactura.appendChild(razonSocialComprador);
        
        Element identificacionComprador = documento.createElement( "identificacionComprador" );
        identificacionComprador.setTextContent( infoFac.get("identificacioncomprador").toString() );
        infoFactura.appendChild(identificacionComprador);
        
        Element totalSinImpuestos = documento.createElement( "totalSinImpuestos" );
        totalSinImpuestos.setTextContent( infoFac.get("totalsinimpuestos").toString() );
        infoFactura.appendChild(totalSinImpuestos);
        
        Element totalDescuento = documento.createElement( "totalDescuento" );
        totalDescuento.setTextContent( infoFac.get("totaldescuento").toString() );
        infoFactura.appendChild(totalDescuento);
        
        
        
        factura.appendChild(infoFactura);
        return documento;
    }
}
