package com.jaap.datamanager.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EnviarMailComplejo {

	public String miCorreo;
	public String miPassword;
	String servidorSMTP;
	String puertoEnvio;
	String[] destinatarios;
	String asunto;
	String cuerpo = null;
	public String[] archivoAdjunto;
	int servidor;
	byte[] archivo;
	private String rutaXmlAutorizado;
	private String nombreXml;

	public EnviarMailComplejo(String[] dest, String asun, String mens, byte[] archivo, int server)
	{
		this.destinatarios = dest;
		this.asunto = asun;
		this.cuerpo = mens;
		this.archivo = archivo;
		this.servidor = server;
		configurarServidor();
	}

	public EnviarMailComplejo(String usuario, String pass, String[] dest, String asun, String mens, byte[] archivo, String rutaXml, String nombre, int server){
		this(dest, asun, mens, archivo, server);
		this.miCorreo = usuario;
		this.miPassword = pass;
		this.rutaXmlAutorizado = rutaXml;
		this.nombreXml = nombre;
	}

	public final void configurarServidor(){
		if (this.servidor == 0)
			this.servidorSMTP = "smtp.gmail.com";
		if (this.servidor == 1)
			//this.servidorSMTP = "smtp.live.com";
			this.servidorSMTP = "smtp.office365.com";
		if (this.servidor == 2)
			this.servidorSMTP = "smtp.mail.yahoo.com";
	}

	public void Enviar() throws MessagingException {
		try {
			System.out.println("SERVIDOR SMTP: " + servidorSMTP);
			Properties props = null;
			props = new Properties();
			props.put("mail.smtp.host", servidorSMTP);
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", servidorSMTP);
			//props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", miCorreo);
			props.setProperty("mail.smtp.auth", "true");
			//SecurityManager security = System.getSecurityManager();
			Session session = Session.getInstance(props);
			
			BodyPart texto = new MimeBodyPart();
			texto.setText(this.cuerpo);

			BodyPart[] adjunto = new BodyPart[2];
			DataSource dataSource = new ByteArrayDataSource( archivo, "application/pdf; charset=UTF-8" );
			DataHandler dataHandler = new DataHandler(dataSource);
			
			adjunto[0] = new MimeBodyPart();
			adjunto[0].setDataHandler(dataHandler);

			adjunto[0].setFileName("Planilla.pdf");

			adjunto[1] = new MimeBodyPart();
			adjunto[1].setDataHandler(new DataHandler(new FileDataSource( new File( this.rutaXmlAutorizado) )));

			adjunto[1].setFileName( this.nombreXml );
			
			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			for (BodyPart aux : adjunto) {
				multiParte.addBodyPart(aux);
			}
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.miCorreo));
			Address[] destinos = new Address[this.destinatarios.length];
			for (int i = 0; i < destinos.length; i++) {
				destinos[i] = new InternetAddress(this.destinatarios[i]);
			}
			message.addRecipients(Message.RecipientType.TO, destinos);
			message.setSubject(this.asunto);
			message.setContent(multiParte);


			Transport t = session.getTransport("smtp");
			t.connect(miCorreo, miPassword);
			t.sendMessage(message, message.getAllRecipients());

			t.close();
		}catch(Exception ex) {
			System.out.println("error en enviar: " + ex.getMessage());
		}

	}

	private class GMailAuthenticator extends Authenticator {
		String user;
		String pw;
		public GMailAuthenticator (String username, String password) {
			super();
			this.user = username;
			this.pw = password;
		}
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pw);
		}
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getRutaXmlAutorizado() {
		return rutaXmlAutorizado;
	}

	public void setRutaXmlAutorizado(String rutaXmlAutorizado) {
		this.rutaXmlAutorizado = rutaXmlAutorizado;
	}
	
	
}
