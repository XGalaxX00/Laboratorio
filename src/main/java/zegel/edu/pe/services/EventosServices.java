package zegel.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IEventosDao;
import zegel.edu.pe.models.Eventos;

@Service
public class EventosServices {
	@Autowired
	private IEventosDao eveDao;
	
	public List<Eventos> getListarEventos(){
		return eveDao.findAll();		
	}
	
	public Eventos guardarEventos(Eventos eve) {
	    if (eve.getSedes() == null || eve.getSedes().getId() == null) {
	        throw new IllegalArgumentException("La sede es obligatoria para guardar un evento.");
	    }
	    if (eve.getCategorias() == null || eve.getCategorias().getId() == null) {
	        throw new IllegalArgumentException("La categoría es obligatoria para guardar un evento.");
	    }

	    // Verificar que la sede no tenga más de 5 eventos
	    List<Eventos> eventosEnSede = eveDao.findBySedes_Id(eve.getSedes().getId());
	    if (eventosEnSede.size() >= 5) {
	        throw new IllegalArgumentException("La sede ya tiene el máximo permitido de 5 eventos.");
	    }

	    return eveDao.save(eve);
	}
	
	public void eliminarEventos(Integer id) {
		Optional<Eventos> evento = eveDao.findById(id);
	    if (evento.isPresent()) {
	    	eveDao.deleteById(id); // Eliminar el evento
	    } else {
	        throw new IllegalArgumentException("Evento no encontrado con ID: " + id);
	    }
	}
	
	public Eventos getListEventosId(Integer id){
		return eveDao.findById(id).orElse(null);
	}
	
	public Eventos actualizarEventos(Eventos eve) {
		return eveDao.save(eve);	
	}

	public Eventos NombreEvento(String nombre) {
		return eveDao.findByNombre(nombre);
	}
	
	public List<Eventos> getEventosPorCategoria(Integer categoriaId) {
	    return eveDao.findByCategorias_Id(categoriaId);
	}
	
	public Optional<Eventos> buscarEventosId(Integer id) {
		return eveDao.findById(id);
	}

}
