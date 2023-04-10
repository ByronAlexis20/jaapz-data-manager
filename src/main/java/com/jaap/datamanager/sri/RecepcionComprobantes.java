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
import java.util.Base64;

import org.jdom2.input.SAXBuilder;

import com.jaap.datamanager.util.Constantes;

public class RecepcionComprobantes {

	private static final String WSDL_PRUEBA = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
	private static final String WSDL_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
	
	public org.jdom2.Document recepcionComprobantes(byte[] comprobante) {
		String url = "";
		if( Constantes.ambiente.equals("1") ) {
			url = WSDL_PRUEBA;
		}else {
			if( Constantes.ambiente.equals("2") ) {
				url = WSDL_PRODUCCION;
			}
		}
		
		String s = Base64.getEncoder().encodeToString(comprobante);
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">" +
                "    <soapenv:Header/>" +
                "    <soapenv:Body>" +
                "        <ec:validarComprobante>" +
                "            <xml>" + s + "</xml>" +
                "        </ec:validarComprobante>" +
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
                    responseStrBuilder.append(inputStr );
                }
                String xml = responseStrBuilder.toString();
                
                xml = xml.replace("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:validarComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.recepcion\">", "");
                xml = xml.replace("</ns2:validarComprobanteResponse></soap:Body></soap:Envelope>", "");
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
