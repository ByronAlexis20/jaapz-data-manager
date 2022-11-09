package com.jaap.datamanager.proceso.service;

import java.util.List;

import com.jaap.datamanager.proceso.models.entity.Documento;

public interface IDocumentoService {

	public List<Documento> listadoDocumentos();
	public Documento obtenerPorId(Integer id);
	public Documento grabarDocumento(Documento doc);
}
