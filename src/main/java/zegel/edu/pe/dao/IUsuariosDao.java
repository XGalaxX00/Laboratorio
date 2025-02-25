package zegel.edu.pe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zegel.edu.pe.models.Usuarios;

public interface IUsuariosDao extends JpaRepository<Usuarios, Integer> {
	
	Usuarios findByDni(String dni);
	
	Usuarios findByCorreo(String correo);
	
	Usuarios findByNombre(String nombre);
	
	List<Usuarios> findByCategorias_Id(Integer categoriaId);
	
}
