package zegel.edu.pe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.models.Categorias;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.services.CategoriasServices;
import zegel.edu.pe.services.CompeticionesServices;
import zegel.edu.pe.services.EventosServices;
import zegel.edu.pe.services.SedesServices;

@Controller
@RequestMapping("/eventos")
public class EventosControllers {
	@Autowired
	private EventosServices eveS;
	@Autowired
	private SedesServices sedS;
	@Autowired
	private CategoriasServices catS;
	@Autowired
	private CompeticionesServices comS;
	
	
	@GetMapping("/registro")
	public String listar(Model mod) {
		List<Eventos> ev = eveS.getListarEventos();
		List<Sedes> sd = sedS.getListarSedes();
		List<Categorias> ct = catS.getListarCategorias();
		
		Eventos eve = new Eventos();
		
		mod.addAttribute("eventos", ev);	
		mod.addAttribute("sed", sd);
		mod.addAttribute("cat", ct);
		mod.addAttribute("even", eve);
		
		return "eventos/registro";
	}

//Método para guardar un evento/competicion
	@PostMapping("/guardar")
	public String guardar(Eventos eve, RedirectAttributes redirectAttributes) {
	    try {
	        // Verifica si los campos 'Sedes' y 'Categorias' no son nulos
	        if (eve.getSedes() == null || eve.getCategorias() == null) {
	            throw new IllegalArgumentException("Sede o categoría no seleccionada.");
	        }

	        Sedes sede = sedS.buscarPorId(eve.getSedes().getId());
	        Categorias categoria = catS.buscarPorId(eve.getCategorias().getId());

	        if (sede == null || categoria == null) {
	            throw new IllegalArgumentException("Sede o categoría no válida.");
	        }

	        eve.setSedes(sede);
	        eve.setCategorias(categoria);

	        // Guardar evento
	        Eventos nuevoEve = eveS.guardarEventos(eve);

	        // Crear y guardar competición
	        Competiciones com = new Competiciones();
	        com.setEventos(nuevoEve);
	        com.setSedes(sede);
	        com.setCategorias(categoria);
	        comS.guardarCompeticiones(com);

	        redirectAttributes.addFlashAttribute("registroExitoso", true);
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:/eventos/registro";
	}


//Método para el ID del Evento
	@GetMapping("/id/{id}")
	public ResponseEntity<Eventos> getEventoId(@PathVariable("id") int id) { 
		Eventos even = eveS.getListEventosId(id);
		if (even != null) {
			return ResponseEntity.ok(even);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


//Método editar Evento
	@PostMapping("/update")
	public String updateEvento(Eventos evento) {
		eveS.actualizarEventos(evento);
		return "redirect:/eventos/registro";
	}
	
//Método de Eliminar
	@GetMapping("/eliminar/{id}")
	public String eliminarEvento(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
	    try {
	        eveS.eliminarEventos(id);
	        redirectAttributes.addFlashAttribute("eliminacionExitoso", true);
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el evento.");
	    }

	    return "redirect:/eventos/registro";
	}
}
