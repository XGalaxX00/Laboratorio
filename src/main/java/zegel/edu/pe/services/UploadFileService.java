package zegel.edu.pe.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	private String folder = "src/main/resources/static/Img-com";

    public String saveImage(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    public void deleteImage(String nombre) {
        File file = new File(folder  + nombre);
        file.delete();
    }

//	private final Path root = Paths.get("src/main/resources/static/Img-com");
//
//	public void init() {
//        try {
//            if (!Files.exists(root)) {
//                Files.createDirectories(root);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("¡No se pudo inicializar la carpeta para cargarla!", e);
//        }
//    }
//
//    public void save(MultipartFile file) {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("El archivo no puede estar vacío.");
//        }
//
//        try {
//            // Copiar el archivo al directorio destino
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//        } catch (FileAlreadyExistsException e) {
//            throw new RuntimeException("Ya existe un archivo con ese nombre: " + file.getOriginalFilename(), e);
//        } catch (IOException e) {
//            throw new RuntimeException("Error al guardar el archivo: " + file.getOriginalFilename(), e);
//        }
//    }
//	  
//	  public void delete(String nombre) {
//			String ruta="src/main/resources/static/Img-com";
//			File file= new File(ruta+nombre);
//			file.delete();
//		}
}
