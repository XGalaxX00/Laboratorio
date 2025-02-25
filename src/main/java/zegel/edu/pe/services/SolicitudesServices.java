package zegel.edu.pe.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import zegel.edu.pe.dao.ISolicitudesDao;
import zegel.edu.pe.models.Solicitudes;

@Service
public class SolicitudesServices {
	
	private final Path root = Paths.get("uploads/foto_club");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("¡No se pudo inicializar la carpeta para cargarla!");
        }
    }

    public void saveImage(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }
    }
	
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
	
	public List<Solicitudes> getSolicitudesEnProceso(){
		return solDao.findByEstadosId(2);
	}

}
