package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.ISedesDao;
import zegel.edu.pe.models.Sedes;

@Service
public class SedesServices {
	@Autowired
	private ISedesDao sedDao;
	
	public List<Sedes> getListarSedes(){
		return sedDao.findAll();		
	}
	
	public void guardarSedes(Sedes sed) {
		sedDao.save(sed);
	}
	
	public void eliminarSedes(Integer id) {
		sedDao.deleteById(id);
	}
	
	public Sedes getListSedesId(Integer id){
		return sedDao.findById(id).orElse(null);
	}
	
	public Sedes actualizarSedes(Sedes sed) {
		return sedDao.save(sed);	
	}
	
	public Sedes buscarPorId(Integer id) {
        return sedDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada con ID: " + id));
    }

}
