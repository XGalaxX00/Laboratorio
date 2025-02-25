package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IResultadosDao;
import zegel.edu.pe.models.Resultados;

@Service
public class ResultadosServices {
	@Autowired
	private IResultadosDao resDao;
	
	public List<Resultados> getListarResultados(){
		return resDao.findAll();		
	}
	
	public void guardarResultados(Resultados res) {
		resDao.save(res);
	}
	
	public void eliminarResultados(Integer id) {
		resDao.deleteById(id);
	}
	
	public Resultados getListResultadosId(Integer id){
		return resDao.findById(id).orElse(null);
	}
	
	public Resultados actualizarResultados(Resultados res) {
		return resDao.save(res);	
	}

}
