package com.jaap.datamanager.proceso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IAnioDAO;
import com.jaap.datamanager.proceso.models.entity.Anio;
import com.jaap.datamanager.proceso.service.IAnioService;

@Service
public class AnioImpl implements IAnioService {

	@Autowired
	private IAnioDAO anioDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Anio> aniosActivos() {
		return this.anioDAO.buscarAnios();
	}

	@Override
	@Transactional(readOnly = true)
	public Anio buscarPorId(Integer id) {
		return this.anioDAO.buscarAnioPorId(id);
	}

	@Override
	@Transactional
	public Anio grabarAnio(Anio anio) {
		return this.anioDAO.save(anio);
	}

}
