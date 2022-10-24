	package com.jaap.datamanager.springSecurity.entity;

import java.util.List;

import com.jaap.datamanager.seguridad.models.entity.Perfil;
import com.jaap.datamanager.seguridad.models.entity.Permiso;

public class JwtRespuesta {

	private String token;
	private String type = "Bearer";
	private Integer codUsuario;
	private String identificacion;
	private String nombres;
	private String apellidos;
	private Perfil perfil;
	private List<Permiso> permisos;

	public JwtRespuesta() {
		super();
	}

	public JwtRespuesta(String token, Integer codUsuario, String identificacion, String nombres,
			String apellidos, Perfil perfil, List<Permiso> permisos) {
		super();
		this.token = token;
		this.codUsuario = codUsuario;
		this.identificacion = identificacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.perfil = perfil;
		this.permisos = permisos;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

}
