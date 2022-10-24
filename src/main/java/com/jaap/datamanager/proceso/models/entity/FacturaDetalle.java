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
@Table(name = "tbl_factura_detalle")
public class FacturaDetalle implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idfactura")
	private Factura factura;

	@ManyToOne
	@JoinColumn(name = "idplanilla")
	private Planilla planilla;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "valorunitario")
	private Double valorUnitario;

	@Column(name = "subtotal")
	private Double subtotal;

	@Column(name = "estado")
	private String estado;

	public FacturaDetalle() {
		super();
	}

	public FacturaDetalle(Long id, Factura factura, Planilla planilla, Integer cantidad, String descripcion,
			Double valorUnitario, Double subtotal, String estado) {
		super();
		this.id = id;
		this.factura = factura;
		this.planilla = planilla;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.subtotal = subtotal;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Planilla getPlanilla() {
		return planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
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

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
