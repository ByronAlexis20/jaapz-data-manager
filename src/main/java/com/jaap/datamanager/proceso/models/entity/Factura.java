package com.jaap.datamanager.proceso.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_factura")
public class Factura implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente cliente;

	@Column(name = "numeroautorizacion")
	private String numeroAutorizacion;

	@Column(name = "numerofactura")
	private String numeroFactura;

	@Column(name = "xmlsinfirmar")
	private String xmlsinFirmar;

	@Column(name = "xmlfirmado")
	private String xmlFirmado;

	@Column(name = "estadoenvio")
	private String estadoEnvio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "totalpagar")
	private Double totalPagar;

	@Column(name = "totalletras")
	private String totalLetras;

	@Column(name = "usuariocrea")
	private String usuarioCrea;

	@Column(name = "estado")
	private String estado;

	public Factura() {
		super();
	}

	public Factura(Long id, Cliente cliente, String numeroAutorizacion, String numeroFactura, String xmlsinFirmar,
			String xmlFirmado, String estadoEnvio, Date fecha, Double totalPagar, String totalLetras,
			String usuarioCrea, String estado) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.numeroAutorizacion = numeroAutorizacion;
		this.numeroFactura = numeroFactura;
		this.xmlsinFirmar = xmlsinFirmar;
		this.xmlFirmado = xmlFirmado;
		this.estadoEnvio = estadoEnvio;
		this.fecha = fecha;
		this.totalPagar = totalPagar;
		this.totalLetras = totalLetras;
		this.usuarioCrea = usuarioCrea;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getXmlsinFirmar() {
		return xmlsinFirmar;
	}

	public void setXmlsinFirmar(String xmlsinFirmar) {
		this.xmlsinFirmar = xmlsinFirmar;
	}

	public String getXmlFirmado() {
		return xmlFirmado;
	}

	public void setXmlFirmado(String xmlFirmado) {
		this.xmlFirmado = xmlFirmado;
	}

	public String getEstadoEnvio() {
		return estadoEnvio;
	}

	public void setEstadoEnvio(String estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(Double totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getTotalLetras() {
		return totalLetras;
	}

	public void setTotalLetras(String totalLetras) {
		this.totalLetras = totalLetras;
	}

	public String getUsuarioCrea() {
		return usuarioCrea;
	}

	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
