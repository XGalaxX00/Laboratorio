package zegel.edu.pe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Estados;
import zegel.edu.pe.models.Solicitudes;
import zegel.edu.pe.services.ClubesServices;
import zegel.edu.pe.services.EstadosServices;
import zegel.edu.pe.services.SolicitudesServices;
import zegel.edu.pe.services.UsuariosServices;

@Controller
@RequestMapping("/clubes")
public class ClubesControllers {
	@Autowired
	private ClubesServices cluS;
	@Autowired
	private SolicitudesServices solS;
	@Autowired
	private UsuariosServices usuS;
	@Autowired
	private EstadosServices estS;
	
	@GetMapping("/registro")
	public String listarClubes(Model mod) {
		List<Solicitudes> sl = solS.getSolicitudesEnProceso();
		
		Clubes clu = new Clubes();
		
		mod.addAttribute("sol", sl);
		mod.addAttribute("club", clu);
	   	
		return "clubes/index";
	}
	
	@GetMapping("/guardar/{id}")
	public String guardar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
		Clubes existente = cluS.getClubBySolicitudId(id);
	    if (existente != null) {
	        redirectAttributes.addFlashAttribute("error", "El club ya ha sido registrado para esta solicitud.");
	        return "redirect:/clubes/registro";
	    }
		
		Solicitudes sol = solS.getSolicitudById(id);
		
		if(sol == null) {
			redirectAttributes.addFlashAttribute("error", "La solicitud no existe");
			return "redirect:/clubes/registro";
		}
		
		Clubes club = new Clubes();
		club.setNombre(sol.getNombre_club());
		club.setImagen(sol.getImagen());
		club.setEncargado(sol.getEncargado());
		
		Estados est = estS.getListEstadosId(1);
		sol.setEstados(est);
		club.setSolicitudes(sol);
	    
		cluS.guardarClubes(club);
		redirectAttributes.addFlashAttribute("success", "El club ha sido registrado exitosamente.");
		return "redirect:/clubes/registro";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		Clubes existente = cluS.getClubBySolicitudId(id);
	    if (existente != null) {
	        redirectAttributes.addFlashAttribute("error", "El club ya ha sido registrado para esta solicitud.");
	        return "redirect:/clubes/registro";
	    }
		
		Solicitudes sol = solS.getSolicitudById(id);
		
		if(sol == null) {
			redirectAttributes.addFlashAttribute("error", "La solicitud no existe");
			return "redirect:/clubes/registro";
		}
		
		Estados est = estS.getListEstadosId(3);
		sol.setEstados(est);
		solS.guardarSolicitudes(sol);
	
		redirectAttributes.addFlashAttribute("success", "Se ha rechazado el registro del club");		
		return "redirect:/clubes/registro";
	}

}
