package com.jaap.datamanager.seguridad.service;

import java.util.List;

import com.jaap.datamanager.seguridad.models.entity.Perfil;

public interface IPerfilService {

	public List<Perfil> buscarPerfilesActivos();
	public List<Perfil> buscarTodosPerfiles();
	public Perfil buscarPorId(Integer idPerfil);
	public Perfil grabarPerfil(Perfil per);
}
