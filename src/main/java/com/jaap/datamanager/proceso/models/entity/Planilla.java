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
@Table(name = "tbl_planilla")
public class Planilla implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente cliente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name = "idanio")
	private Anio anio;

	@Column(name = "anio")
	private String descripcionAnio;

	@ManyToOne
	@JoinColumn(name = "idmes")
	private Mes mes;

	@Column(name = "mes")
	private String descripcionMes;

	@Column(name = "totalpagar")
	private Double totalPagar;

	@Column(name = "totalletras")
	private String totalLetras;

	@Column(name = "usuariocrea")
	private String usuarioCrea;

	@Column(name = "estado")
	private String estado;

	public Planilla() {
		super();
	}

	public Planilla(Long id, Cliente cliente, Date fecha, Anio anio, String descripcionAnio, Mes mes,
			String descripcionMes, Double totalPagar, String totalLetras, String usuarioCrea, String estado) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.anio = anio;
		this.descripcionAnio = descripcionAnio;
		this.mes = mes;
		this.descripcionMes = descripcionMes;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Anio getAnio() {
		return anio;
	}

	public void setAnio(Anio anio) {
		this.anio = anio;
	}

	public String getDescripcionAnio() {
		return descripcionAnio;
	}

	public void setDescripcionAnio(String descripcionAnio) {
		this.descripcionAnio = descripcionAnio;
	}

	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	public String getDescripcionMes() {
		return descripcionMes;
	}

	public void setDescripcionMes(String descripcionMes) {
		this.descripcionMes = descripcionMes;
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
