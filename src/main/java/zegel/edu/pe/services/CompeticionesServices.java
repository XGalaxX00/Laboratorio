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
		
	public List<Competiciones> getListCompePorSede(Integer sedeId) {
        return comDao.findBySedes_Id(sedeId); // Devuelve sedes filtrados
    }

	
	public int contarUsuariosInscritos(Competiciones competicion) {
	    return competicion.getUsuarios().size();
	}
	
	public long contarUsuariosPorCompeticion(Integer competicionId) {
		return comDao.countUsuariosByCompeticionId(competicionId);
	}
	
	public Competiciones obtenerId(Integer id) {
        return comDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Competición no encontrada"));
    }

}
