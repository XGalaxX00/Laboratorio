package zegel.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.ICompeticionesDao;
import zegel.edu.pe.dao.IEventosDao;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Usuarios;

@Service
public class CompeticionesServices {
	@Autowired
	private ICompeticionesDao comDao;
	@Autowired
	private IUsuariosDao usuDao;
	
	public List<Competiciones> getListarCompeticiones(){
		return comDao.findAll();		
	}
	
	public void guardarCompeticiones(Competiciones com) {
		comDao.save(com);
	}
	
	public void eliminarCompeticiones(Integer id) {
		comDao.deleteById(id);
	}
	
	public Competiciones getListCompeticionesId(Integer id){
		return comDao.findById(id).orElse(null);
	}
	
	public Competiciones actualizarCompeticiones(Competiciones com) {
		return comDao.save(com);	
	}
	
	public Optional<Competiciones> buscarComPorEve(Eventos evento) {
	    return comDao.findByEventos(evento);
	}
	
	public Competiciones inscribirUsuario(Competiciones com, Integer usuarios_id) {
	    // Validar que la competición no tenga más de 4 usuarios inscritos
	    if (com.getUsuarios() != null && com.getUsuarios().size() >= 4) {
	        throw new IllegalStateException("La competición ya tiene el máximo de usuarios inscritos.");
	    }

	    // Verificar que el usuario exista
	    Usuarios usu = usuDao.findById(usuarios_id).orElseThrow(() -> 
	        new IllegalStateException("El usuario no existe.")
	    );

	    // Validar que el usuario no esté ya inscrito
	    if (com.getUsuarios() != null && com.getUsuarios().contains(usu)) {
	        throw new IllegalStateException("El usuario ya está inscrito en esta competición.");
	    }

	    // Agregar el usuario a la lista de la competición
	    com.getUsuarios().add(usu);

	    // Guardar los cambios
	    return comDao.save(com);
	}
	
	public List<Competiciones> getListCompePorSede(Integer sedeId) {
        return comDao.findBySedes_Id(sedeId); // Devuelve sedes filtrados
    }

	
	public int contarUsuariosInscritos(Competiciones competicion) {
	    return competicion.getUsuarios().size();
	}
	
	public long contarUsuariosPorCompeticion(Integer competicionId) {
		return comDao.countUsuariosByCompeticionId(competicionId);
	}

}
