package com.jaap.datamanager.proceso.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_planilla_detalle")
public class PlanillaDetalle implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idplanilla")
	private Planilla planilla;

	@ManyToOne
	@JoinColumn(name = "iddocumento")
	private Documento documento;

	@Column(name = "lecturaanterior")
	private Integer lecturaAnterior;

	@Column(name = "lecturaactual")
	private Integer lecturaActual;

	@Column(name = "consumominimo")
	private Integer consumoMinimo;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private String estado;

	public PlanillaDetalle() {
		super();
	}

	public PlanillaDetalle(Long id, Planilla planilla, Documento documento, Integer lecturaAnterior,
			Integer lecturaActual, Integer consumoMinimo, Integer cantidad, String descripcion, String estado) {
		super();
		this.id = id;
		this.planilla = planilla;
		this.documento = documento;
		this.lecturaAnterior = lecturaAnterior;
		this.lecturaActual = lecturaActual;
		this.consumoMinimo = consumoMinimo;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Planilla getPlanilla() {
		return planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Integer getLecturaAnterior() {
		return lecturaAnterior;
	}

	public void setLecturaAnterior(Integer lecturaAnterior) {
		this.lecturaAnterior = lecturaAnterior;
	}

	public Integer getLecturaActual() {
		return lecturaActual;
	}

	public void setLecturaActual(Integer lecturaActual) {
		this.lecturaActual = lecturaActual;
	}

	public Integer getConsumoMinimo() {
		return consumoMinimo;
	}

	public void setConsumoMinimo(Integer consumoMinimo) {
		this.consumoMinimo = consumoMinimo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
