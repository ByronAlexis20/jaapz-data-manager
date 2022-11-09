package com.jaap.datamanager.proceso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IDocumentoDAO;
import com.jaap.datamanager.proceso.models.entity.Documento;
import com.jaap.datamanager.proceso.service.IDocumentoService;

@Service
public class DocumentoImpl implements IDocumentoService {

	@Autowired
	private IDocumentoDAO documentoDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Documento> listadoDocumentos() {
		return this.documentoDAO.buscarDocumentosActivos();
	}

	@Override
	@Transactional(readOnly = true)
	public Documento obtenerPorId(Integer id) {
		return this.documentoDAO.buscarDocumentoPorId(id);
	}

	@Override
	@Transactional
	public Documento grabarDocumento(Documento doc) {
		return this.documentoDAO.save(doc);
	}

}
