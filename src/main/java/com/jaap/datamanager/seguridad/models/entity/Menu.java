package com.jaap.datamanager.seguridad.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seg_menu")
public class Menu implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int4")
	private Integer id;

	@Column(name = "idpadre")
	private Integer idPadre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "vista")
	private String vista;

	@Column(name = "icono")
	private String icono;

	@Column(name = "posicion")
	private Integer posicion;

	@Column(name = "estado")
	private String estado;

	public Menu() {
		super();
	}

	public Menu(Integer id, Integer idPadre, String descripcion, String vista, String icono, Integer posicion,
			String estado) {
		super();
		this.id = id;
		this.idPadre = idPadre;
		this.descripcion = descripcion;
		this.vista = vista;
		this.icono = icono;
		this.posicion = posicion;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
