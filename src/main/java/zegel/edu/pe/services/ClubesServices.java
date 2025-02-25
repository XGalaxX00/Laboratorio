package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.IClubesDao;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Usuarios;

@Service
public class ClubesServices {
	@Autowired
	private IClubesDao cluDao;
	
	public List<Clubes> getListClubes(){
		return cluDao.findAll();
	}
	
	public Clubes getListClubesId(Integer id){
		return cluDao.findById(id).orElse(null);
	}
	
	public Clubes actualizarClubes(Clubes clu) {
		return cluDao.save(clu);	
	}
	
	public Clubes guardarClubes(Clubes clu) {
		return cluDao.save(clu);
	}
	
	public void eliminarClubes(Integer id) {
		cluDao.deleteById(id);
	}

	public Clubes getClubBySolicitudId(Integer id) {
		return cluDao.findBySolicitudesId(id);
	}
}
