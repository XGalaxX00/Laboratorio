package zegel.edu.pe.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import zegel.edu.pe.dao.IUsuarios_ClubesDao;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Turnos;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.models.Usuarios_Clubes;
import zegel.edu.pe.services.CategoriasServices;
import zegel.edu.pe.services.ClubesServices;
import zegel.edu.pe.services.CompeticionesServices;
import zegel.edu.pe.services.EventosServices;
import zegel.edu.pe.services.InscripcionesServices;
import zegel.edu.pe.services.TurnosServices;
import zegel.edu.pe.services.UsuariosServices;
import zegel.edu.pe.services.Usuarios_ClubesServices;

@Controller
@RequestMapping("/inicio")
public class VComController {

	@Autowired
	private EventosServices eveS;
	@Autowired
	private UsuariosServices usuS;
	@Autowired
	private CategoriasServices catS;
	@Autowired
	private ClubesServices clubS;
	@Autowired
	private InscripcionesServices inscripS;
	@Autowired
	private CompeticionesServices compeS;
	@Autowired
	private Usuarios_ClubesServices ucS;
	@Autowired
	private TurnosServices turnoS;
	@Autowired
	private IUsuarios_ClubesDao ucDao;
	

	@GetMapping("/inicio")
	public String incio(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		
		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		return "VCom/index";
	}

	@GetMapping("/categorias")
	public String inicioCategorias(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		return "VCom/descripcion_categoria";
	}

	@GetMapping("/niveles")
	public String inicioNiveles(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		return "VCom/descripcion_niveles";
	}

	@GetMapping("/sedes")
	public String inicioSedes(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		return "VCom/PaginaPrincipal-Sedes";
	}
	
	@GetMapping("/noticias")
	public String inicioNoticias(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		return "VCom/noticias_grafico";
	}

	@GetMapping("/clubes")
	public String inicioClubes(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());

		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		if (usuarioAutenticado == null) {
			return "redirect:/login";
		}

		List<Clubes> clubes = clubS.getListClubes();
	    
	    // Mapa para almacenar el conteo de usuarios por club
	    Map<Integer, Integer> cantidadUsu = new HashMap<>();
	    for (Clubes club : clubes) {
	        Integer cantidad = ucS.contarUsuariosPorClub(club.getId());
	        cantidadUsu.put(club.getId(), cantidad);
	    }

	    modelo.addAttribute("usuar", usuarioAutenticado);
	    modelo.addAttribute("clubes", clubes);
	    modelo.addAttribute("cantidadUsuario", cantidadUsu);
		
		return "VCom/inscripcion_club";
	}

