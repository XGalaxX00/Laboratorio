package zegel.edu.pe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.models.Usuarios_Clubes;

public interface IUsuarios_ClubesDao extends JpaRepository<Usuarios_Clubes, Integer> {
	Optional<Usuarios_Clubes> findByUsuarios_id(Integer usuarios_id);
	
	List<Usuarios> findUsuariosByclubes_id(@Param("clubes_id") Integer clubes_id);
	
	@Query("SELECT COUNT(uc) FROM Usuarios_Clubes uc WHERE uc.clubes.id = :clubes_id")
	Integer countUsuariosByClubes_id(@Param("clubes_id") Integer clubes_id);

}
