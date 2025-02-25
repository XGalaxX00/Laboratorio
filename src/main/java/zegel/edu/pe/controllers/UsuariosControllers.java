package zegel.edu.pe.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import zegel.edu.pe.models.Categorias;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Niveles;
import zegel.edu.pe.models.Puntaje;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.models.Tipo_Usuario;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.CategoriasServices;
import zegel.edu.pe.services.ClubesServices;
import zegel.edu.pe.services.EventosServices;
import zegel.edu.pe.services.InscripcionesServices;
import zegel.edu.pe.services.NivelesServices;
import zegel.edu.pe.services.PuntajeServices;
import zegel.edu.pe.services.SolicitudesServices;
import zegel.edu.pe.services.Tipo_UsuarioServices;
import zegel.edu.pe.services.UploadFileService;
import zegel.edu.pe.services.UsuariosServices;

@Controller
@RequestMapping("/usuarios")
public class UsuariosControllers {
	@Autowired
	private UsuariosServices usuS;
	@Autowired
	private CategoriasServices catS;
	@Autowired
	private NivelesServices nivS;
	@Autowired
	private PuntajeServices punS;
	@Autowired
	private ClubesServices cluS;
	@Autowired
	private SolicitudesServices solS;
	@Autowired
	private Tipo_UsuarioServices tipoS;
	@Autowired
	private EventosServices eveS;
	@Autowired
	private UploadFileService uploadS;
	@Autowired
	private InscripcionesServices insS;
	
	@GetMapping("/registro")
	public String listar(Model mod) {
		
//		List<Usuarios> us = usuS.getListUsuarios();
//		List<Categorias> ct = catS.getListarCategorias();
//		List<Niveles> nv = nivS.getListarNiveles();
//	    List<Puntaje> pt = punS.getListPuntaje();
		
		mod.addAttribute("categoria", catS.getListarCategorias());
//		mod.addAttribute("niv", nv);
//		mod.addAttribute("pun", pt);
		mod.addAttribute("usua", new Usuarios());
		
		return "usuarios/registro";
	}
	
	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute Usuarios usu,@RequestParam Integer categoria) throws IOException{
			
		usu.setNombre(usu.getNombre());
		usu.setApellido(usu.getApellido());
		usu.setTelefono(usu.getTelefono());
		usu.setCorreo(usu.getCorreo());
		usu.setContrasena(usu.getContrasena());
		usu.setDni(usu.getDni());
		
		Categorias cate = catS.getListCategoriasId(categoria);
	    usu.setCategorias(cate);
		
		Niveles niv=nivS.getListNivelesId(1);
		usu.setNiveles(niv);

		Puntaje pun = punS.getListPuntajeId(1);
		usu.setPuntaje(pun);
		
		Tipo_Usuario tipo = tipoS.getListTipo_UsuarioId(5);
		usu.setTipo_usuario(tipo);
		
		String passwordEncriptada = new BCryptPasswordEncoder().encode(usu.getContrasena());
		usu.setContrasena(passwordEncriptada);
		
		
		usuS.guardarUsuarios(usu);
		
//		Usuarios usua = usuS.buscarUsu(usu.getDni());
//		
//		if( usua == null ) {
//		        if (file == null || file.isEmpty()) {
//		            throw new IllegalArgumentException("El archivo no puede estar vacío");
//		        }
//			
//			usu.setFoto(file.getOriginalFilename());
//			
//          Puntaje nuevoPun = new Puntaje();
//			nuevoPun.setPuntaje(0);
//			Puntaje pun = punS.guardarPuntaje(nuevoPun);
//			
//			Niveles niv = nivS.getListNivelesId(1);
//			
//			Tipo_Usuario tipo = tipoS.getListTipo_UsuarioId(5);
//			
//			Categorias cate = catS.getListCategoriasId(1);
//			
//			usu.setNombre(usu.getNombre());
//			usu.setApellido(usu.getApellido());
//			usu.setTelefono(usu.getTelefono());
//			usu.setCorreo(usu.getCorreo());
//			usu.setDni(usu.getDni());
//			usu.setNiveles(niv);
//			usu.setPuntaje(pun);
//			usu.setTipo_usuario(tipo);
//			usu.setCategorias(cate);
//			
//			usuS.save(file);
//			
//			String passwordEncriptada = new BCryptPasswordEncoder().encode(usu.getContrasena());
//			usu.setContrasena(passwordEncriptada);
//	        
//			usuS.guardarUsuarios(usu);
//		}
//		else {
//			System.out.println("Ya existe este usuario");
//		}

		return "redirect:/login";
	}
	
	@PostMapping("/clubes/{clubes_id}")
	public String inscribirAClub(@PathVariable Integer clubes_id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    String username = authentication.getName();
	    Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
	    if (usuarioAutenticado == null) {
	        return "redirect:/login";
	    }
	    
	    Integer usuarios_id = usuarioAutenticado.getId();
		
		usuS.actualizarUsuarioPorClub(usuarios_id, clubes_id);
		return "redirect:/inicio/perfil";	
	}
	
	@GetMapping("/historial/{usuarioId}")
	public String obtenerHistorial(@PathVariable Integer usuarioId, Model model) {
	    Usuarios usu = usuS.getUsuarioById(usuarioId);
	    
	    List<Competiciones> historial = insS.obtenerHistorialCompeticiones(usuarioId);

	    if (historial.isEmpty()) {
	        model.addAttribute("mensaje", "El usuario no tiene competiciones en su historial.");
	    } else {
	        model.addAttribute("historial", historial);
	    }

	    return "usuarios/historial"; // Retorna la vista Thymeleaf "usuarios/historial.html"
	}


}
