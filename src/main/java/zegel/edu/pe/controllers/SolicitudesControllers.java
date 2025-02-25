package zegel.edu.pe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.models.Estados;
import zegel.edu.pe.models.Solicitudes;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.EstadosServices;
import zegel.edu.pe.services.SolicitudesServices;
import zegel.edu.pe.services.UsuariosServices;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesControllers {
	@Autowired
	private SolicitudesServices solS;
	@Autowired
	private EstadosServices estS;
	@Autowired
	private UsuariosServices usuS;
	
	@GetMapping("/registro/{id}")
	public String listar(@PathVariable Integer id, Model mod) {
		Usuarios usu = usuS.getUsuarioById(id);
		
	    List<Solicitudes> sl = solS.getListSolicitudes();
	    List<Estados> es = estS.getListEstados();

		mod.addAttribute("est", es);
		mod.addAttribute("encargado", id);
	    mod.addAttribute("nombreUsu", usu.getNombre()); // Opcional, si deseas mostrar el nombre del usuario
	    mod.addAttribute("soli", new Solicitudes()); // Objeto vacío para el formulario
		
		return "solicitudes/index";
	}
	
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Solicitudes sol, @RequestParam Integer encargado_id, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file){
		Solicitudes existente = solS.getSolicitudByEncargadoId(encargado_id);
	    if (existente != null) {
	        redirectAttributes.addFlashAttribute("error", "Ya existe una solicitud registrada para este encargado.");
	        return "redirect:/inicio/perfil";
	    }
		    
		System.out.println(file.getOriginalFilename());
		
		if (!file.getContentType().startsWith("image/")) {
	        redirectAttributes.addFlashAttribute("error", "El archivo debe ser una imagen.");
	        return "redirect:/inicio/perfil";
	    }
		
		Usuarios encargado = usuS.getUsuarioById(encargado_id);
		sol.setEncargado(encargado);
		
		Estados est = estS.getListEstadosId(2);
		
		sol.setImagen(file.getOriginalFilename());
		solS.saveImage(file);
		
		sol.setEstados(est);
		solS.guardarSolicitudes(sol);
		
		
		redirectAttributes.addFlashAttribute("success", "Solicitud registrada correctamente");
		
		return "redirect:/inicio/perfil";
	}

}
