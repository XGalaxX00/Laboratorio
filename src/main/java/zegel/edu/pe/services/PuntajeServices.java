package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IPuntajeDao;
import zegel.edu.pe.models.Puntaje;

@Service
public class PuntajeServices {
	@Autowired
	private IPuntajeDao punDao;
	
	public List<Puntaje> getListPuntaje(){
		return punDao.findAll();
	}
	
	public Puntaje getListPuntajeId(Integer id){
		return punDao.findById(id).orElse(null);
	}
	
	public Puntaje actualizarPuntaje(Puntaje pun) {
		return punDao.save(pun);	
	}
	
	public Puntaje guardarPuntaje(Puntaje pun) {
		return punDao.save(pun);
	}
	
	public void eliminarPuntaje(Integer id) {
		punDao.deleteById(id);
	}

}
