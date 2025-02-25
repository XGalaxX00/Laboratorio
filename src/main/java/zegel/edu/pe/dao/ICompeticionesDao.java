  package zegel.edu.pe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Usuarios;

public interface ICompeticionesDao extends JpaRepository<Competiciones, Integer> {
	
	Optional<Competiciones> findById(Integer id);
	
    Optional<Competiciones> findByEventos(Eventos evento);
    
    List<Competiciones> findBySedes_Id(Integer sedeId);

    @Query("SELECT COUNT(u) FROM Competiciones c JOIN c.usuarios u WHERE c.id = :competicionId")
    long countUsuariosByCompeticionId(@Param("competicionId") Integer competicionId);
    
    @Query("SELECT c FROM Competiciones c WHERE c.eventos.id = :eventoId")
    Optional<Competiciones> findByEventoId(@Param("eventoId") Integer eventoId);

}
