package zegel.edu.pe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import zegel.edu.pe.models.Eventos;

public interface IEventosDao extends JpaRepository<Eventos, Integer>{
	
	public Eventos findByNombre(String nombre);
	
	boolean existsByCategoriasId(Integer categoriasId);
	
	List<Eventos> findByCategorias_Id(Integer categoriaId);
	
	Optional<Eventos> findById(Integer id);
	
	List<Eventos> findBySedes_Id(Integer sedeId);
}
