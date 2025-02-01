package zegel.edu.pe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import zegel.edu.pe.models.Solicitudes;

public interface ISolicitudesDao extends JpaRepository<Solicitudes, Integer>{

	Solicitudes findByEncargadoId(Integer encargado_id);
}
