package com.jaap.datamanager.proceso.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_rango_factura")
public class RangoFactura implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@Column(name = "numerofacturero")
	private String numeroFacturero;

	@Column(name = "rangoinicial")
	private Integer rangoInicial;

	@Column(name = "rangofinal")
	private Integer rangoFinal;

	@Column(name = "total")
	private Integer total;

	@Column(name = "restantes")
	private Integer restantes;

	@Column(name = "estado")
	private String estado;

	public RangoFactura() {
		super();
	}

	public RangoFactura(Long id, String numeroFacturero, Integer rangoInicial, Integer rangoFinal, Integer total,
			Integer restantes, String estado) {
		super();
		this.id = id;
		this.numeroFacturero = numeroFacturero;
		this.rangoInicial = rangoInicial;
		this.rangoFinal = rangoFinal;
		this.total = total;
		this.restantes = restantes;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroFacturero() {
		return numeroFacturero;
	}

	public void setNumeroFacturero(String numeroFacturero) {
		this.numeroFacturero = numeroFacturero;
	}

	public Integer getRangoInicial() {
		return rangoInicial;
	}

	public void setRangoInicial(Integer rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	public Integer getRangoFinal() {
		return rangoFinal;
	}

	public void setRangoFinal(Integer rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRestantes() {
		return restantes;
	}

	public void setRestantes(Integer restantes) {
		this.restantes = restantes;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
