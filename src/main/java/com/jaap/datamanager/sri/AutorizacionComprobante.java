package com.jaap.datamanager.sri;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jdom2.input.SAXBuilder;

import com.jaap.datamanager.util.Constantes;

public class AutorizacionComprobante {

	private static final String WSDL_PRUEBA = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";//PRUEBAS
	private static final String WSDL_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";//PRODUCCION
	
	public org.jdom2.Document autorizarComprobantes(String claveacceso) {
		String url = "";
		if( Constantes.ambiente.equals("1") ) {
			url = WSDL_PRUEBA;
		}else {
			if( Constantes.ambiente.equals("2") ) {
				url = WSDL_PRODUCCION;
			}
		}
        if( claveacceso.equals("") ){
            System.err.println("Numero de autorización no válido....");
            return null;
        }
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\">" +
                "    <soapenv:Header/>" +
                "    <soapenv:Body>" +
                "        <ec:autorizacionComprobante>" +
                "            <claveAccesoComprobante>" + claveacceso + "</claveAccesoComprobante>" +
                "        </ec:autorizacionComprobante>" +
                "    </soapenv:Body>" +
                "</soapenv:Envelope>";
        HttpURLConnection conn = null;
        URL uriLogin;
        try {
            uriLogin = new URL(url);
            conn = (HttpURLConnection) uriLogin.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");       
            //Sender POST
            DataOutputStream wr = new DataOutputStream ( conn.getOutputStream () );
            wr.writeBytes (params);
            wr.flush ();
            wr.close ();
            if (conn.getResponseCode() == conn.HTTP_OK) {
                StringBuilder responseStrBuilder = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                	inputStr = inputStr.replace("&lt;", "<").replace("&#xd;", "").replace("<comprobante>", "<comprobante><![CDATA[").replace("</comprobante>", "]]></comprobante>");
                    responseStrBuilder.append(inputStr + "\n");
                }
                String xml = responseStrBuilder.toString();
                xml = xml.replace("</autorizaciones></RespuestaAutorizacionComprobante></ns2:autorizacionComprobanteResponse></soap:Body></soap:Envelope>", "");
                xml = xml.replace("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                		+ "<soap:Body><ns2:autorizacionComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.autorizacion\">"
                		+ "<RespuestaAutorizacionComprobante><claveAccesoConsultada>" + claveacceso 
                		+ "</claveAccesoConsultada><numeroComprobantes>1</numeroComprobantes><autorizaciones>", "");
                System.out.println("xml respuesta " + xml);
                try {
                    SAXBuilder builder = new SAXBuilder();
                    InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                    org.jdom2.Document anotherDocument = builder.build( stream );
                    //System.out.println("Elemento padre: " + anotherDocument.getRootElement().getContent(0).getValue() + "\n");
                    return anotherDocument;
                    
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                
            }else{
                System.err.println("HTTP: " + conn.getResponseMessage());
                return null;
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
	}
	
}
