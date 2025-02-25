package zegel.edu.pe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import zegel.edu.pe.models.Fase;
import zegel.edu.pe.models.Turnos;

public interface ITurnos extends JpaRepository<Turnos, Integer>{
    
	@Query("SELECT t FROM Turnos t JOIN t.fase f WHERE t.competicion.id = :competicionId AND f.nombre = :faseNombre")
	List<Turnos> findByCompeticionIdAndFase(@Param("competicionId") Integer competicionId, @Param("faseNombre") String faseNombre);
	
	@Query("SELECT t FROM Turnos t WHERE t.competicion.id = :competicionId")
	List<Turnos> findByCompeticionId(@Param("competicionId") Integer competicionId);

	@Query("SELECT t FROM Turnos t WHERE t.competicion.id = :competicionId AND t.fase.nombre = 'final'")
	List<Turnos> findFinalTurnosByCompeticion(@Param("competicionId") Integer competicionId);
	
	@Query("SELECT t FROM Turnos t WHERE t.fase.nombre = :faseNombre")
	List<Turnos> findByFaseNombre(@Param("faseNombre") String faseNombre);

}
