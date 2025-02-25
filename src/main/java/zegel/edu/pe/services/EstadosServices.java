package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IEstadosDao;
import zegel.edu.pe.models.Estados;

@Service
public class EstadosServices {
	@Autowired
	private IEstadosDao estDao;
	
	public List<Estados> getListEstados(){
		return estDao.findAll();
	}
	
	public Estados getListEstadosId(Integer id){
		return estDao.findById(id).orElse(null);
	}
	
	public Estados actualizarEstados(Estados est) {
		return estDao.save(est);	
	}
	
	public Estados guardarEstados(Estados est) {
		return estDao.save(est);
	}
	
	public void eliminarEstados(Integer id) {
		estDao.deleteById(id);
	}

}
