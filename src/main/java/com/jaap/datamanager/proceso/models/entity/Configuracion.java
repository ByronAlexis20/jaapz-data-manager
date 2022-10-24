package com.jaap.datamanager.proceso.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_configuracion")
public class Configuracion implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int4")
	private Integer id;

	@Column(name = "numerodecimales")
	private Integer numeroDecimales;

	@Column(name = "tarifaminima")
	private Integer tarifaMinima;

	@Column(name = "ordencorte")
	private Integer ordenCorte;

	@Column(name = "preciom3")
	private Integer preciom3;

	@Column(name = "estado")
	private String estado;

	public Configuracion() {
		super();
	}

	public Configuracion(Integer id, Integer numeroDecimales, Integer tarifaMinima, Integer ordenCorte,
			Integer preciom3, String estado) {
		super();
		this.id = id;
		this.numeroDecimales = numeroDecimales;
		this.tarifaMinima = tarifaMinima;
		this.ordenCorte = ordenCorte;
		this.preciom3 = preciom3;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumeroDecimales() {
		return numeroDecimales;
	}

	public void setNumeroDecimales(Integer numeroDecimales) {
		this.numeroDecimales = numeroDecimales;
	}

	public Integer getTarifaMinima() {
		return tarifaMinima;
	}

	public void setTarifaMinima(Integer tarifaMinima) {
		this.tarifaMinima = tarifaMinima;
	}

	public Integer getOrdenCorte() {
		return ordenCorte;
	}

	public void setOrdenCorte(Integer ordenCorte) {
		this.ordenCorte = ordenCorte;
	}

	public Integer getPreciom3() {
		return preciom3;
	}

	public void setPreciom3(Integer preciom3) {
		this.preciom3 = preciom3;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