//Perfil
	@GetMapping("/perfil")
	public String mostrar(HttpServletRequest request, Model modelo, HttpSession session, RedirectAttributes redirectAttributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());

		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		if (usuarioAutenticado == null) {
			return "redirect:/login";
		}

		Integer categoriaId = usuarioAutenticado.getCategorias().getId();
		List<Eventos> eventosPorCategoria = eveS.getEventosPorCategoria(categoriaId);

		List<Usuarios> usuariosPorCategoria = usuS
				.getListUsuariosPorCategoria(usuarioAutenticado.getCategorias().getId());
		
		List<Competiciones> competiciones = inscripS.obtenerCompeticionesPorUsuario(usuarioAutenticado.getId());

		
		Optional<Usuarios_Clubes> usuarioClubOpt = ucDao.findByUsuarios_id(usuarioAutenticado.getId());
	    if (usuarioClubOpt.isPresent()) {
	        // Suponiendo que Usuarios_Clubes tiene un método getClub() que retorna la entidad Club
	        Clubes clubes = usuarioClubOpt.get().getClubes();
	        modelo.addAttribute("clubes", clubes);
	    } else {
	        // Si el usuario no está inscrito en ningún club, puedes agregar un atributo nulo o un mensaje
	        modelo.addAttribute("clubes", null);
	    }

		String foto = usuarioAutenticado.getFoto();
		if (foto == null || foto.isEmpty()) {
			foto = "default.jpg";
		}	
		
		modelo.addAttribute("usuar", usuarioAutenticado);
		modelo.addAttribute("correo", usuarioAutenticado.getCorreo());
		modelo.addAttribute("foto", foto);
		modelo.addAttribute("categoria", usuarioAutenticado.getCategorias().getNombre());
		modelo.addAttribute("nivel", usuarioAutenticado.getNiveles().getNombre());
		modelo.addAttribute("puntaje", usuarioAutenticado.getPuntaje().getPuntaje());
		modelo.addAttribute("eventos", eventosPorCategoria);
		modelo.addAttribute("usuario", usuariosPorCategoria);
		modelo.addAttribute("competiciones", competiciones);

		return "usuarios/perfil";
	}
	
	@PostMapping("/inscribirse/{evento_id}")
	public String inscribirUsuario(@PathVariable Integer evento_id,
	        RedirectAttributes redirectAttributes, HttpServletRequest request, HttpSession sesion) {
	    
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);

	    if (usuarioAutenticado == null) {
	        return "redirect:/login";
	    }

	    System.out.println("Usuario autenticado: " + usuarioAutenticado.getId());
	    System.out.println("Evento recibido: " + evento_id);

	    try {
	        inscripS.inscribirUsuarioEnEvento(usuarioAutenticado.getId(), evento_id);
	        System.out.println("✅ Inscripción exitosa.");
	        redirectAttributes.addFlashAttribute("success", "¡Inscripción Exitosa!");
	    } catch (IllegalArgumentException | IllegalStateException e) {
	        System.out.println("❌ Error al inscribir: " + e.getMessage());
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:/inicio/perfil";
	}

//Eliminar perfil
	@PostMapping("/perfil/eliminar/{id}")
	public String eliminarCuenta(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
	    System.out.println("Intentando eliminar usuario con ID: " + id);

	    Usuarios usuarioAutenticado = usuS.getListUsuariosId(id);
	    if (usuarioAutenticado == null) {
	        System.out.println("Usuario no encontrado.");
	        return "redirect:/login";
	    }

	    try {
	        usuS.eliminarUsuarios(id);
	        SecurityContextHolder.getContext().setAuthentication(null); // Cerrar sesión
	        session.invalidate();
	        redirectAttributes.addFlashAttribute("success", "Tu cuenta ha sido eliminada correctamente.");
	        return "redirect:/login";
	    } catch (DataIntegrityViolationException e) {
	        System.out.println("Error: No se puede eliminar el usuario porque tiene datos relacionados.");
	        redirectAttributes.addFlashAttribute("error", "No puedes eliminar tu cuenta porque tienes datos asociados (encargado de un club)");
	        return "redirect:/inicio/perfil";
	    } catch (Exception e) {
	        System.out.println("Error al eliminar usuario: " + e.getMessage());
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar tu cuenta. Inténtalo de nuevo.");
	        return "redirect:/inicio/perfil";
	    }
	}


	
//Editar Perfil
	@GetMapping({"/perfil/editar/{id}"})
	public String EditarUsuario(HttpServletRequest request, Model modelo,@PathVariable("id") int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());

		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		if (usuarioAutenticado == null) {
			return "redirect:/login";
		}
		
		Usuarios usuario = usuS.getUsuarioById(id);
		modelo.addAttribute("usuarios", usuario);

		return "usuarios/editar_perfil";
	}
	
	@PostMapping("/updatePerfil/{id}")
	public String updateUsuario(@PathVariable("id") int id,Usuarios usuario,Model modelo,@RequestParam("file") MultipartFile file) {
		
		Usuarios usuarioExist = usuS.getUsuarioById(id);
		
		usuarioExist.setNombre(usuario.getNombre());
		usuarioExist.setApellido(usuario.getApellido());
		usuarioExist.setTelefono(usuario.getTelefono());
		usuarioExist.setContrasena(usuario.getContrasena());		
		
		usuS.actualizarUsuarios(usuario);
		
		return "redirect:/inicio/perfil";
	}
	

