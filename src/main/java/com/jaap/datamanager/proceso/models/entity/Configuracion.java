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

	@Column(name = "rutagenerados")
	private String rutagenerados;

	@Column(name = "rutafirmados")
	private String rutafirmados;

	@Column(name = "rutaenviados")
	private String rutaenviados;

	@Column(name = "rutaautorizados")
	private String rutaautorizados;

	@Column(name = "rutanoautorizados")
	private String rutanoautorizados;

	@Column(name = "rutafirma")
	private String rutafirma;

	@Column(name = "clavefirma")
	private String clavefirma;

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

	public String getRutagenerados() {
		return rutagenerados;
	}

	public void setRutagenerados(String rutagenerados) {
		this.rutagenerados = rutagenerados;
	}

	public String getRutafirmados() {
		return rutafirmados;
	}

	public void setRutafirmados(String rutafirmados) {
		this.rutafirmados = rutafirmados;
	}

	public String getRutaenviados() {
		return rutaenviados;
	}

	public void setRutaenviados(String rutaenviados) {
		this.rutaenviados = rutaenviados;
	}

	public String getRutaautorizados() {
		return rutaautorizados;
	}

	public void setRutaautorizados(String rutaautorizados) {
		this.rutaautorizados = rutaautorizados;
	}

	public String getRutanoautorizados() {
		return rutanoautorizados;
	}

	public void setRutanoautorizados(String rutanoautorizados) {
		this.rutanoautorizados = rutanoautorizados;
	}

	public String getRutafirma() {
		return rutafirma;
	}

	public void setRutafirma(String rutafirma) {
		this.rutafirma = rutafirma;
	}

	public String getClavefirma() {
		return clavefirma;
	}

	public void setClavefirma(String clavefirma) {
		this.clavefirma = clavefirma;
	}

}
