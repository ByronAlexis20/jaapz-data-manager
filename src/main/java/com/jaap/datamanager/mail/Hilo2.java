package com.jaap.datamanager.mail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.jaap.datamanager.util.Constantes;

public class Hilo2 {

	private boolean continuar = true;
	public static  boolean  envioExito= false;
	private String adjunto;
	private String[] adjuntos;
	private String[] destinatarios;
	private int servidor;
	private String destinatario;
	private String asunto;
	private String mensaje;
	private String cliente;
	private String medidor;
	private byte[] archivoAdjunto;
	private String rutaXml;
	private String nombreXml;
	
	public Hilo2(String adjunto, String[] adjuntos, String[] destinatarios, int servidor, String destinatario,
			String asunto, String mensaje,String cliente, String medidor) {
		this.adjunto = adjunto;
		this.adjuntos = adjuntos;
		this.destinatarios = destinatarios;
		this.servidor = servidor;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.cliente = cliente;
		this.medidor = medidor;
	}
	public Hilo2(String adjunto, byte[] archivoAdjunto, String[] destinatarios, int servidor, String destinatario,
			String asunto, String mensaje, String rutaXml, String nombreXml) {
		this.adjunto = adjunto;
		this.archivoAdjunto = archivoAdjunto;
		this.destinatarios = destinatarios;
		this.servidor = servidor;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.rutaXml = rutaXml;
		this.nombreXml = nombreXml;
	}
	public void detenElHilo(){
		this.continuar = false;
	}
	int i = 0;
	public void enviarCorreo() {
		while (this.continuar) {
			i = i + 1;
			//adjunto es la direccion del archivo adjunto
			if (this.adjunto.isEmpty()) {
				EnviarMail propio = new EnviarMail(Constantes.CORREO_ORIGEN, Constantes.CONTRASENIA_ORIGEN, 
						this.destinatarios, this.asunto, this.mensaje, this.servidor);
				try {
					propio.enviar();
				}
				catch (MessagingException ex){
					JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
					Logger.getLogger(Hilo2.class.getName()).log(Level.SEVERE, null, ex);
					detenElHilo();
				}
			}
			else {
				EnviarMailComplejo propio = new EnviarMailComplejo(Constantes.CORREO_ORIGEN, 
						Constantes.CONTRASENIA_ORIGEN, this.destinatarios, this.asunto, this.mensaje, this.archivoAdjunto, this.rutaXml, this.nombreXml, this.servidor);
				try {
					propio.Enviar();
				}
				catch (MessagingException ex){
					JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
					Logger.getLogger(Hilo2.class.getName()).log(Level.SEVERE, null, ex);
					detenElHilo();
				}
			}
			detenElHilo();
			System.out.println(i);
		}
	}
	public void enviarCorreoSolicitud() {
		while (this.continuar) {
			i = i + 1;
			//adjunto es la direccion del archivo adjunto
			
				EnviarMailComplejo propio = new EnviarMailComplejo(Constantes.CORREO_ORIGEN, Constantes.CONTRASENIA_ORIGEN, this.destinatarios, this.asunto, this.mensaje, this.archivoAdjunto, this.rutaXml, this.nombreXml, this.servidor);
				try {
					propio.Enviar();
				}
				catch (MessagingException ex){
					JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
					Logger.getLogger(Hilo2.class.getName()).log(Level.SEVERE, null, ex);
					detenElHilo();
				}
			
			detenElHilo();
			System.out.println(i);
		}
	}
	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String[] getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(String[] adjuntos) {
		this.adjuntos = adjuntos;
	}

	public String[] getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String[] destinatarios) {
		this.destinatarios = destinatarios;
	}

	public int getServidor() {
		return servidor;
	}

	public void setServidor(int servidor) {
		this.servidor = servidor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getMedidor() {
		return medidor;
	}

	public void setMedidor(String medidor) {
		this.medidor = medidor;
	}
	public byte[] getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(byte[] archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	public String getRutaXml() {
		return rutaXml;
	}
	public void setRutaXml(String rutaXml) {
		this.rutaXml = rutaXml;
	}
	public String getNombreXml() {
		return nombreXml;
	}
	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}
	
}
