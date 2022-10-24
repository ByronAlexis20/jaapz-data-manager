package com.jaap.datamanager.springSecurity.services;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaap.datamanager.seguridad.models.entity.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private Integer codUsuario;
	private String username;
	@JsonIgnore
	private String password;
	private String nombre;
	private String apellido;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(Integer codUsuario, String username, String password, String nombre, String apellido,
			Collection<? extends GrantedAuthority> authorities) {
		this.codUsuario = codUsuario;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Usuario user) {
		List<GrantedAuthority> authorities = null;
		return new UserDetailsImpl(
									user.getId(), 
									user.getUsuario(), 
									user.getClave(),
									user.getNombres(),
									user.getApellidos(),
									authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	


	public Integer getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(codUsuario, user.codUsuario);
	}

	private static final long serialVersionUID = 1L;
}