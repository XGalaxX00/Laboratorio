package zegel.edu.pe.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Usuarios;

@Service
public class UsuariosServices {
  
	@Autowired
	private IUsuariosDao usuDao;
	
	public List<Usuarios> getListUsuarios(){
		return usuDao.findAll();
	}
	
	public Usuarios getListUsuariosId(Integer id){
		return usuDao.findById(id).orElse(null);
	}
	
	public Usuarios actualizarUsuarios(Usuarios usu) {
		return usuDao.save(usu);	
	}
	
	public Usuarios guardarUsuarios(Usuarios usu) {
		return usuDao.save(usu);
	}
	
	public void eliminarUsuarios(Integer id) {
		usuDao.deleteById(id);
	}
	
	public Usuarios buscarUsu(String dni) {
		return usuDao.findByDni(dni);
	}
	
	public Usuarios CorreoUsuario(String correo) {
		return usuDao.findByCorreo(correo);
	}
	
	public Usuarios NombreUsuario(String nombre) {
		return usuDao.findByNombre(nombre);
	}

    public List<Usuarios> getListUsuariosPorCategoria(Integer categoriaId) {
        return usuDao.findAll().stream()
                .filter(u -> u.getCategorias().getId().equals(categoriaId))
                .sorted((u1, u2) -> Integer.compare(u2.getPuntaje().getPuntaje(), u1.getPuntaje().getPuntaje()))
                .collect(Collectors.toList());
    }
    
    public List<Usuarios> getListUsuPorCategoria(Integer categoriaId) {
        return usuDao.findByCategorias_Id(categoriaId); // Devuelve usuarios filtrados
    }
    
    public Usuarios getUsuarioById(Integer id) {
        return usuDao.findById(id).orElse(null);
    }
}
