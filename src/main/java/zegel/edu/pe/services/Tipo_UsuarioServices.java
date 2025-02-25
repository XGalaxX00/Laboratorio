package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.ITipo_UsuarioDao;
import zegel.edu.pe.models.Tipo_Usuario;

@Service
public class Tipo_UsuarioServices {
	@Autowired
	private ITipo_UsuarioDao tipoDao;
	
	public List<Tipo_Usuario> getListarTipo_Usuario(){
		return tipoDao.findAll();		
	}
	
	public void guardarTipo_Usuario(Tipo_Usuario tipo) {
		tipoDao.save(tipo);
	}
	
	public void eliminarTipo_Usuario(Integer id) {
		tipoDao.deleteById(id);
	}
	
	public Tipo_Usuario getListTipo_UsuarioId(Integer id){
		return tipoDao.findById(id).orElse(null);
	}
	
	public Tipo_Usuario actualizarTipo_Usuario(Tipo_Usuario tipo) {
		return tipoDao.save(tipo);	
	}

}
