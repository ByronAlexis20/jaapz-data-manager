package com.jaap.datamanager.seguridad.models.entity;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jaap.datamanager.proceso.models.entity.Cliente;

@Entity
@Table(name = "seg_usuario")
public class Usuario implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int4")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "idperfil")
	private Perfil perfil;

	@Column(name = "cedula")
	private String cedula;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "clave")
	private String clave;

	@Lob
	@Column(name = "foto")
	private byte[] foto;

	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente cliente;

	@Column(name = "estado")
	private String estado;

	public Usuario() {
		super();
	}

	public Usuario(Integer id, Perfil perfil, String cedula, String nombres, String apellidos, String direccion,
			String telefono, String cargo, String usuario, String clave, byte[] foto, Cliente cliente, String estado) {
		super();
		this.id = id;
		this.perfil = perfil;
		this.cedula = cedula;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cargo = cargo;
		this.usuario = usuario;
		this.clave = clave;
		this.foto = foto;
		this.cliente = cliente;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", perfil=" + perfil + ", cedula=" + cedula + ", nombres=" + nombres
				+ ", apellidos=" + apellidos + ", direccion=" + direccion + ", telefono=" + telefono + ", cargo="
				+ cargo + ", usuario=" + usuario + ", clave=" + clave + ", foto=" + Arrays.toString(foto) + ", cliente="
				+ cliente + ", estado=" + estado + "]";
	}

}