//Método para obtener ID del evento
	@GetMapping("/evento/id/{id}")
	public ResponseEntity<Eventos> getEventoId(@PathVariable("id") int id) {
		Eventos evento = eveS.getListEventosId(id);
		if (evento != null) {
			return ResponseEntity.ok(evento);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
//Método para traer la competicion
	@GetMapping("/competicion/id/{id}")
	public ResponseEntity<Competiciones> getCompeticionesId(@PathVariable("id") int id) {
		
		Competiciones competiciones = compeS.getListCompeticionesId(id);
		if (competiciones != null) {
			return ResponseEntity.ok(competiciones);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping({ "/perfil/orden-merito" })
	public String ordenMerito(@RequestParam(required = false) Integer categoriaId, HttpServletRequest request,
			Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());

		// Lista de usuarios filtrados según la categoría seleccionada
		List<Usuarios> usuariosPorCategoria = (categoriaId == null) ? usuS.getListUsuarios()
				: usuS.getListUsuPorCategoria(categoriaId);

		// Ordenar usuarios por puntaje de mayor a menor
		usuariosPorCategoria.sort((u1, u2) -> u2.getPuntaje().getPuntaje() - u1.getPuntaje().getPuntaje());

		// Agregar datos a la vista
		modelo.addAttribute("usuario", usuariosPorCategoria);
		modelo.addAttribute("categorias", catS.getListarCategorias()); // Para llenar el select

		return "usuarios/orden-merito";
	}
	
	@GetMapping({"/perfil/actual-competicion"})
	public String ActualCompeticion(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());

		String username = authentication.getName();
		Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
		if (usuarioAutenticado == null) {
			return "redirect:/login";
		}
		
		return "VCom/grafico_competicion";
	}
	
	
	//Historial
	@GetMapping("/historial/{id}")
	public String obtenerHistorial(@PathVariable Integer id, HttpServletRequest request, Model model) {
	    // Obtener autenticación del usuario
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        return "redirect:/login";
	    }

	    // Guardar información de autenticación en el modelo
	    model.addAttribute("isAuthenticated", true);
	    model.addAttribute("currentUri", request.getRequestURI());

	    // Obtener usuario autenticado
	    String username = authentication.getName();
	    Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);
	    if (usuarioAutenticado == null) {
	        return "redirect:/login";
	    }

	    // Obtener usuario por ID
	    Usuarios usuario = usuS.getUsuarioById(id);
	    if (usuario == null) {
	        model.addAttribute("mensaje", "El usuario no existe.");
	        return "usuarios/historial";
	    }

	    model.addAttribute("usuarios", usuario);

	    // Obtener historial de competiciones
	    List<Competiciones> competiciones = inscripS.obtenerHistorialCompeticiones(id);
	    if (competiciones.isEmpty()) {
	        model.addAttribute("mensaje", "El usuario no tiene competiciones en su historial.");
	    } else {
	        model.addAttribute("competiciones", competiciones);
	    }

	    return "usuarios/historial"; // Vista Thymeleaf
	}
	
	@GetMapping("/descripcion/{id}")
	public String descripcionHistorial(@PathVariable Integer id, Model model) {
	    Competiciones competiciones = compeS.obtenerId(id);
	    if (competiciones == null) {
	        return "redirect:/usuarios/historial"; // Si no encuentra la competición, redirige
	    }
	    
	    List<Turnos> turnos = turnoS.obtenerTurnosPorCompeticion(id);

	    if (turnos == null || turnos.isEmpty()) {
	        model.addAttribute("mensaje", "No hay turnos registrados para esta competición.");
	    } else {
	        model.addAttribute("turnos", turnos);
	    }
	    
	    System.out.println("Turnos recuperados para la competición ID " + id + ": " + turnos.size());
	    for (Turnos turno : turnos) {
	        System.out.println(" - " + turno.getCompetidor1().getNombre() + " vs " + turno.getCompetidor2().getNombre());
	    }

	    model.addAttribute("competiciones", competiciones);
	    return "usuarios/historial_descripcion"; // Retorna la vista eventos/detalles.html
	}


}
