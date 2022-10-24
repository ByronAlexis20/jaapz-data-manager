package com.jaap.datamanager.seguridad.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "seg_empresa")
public class Empresa implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int4")
	private Integer id;

	@Column(name = "ruc")
	private String ruc;

	@Column(name = "razonsocial")
	private String razonSocial;

	@Column(name = "representante")
	private String representante;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "estado")
	private String estado;

	@Column(name = "numeroestablecimiento")
	private String numeroestablecimiento;

	@Lob
	@Column(name = "logo")
	private byte[] logo;

	public Empresa() {
		super();
	}

	public Empresa(Integer id, String ruc, String razonSocial, String representante, String direccion, String telefono,
			String estado, String numeroestablecimiento, byte[] logo) {
		super();
		this.id = id;
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.representante = representante;
		this.direccion = direccion;
		this.telefono = telefono;
		this.estado = estado;
		this.numeroestablecimiento = numeroestablecimiento;
		this.logo = logo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumeroestablecimiento() {
		return numeroestablecimiento;
	}

	public void setNumeroestablecimiento(String numeroestablecimiento) {
		this.numeroestablecimiento = numeroestablecimiento;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

}
