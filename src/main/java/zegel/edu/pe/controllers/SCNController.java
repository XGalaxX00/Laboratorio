package zegel.edu.pe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.models.Categorias;
import zegel.edu.pe.models.Niveles;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.services.CategoriasServices;
import zegel.edu.pe.services.NivelesServices;
import zegel.edu.pe.services.SedesServices;

@Controller
@RequestMapping("/scn")
public class SCNController {	
	@Autowired
	private SedesServices sedS;
	
	@Autowired
	private CategoriasServices catS;
	
	@Autowired
	private NivelesServices nivS;
	
//Sedes
	@GetMapping("/lista")
	public String listarSCN(Model model) {
				
		List<Sedes> listarSed = sedS.getListarSedes();
		List<Categorias> listarCat = catS.getListarCategorias();
		List<Niveles> listarNiv = nivS.getListarNiveles();
		
		Sedes sed = new Sedes();
		Categorias cat = new Categorias();
		Niveles niv = new Niveles();
				
		model.addAttribute("listarSedes", listarSed);
		model.addAttribute("nuevoSede", sed);
		
		model.addAttribute("listarCate", listarCat);
		model.addAttribute("nuevoCat", cat);
		
		model.addAttribute("listarNiv", listarNiv);
		model.addAttribute("nuevoNiv", niv);
		
		return "sedes-categorias-niveles/index";	
	}
	
	
//Método para guardar Sede
	@PostMapping("/guardarSedes")
	public String guardarSedes(@ModelAttribute("sede") Sedes sed, RedirectAttributes redirectAttributes) {
		sedS.guardarSedes(sed);
		redirectAttributes.addFlashAttribute("success", "Registro Exitoso");
		return "redirect:/scn/lista";	
	}
	
//Obtener el ID de la sede
	@GetMapping("/sedes/id/{id}")
	public ResponseEntity<Sedes> getSedesId(@PathVariable("id") int id) { 
		Sedes sede = sedS.getListSedesId(id);
		if (sede != null) {
			return ResponseEntity.ok(sede);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
//Método editar Sede
	@PostMapping("/sedes/update")
	public String updateSede(Sedes sede) {
		
		Sedes sedeExist = sedS.getListSedesId(sede.getId());
		sedeExist.setNombre(sede.getNombre());
		
		sedS.guardarSedes(sedeExist);
		
		return "redirect:/scn/lista";
	}
	
	
//Método para guardar Categoria	
	@PostMapping("/guardarCategorias")
	public String guardar(Categorias cat, RedirectAttributes redirectAttributes) {
		catS.guardarCategorias(cat);
		redirectAttributes.addFlashAttribute("success", "Registro Exitoso");
		return "redirect:/scn/lista";	
	}
	
//Método para el ID Categoria
	@GetMapping("/categorias/id/{id}")
	public ResponseEntity<Categorias> getCategoriaId(@PathVariable("id") int id) { 
		Categorias cate = catS.getListCategoriasId(id);
		if (cate != null) {
			return ResponseEntity.ok(cate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
//Método editar categoria
	@PostMapping("/categorias/update")
	public String updateCategoria(Categorias categorias) {
		
		Categorias cateExist = catS.getListCategoriasId(categorias.getId());
		cateExist.setNombre(categorias.getNombre());
		
		catS.guardarCategorias(cateExist);
		
		return "redirect:/scn/lista";
	}
	
//Método para guardar Niveles
	@PostMapping("/guardarNiveles")
	public String guardarNiveles(Niveles niv, RedirectAttributes redirectAttributes) {
		nivS.guardarNiveles(niv);
		redirectAttributes.addFlashAttribute("success", "Registro Exitoso");
		return "redirect:/scn/lista";	
	}
	
//Método para el ID de Niveles
	@GetMapping("/niveles/id/{id}")
	public ResponseEntity<Niveles> getNivelesId(@PathVariable("id") int id){
		Niveles nive = nivS.getListNivelesId(id);
		if (nive != null) {
			return ResponseEntity.ok(nive);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
//Método editar Niveles
	@PostMapping("/niveles/update")
	public String updateNiveles(Niveles niveles) {
		
		Niveles nivExist = nivS.getListNivelesId(niveles.getId());
		nivExist.setNombre(niveles.getNombre());
		
		nivS.guardarNiveles(nivExist);
		
		return "redirect:/scn/lista";
	}
	
}
