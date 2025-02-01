package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.ISolicitudesDao;
import zegel.edu.pe.models.Solicitudes;

@Service
public class SolicitudesServices {
	@Autowired
	private ISolicitudesDao solDao;
	
	public List<Solicitudes> getListSolicitudes(){
		return solDao.findAll();
	}
	
	public Solicitudes getListSolicitudesId(Integer id){
		return solDao.findById(id).orElse(null);
	}
	
	public Solicitudes actualizarSolicitudes(Solicitudes sol) {
		return solDao.save(sol);	
	}
	
	public Solicitudes guardarSolicitudes(Solicitudes sol) {
		return solDao.save(sol);
	}
	
	public void eliminarSolicitudes(Integer id) {
		solDao.deleteById(id);
	}
	
	public Solicitudes getSolicitudById(Integer id) {
	    return solDao.findById(id).orElse(null);
	}
	
	public Solicitudes getSolicitudByEncargadoId(Integer encargado_id) {
		return solDao.findByEncargadoId(encargado_id);
	}

}
