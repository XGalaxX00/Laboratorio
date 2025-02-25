package zegel.edu.pe.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zegel.edu.pe.models.Fase;

public interface IFaseDao extends JpaRepository<Fase, Integer>{
	
	Optional<Fase> findByNombre(String nombre);
	
}
