package com.jaap.datamanager.proceso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.proceso.models.dao.IMesDAO;
import com.jaap.datamanager.proceso.models.entity.Mes;
import com.jaap.datamanager.proceso.service.IMesService;

@Service
public class MesImpl implements IMesService {

	@Autowired
	private IMesDAO mesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Mes> mesesActivos() {
		return this.mesDAO.listaMesesActivos();
	}

}
