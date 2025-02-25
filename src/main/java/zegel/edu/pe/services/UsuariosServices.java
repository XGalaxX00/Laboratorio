package zegel.edu.pe.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IClubesDao;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Usuarios;

@Service
public class UsuariosServices {
  
	@Autowired
	private IUsuariosDao usuDao;
	
	@Autowired
	private IClubesDao cluDao;
	
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
    
    public Usuarios actualizarUsuarioPorClub(Integer usuarios_id, Integer clubes_id) {
        Usuarios usuario = usuDao.findById(usuarios_id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Clubes club = cluDao.findById(clubes_id)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));

        return usuDao.save(usuario);
    }
}
