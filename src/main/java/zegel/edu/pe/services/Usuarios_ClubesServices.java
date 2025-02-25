package zegel.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IClubesDao;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.dao.IUsuarios_ClubesDao;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.models.Usuarios_Clubes;

@Service
public class Usuarios_ClubesServices {
	@Autowired
	private IUsuarios_ClubesDao ucDao;
	
	@Autowired
	private IUsuariosDao usuDao;
	
	@Autowired
	private IClubesDao cluDao;
	
	public Usuarios_Clubes actualizarUsuarioPorClub(Integer usuarios_id, Integer clubes_id) {
		
        Usuarios usuario = usuDao.findById(usuarios_id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Clubes club = cluDao.findById(clubes_id)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));

        Usuarios_Clubes uc = new Usuarios_Clubes();
        uc.setUsuarios(usuario);
        uc.setClubes(club);
        
        return ucDao.save(uc);
    }
	
	public List<Usuarios> obtenerUsuariosDeClub(Integer clubes_id) {
        return ucDao.findUsuariosByclubes_id(clubes_id);
    }
	
	public Integer contarUsuariosPorClub(Integer clubes_id) {
		return ucDao.countUsuariosByClubes_id(clubes_id);
	}

}
