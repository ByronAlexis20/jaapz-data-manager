package com.jaap.datamanager.seguridad.models.entity;

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
@Table(name = "seg_permiso")
public class Permiso implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int4")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "idperfil")
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "idmenu")
	private Menu menu;

	@Column(name = "estado")
	private String estado;

	public Permiso() {
		super();
	}

	public Permiso(Integer id, Perfil perfil, Menu menu, String estado) {
		super();
		this.id = id;
		this.perfil = perfil;
		this.menu = menu;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
