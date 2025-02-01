package zegel.edu.pe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.CompeticionesServices;
import zegel.edu.pe.services.EventosServices;
import zegel.edu.pe.services.SedesServices;
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
	
	@PostMapping("/inscribir")
	public String inscribirUsuario(@RequestParam Integer id, @RequestParam Integer usuarios_id, Eventos eve, RedirectAttributes redirectAttributes) {
	    // Buscar el evento
	    Optional<Eventos> even = eveS.buscarEventosId(eve.getId());
	    if (!even.isPresent()) {
	        redirectAttributes.addFlashAttribute("error", "No se encuentra el evento.");
	        return "redirect:/competiciones/lista";
	    }

	    // Buscar la competición asociada al evento
	    Optional<Competiciones> com = comS.buscarComPorEve(eve);
	    if (!com.isPresent()) {
	        redirectAttributes.addFlashAttribute("error", "No se encuentra la competición asociada al evento.");
	        return "redirect:/competiciones/lista";
	    }

	    Competiciones competicion = com.get();

	    // Verificar el número de usuarios inscritos
	    long usuariosInscritos = comS.contarUsuariosPorCompeticion(competicion.getId());
	    if (usuariosInscritos >= 4) {
	        redirectAttributes.addFlashAttribute("error", "La competición ya tiene el máximo de 4 usuarios inscritos.");
	        return "redirect:/competiciones/lista";
	    }

	    // Inscribir al usuario en la competición
	    comS.inscribirUsuario(competicion, usuarios_id);
	    redirectAttributes.addFlashAttribute("success", "Usuario inscrito correctamente.");
	    return "redirect:/usuarios/perfil";
	}

	
}
