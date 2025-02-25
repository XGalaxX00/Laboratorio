package zegel.edu.pe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zegel.edu.pe.dao.ICategoriasDao;
import zegel.edu.pe.models.Categorias;

@Service
public class CategoriasServices {
	@Autowired
	private ICategoriasDao catDao;
	
	public List<Categorias> getListarCategorias(){
		return catDao.findAll();		
	}
	
	public void guardarCategorias(Categorias cat) {
		catDao.save(cat);
	}
	
	public void eliminarCategorias(Integer id) {
		catDao.deleteById(id);
	}
	
	public Categorias getListCategoriasId(Integer id){
		return catDao.findById(id).orElse(null);
	}
	
	public Categorias actualizarCategorias(Categorias cat) {
		return catDao.save(cat);	
	}
	
	public Categorias buscarPorId(Integer id) {
        return catDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));
    }
}
