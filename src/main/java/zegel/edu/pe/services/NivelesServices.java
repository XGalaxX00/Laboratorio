package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.INivelesDao;
import zegel.edu.pe.models.Niveles;

@Service
public class NivelesServices {
	@Autowired
	private INivelesDao nivDao;
	
	public List<Niveles> getListarNiveles(){
		return nivDao.findAll();		
	}
	
	public void guardarNiveles(Niveles niv) {
		nivDao.save(niv);
	}
	
	public void eliminarNiveles(Integer id) {
		nivDao.deleteById(id);
	}
	
	public Niveles getListNivelesId(Integer id){
		return nivDao.findById(id).orElse(null);
	}
	
	public Niveles actualizarNiveles(Niveles niv) {
		return nivDao.save(niv);	
	}

}
