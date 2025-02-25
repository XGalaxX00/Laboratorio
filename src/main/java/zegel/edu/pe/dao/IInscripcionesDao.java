package zegel.edu.pe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Inscripciones;
import zegel.edu.pe.models.Usuarios;

public interface IInscripcionesDao extends JpaRepository<Inscripciones, Integer>{
	
	boolean existsByUsuariosAndCompeticiones(Usuarios usu, Competiciones compe);
	
	List<Inscripciones> findByCompeticiones_Id(Integer competiciones_id);
	
	long countByCompeticiones(Competiciones compe);
	
	List<Inscripciones> findByUsuarios(Usuarios usuario);

    @Query("SELECT i.competiciones FROM Inscripciones i WHERE i.usuarios.id = :usuarioId")
    List<Competiciones> findCompeticionesByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
