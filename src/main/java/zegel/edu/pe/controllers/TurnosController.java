package zegel.edu.pe.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.dao.ITurnos;
import zegel.edu.pe.models.Turnos;
import zegel.edu.pe.services.TurnosServices;

@Controller
@RequestMapping("/turnos")
public class TurnosController {

	@Autowired
    private TurnosServices turnosService;
	@Autowired
	private ITurnos turnosRepository;

	@GetMapping("/generar/{competicionId}")
    public ResponseEntity<String> generarTurnos(@PathVariable Integer competicionId) {
        try {
            turnosService.generarTurnosSemifinal(competicionId);
            return ResponseEntity.ok("Turnos generados correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar los turnos.");
        }
    }
	@GetMapping("/lista")
	public String listarTurnos(Model model) {
	    List<Turnos> turnos = turnosService.obtenerTodosLosTurnos(); // Asegúrate de que este método exista en TurnosServices
	    model.addAttribute("turnos", turnos);
	    return "turnos/lista_turnos"; // Vista donde se mostrarán todos los turnos
	}
	@GetMapping
	public String listarTurnosGenerales(Model model) {
	    List<Turnos> turnos = turnosService.obtenerTodosLosTurnos();
	    model.addAttribute("turnos", turnos);
	    return "turnos/lista_turnos";
	}

	
	@GetMapping("/{competicionId}")
	public String verTurnos(@PathVariable Integer competicionId, Model model) {
	    List<Turnos> turnos = turnosService.obtenerTurnosPorCompeticion(competicionId);

	    System.out.println("Turnos recuperados para la competición ID " + competicionId + ": " + turnos.size());
	    for (Turnos turno : turnos) {
	        System.out.println(" - " + turno.getCompetidor1().getNombre() + " vs " + turno.getCompetidor2().getNombre());
	    }

	    model.addAttribute("turnos", turnos);
	    return "turnos/lista_turnos";
	}

	
//	 Generar turnos de semifinal y mostrar enfrentamientos
	@GetMapping("/generar-semifinal/{competicionId}")
	public String generarTurnosSemifinal(@PathVariable Integer competicionId, Model model) {
	    try {
	        turnosService.generarTurnosSemifinal(competicionId);
	        List<Turnos> turnosSemifinal = turnosService.findByCompeticionIdAndFase(competicionId, "Semifinal");
	        model.addAttribute("mensaje", "Turnos de semifinal generados exitosamente.");
	        model.addAttribute("turnos", turnosSemifinal);
	        return "turnos/lista_turnos"; // Asegúrate de que la vista existe
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al generar turnos: " + e.getMessage());
	        return "turnos/lista_turnos";
	    }
	}
	
// Generar Final
	@PostMapping("/registrarGanadores")
	public String registrarGanadores(@RequestParam Map<String, String> ganadores, RedirectAttributes redirectAttributes) {
	    try {
	        // Filtra el campo _csrf y convierte los valores a enteros
	        Map<Integer, Integer> ganadoresFiltrados = ganadores.entrySet().stream()
	            .filter(entry -> !entry.getKey().equals("_csrf")) // Ignorar el token CSRF
	            .collect(Collectors.toMap(
	                entry -> Integer.parseInt(entry.getKey()), 
	                entry -> Integer.parseInt(entry.getValue())
	            ));

	        turnosService.registrarGanadoresSemifinal(ganadoresFiltrados);
	        redirectAttributes.addFlashAttribute("mensaje", "Ganadores registrados con éxito.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al registrar ganadores: " + e.getMessage());
	    }
	    return "redirect:/turnos/lista";
	}




}
