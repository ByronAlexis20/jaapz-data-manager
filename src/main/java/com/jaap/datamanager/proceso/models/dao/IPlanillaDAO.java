package com.jaap.datamanager.proceso.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jaap.datamanager.proceso.models.entity.Planilla;

public interface IPlanillaDAO extends CrudRepository<Planilla, Integer> {

	@Query(value = "select public.fun_buscar_cliente_lecturas(?1, ?2, ?3);", nativeQuery = true)
	public String buscarClienteLecturas(Integer idanio, Integer idmes, Integer idcliente);
	
	@Query(value = "select public.fun_grabarplanilla( ?1 );", nativeQuery = true)
	public Integer grabarPlanilla(String json);
	
	@Query(value = "select public.fun_buscar_planilla_cliente( ?1 );", nativeQuery = true)
	public String consultarDatosPlanilla(Integer id);
	
	@Query(value = "select pl.id from tbl_cliente cl, tbl_planilla pl where cl.id = pl.idcliente and cl.id = ?1 and pl.idanio = ?2 and pl.idmes = ?3 and pl.estado = 'A' ", nativeQuery = true)
	public List<Object[]> consultarPlanillaPorClienteAnioMes(Integer idcliente, Integer idanio, Integer idmes);
	
	@Query(value = "select public.fun_consultar_planillas_clientes( ?1 );", nativeQuery = true)
	public String consultarPlanillasPorCliente(Integer id);
	
	@Query(value = "select public.fun_eliminar_planilla_por_id( ?1 );", nativeQuery = true)
	public Integer eliminarPlanillaPorId(Integer id);
	
	@Query(value = "select public.fun_consultar_deudas();", nativeQuery = true)
	public String consultarDeudas();
	
	@Query(value = "select public.fun_buscar_detalle_planilla( ?1 );", nativeQuery = true)
	public String consultarDetallePlanilla(Integer idplanilla);
	
	@Query(value = "select public.fun_consultar_deudas_clientes( ?1 );", nativeQuery = true)
	public String consultardeudascliente(Integer idcliente);
	
	@Query(value = "select public.fun_actualizarplanilla( ?1, ?2 );", nativeQuery = true)
	public Integer insertarDetalles(String json, String criterio);
	
	@Query(value = "select public.fun_reporte_toma_lectura();", nativeQuery = true)
	public String consultarReporteTomaLectura();
	
	@Query(value = "select public.fun_reporte_consolidado_consumo( ?1, ?2 );", nativeQuery = true)
	public String consultarReporteConsolidadoConsumo(Integer idmes, Integer idanio);
	
}
