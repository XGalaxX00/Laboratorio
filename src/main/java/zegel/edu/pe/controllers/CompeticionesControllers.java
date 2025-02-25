package zegel.edu.pe.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.models.Turnos;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.CompeticionesServices;
import zegel.edu.pe.services.EventosServices;
import zegel.edu.pe.services.InscripcionesServices;
import zegel.edu.pe.services.SedesServices;
import zegel.edu.pe.services.TurnosServices;
import zegel.edu.pe.services.UsuariosServices;

@Controller
@RequestMapping("/competiciones")
public class CompeticionesControllers {
	
	@Autowired
	private UsuariosServices usuS;
	@Autowired
	private CompeticionesServices comS;
	@Autowired
	private EventosServices eveS;
	@Autowired
	private SedesServices sedeS;
	@Autowired
	private InscripcionesServices inscripS;
	@Autowired
	private TurnosServices turnS;

	@GetMapping("/lista")
	public String listarCompeticiones(@RequestParam(required = false)Integer sedeId, HttpServletRequest request,Model model) {		
		// Lista de usuarios filtrados según la categoría seleccionada
        List<Competiciones> competicionesPorSede = (sedeId == null)
            ? comS.getListarCompeticiones() // Si no se seleccionó categoría, muestra todos los usuarios
            : comS.getListCompePorSede(sedeId);
        
	    model.addAttribute("competiciones", competicionesPorSede);
	    model.addAttribute("sedes", sedeS.getListarSedes());
	    
	return "competiciones/lista";
	}
	
	@GetMapping("/resultados")
	public String listarResultados(@RequestParam(required = false) Integer sedeId, Model model) {        
	    // Obtener todas las competiciones o filtrar por sede
	    List<Competiciones> competiciones = (sedeId == null) 
	        ? comS.getListarCompeticiones() 
	        : comS.getListCompePorSede(sedeId);

	    // Filtrar solo las competiciones que tienen turnos registrados
	    List<Competiciones> competicionesConTurnos = competiciones.stream()
	        .filter(compe -> !turnS.obtenerTurnosPorCompeticion(compe.getId()).isEmpty())
	        .collect(Collectors.toList());

	    model.addAttribute("competiciones", competicionesConTurnos);
	    model.addAttribute("sedes", sedeS.getListarSedes());

	    return "resultados/lista";
	}

	@GetMapping("/descripcion/{id}")
	public String descripcionHistorial(@PathVariable Integer id, Model model) {
	    // Obtener la competición por ID
	    Competiciones competiciones = comS.obtenerId(id);
	    if (competiciones == null) {
	        return "redirect:/resultados"; // Redirigir si no existe
	    }
	    
	    // Obtener los turnos de la competición
	    List<Turnos> turnos = turnS.obtenerTurnosPorCompeticion(id);

	    // Agregar información al modelo
	    model.addAttribute("competiciones", competiciones);
	    model.addAttribute("turnos", turnos);

	    return "resultados/lista_descripcion"; // Vista de detalles
	}



	
}
