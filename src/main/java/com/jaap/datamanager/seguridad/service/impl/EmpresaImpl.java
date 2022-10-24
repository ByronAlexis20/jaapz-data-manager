package com.jaap.datamanager.seguridad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.seguridad.models.dao.IEmpresaDAO;
import com.jaap.datamanager.seguridad.models.entity.Empresa;
import com.jaap.datamanager.seguridad.service.IEmpresaService;

@Service
public class EmpresaImpl implements IEmpresaService {

	@Autowired
	private IEmpresaDAO empresaDAO;
	
	@Override
	@Transactional
	public Empresa grabarEmpresa(Empresa emp) {
		return this.empresaDAO.save(emp);
	}

	@Override
	@Transactional
	public Empresa buscarEmpresa() {
		return this.empresaDAO.buscarEmpresa();
	}

}
