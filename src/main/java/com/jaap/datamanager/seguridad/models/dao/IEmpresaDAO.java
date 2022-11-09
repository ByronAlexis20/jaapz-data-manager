package com.jaap.datamanager.seguridad.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.seguridad.models.entity.Empresa;

public interface IEmpresaDAO extends CrudRepository<Empresa, Integer> {

	@Query("Select e from Empresa e where e.estado = 'A'")
	public Empresa buscarEmpresa();
	
	@Query(value = "select cast(json_agg(t) as text) from ("
			+ "select e.razonsocial as nombrejunta, 'R.U.C. ' || e.ruc as ruc, e.direccion, 'TELF. ' || e.telefono as telefono, 'DE ZAPOTAL' as lugar "
			+ "from seg_empresa e where e.estado = 'A' "
			+ ") t ;", nativeQuery = true)
	public String consultarDatosEmpresa();
	
}
