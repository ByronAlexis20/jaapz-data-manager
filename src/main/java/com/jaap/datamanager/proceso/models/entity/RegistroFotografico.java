package com.jaap.datamanager.proceso.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_registro_fotografico")
public class RegistroFotografico implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int8")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idplanilladetalle")
	private PlanillaDetalle planillaDetalle;

	@Lob
	@Column(name = "foto")
	private byte[] foto;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "estado")
	private String estado;

	public RegistroFotografico() {
		super();
	}

	public RegistroFotografico(Long id, PlanillaDetalle planillaDetalle, byte[] foto, String observacion,
			String estado) {
		super();
		this.id = id;
		this.planillaDetalle = planillaDetalle;
		this.foto = foto;
		this.observacion = observacion;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlanillaDetalle getPlanillaDetalle() {
		return planillaDetalle;
	}

	public void setPlanillaDetalle(PlanillaDetalle planillaDetalle) {
		this.planillaDetalle = planillaDetalle;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
