package zegel.edu.pe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zegel.edu.pe.models.Solicitudes;

public interface ISolicitudesDao extends JpaRepository<Solicitudes, Integer>{

	Solicitudes findByEncargadoId(Integer encargado_id);
	
	List<Solicitudes> findByEstadosId(Integer estados_id);
}
