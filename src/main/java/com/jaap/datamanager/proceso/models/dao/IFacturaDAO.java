package com.jaap.datamanager.proceso.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Factura;

public interface IFacturaDAO extends CrudRepository<Factura, Integer> {

	@Query(value = "select public.fun_grabarfactura( ?1, ?2, ?3 );", nativeQuery = true)
	public Integer grabarFactura( String cabecera, String detalle, String planillas );
	
	@Query(value = "select public.fun_consultar_factura( ?1 );", nativeQuery = true)
	public String consultarFactura( Integer idfactura );
	
	@Query(value = "select public.fun_consultar_facturas_clientes( ?1 );", nativeQuery = true)
	public String consultarFacturasCliente( Integer idfactura );
	
	@Query(value = "select public.fun_eliminar_factura_por_id( ?1 );", nativeQuery = true)
	public Integer eliminarFacturaPorId(Integer id);
	
	@Query(value = "select public.fun_reporte_recaudacion_diaria( ?1, ?2, ?3 );", nativeQuery = true)
	public String consultarReporteRecaudacionDiaria( Integer dia, Integer mes, Integer anio );
	
	@Query(value = "select public.fun_consultar_datos_factura( ?1 );", nativeQuery = true)
	public String consultarDatosFactura( Integer id );
	
	@Query(value = "select public.fun_actualizarfacturaclaveacceso( ?1, ?2 );", nativeQuery = true)
	public Integer actualizarClaveAcceso( Integer id, String claveacceso );
}
