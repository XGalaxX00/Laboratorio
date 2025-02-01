package zegel.edu.pe.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/inicio")
public class VComController {	

	@GetMapping("/inicio")
	public String incio(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "VCom/index";
	}
	
	@GetMapping("/categorias")
	public String inicioCategorias(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "VCom/descripcion_categoria";
	}
	
	@GetMapping("/niveles")
	public String inicioNiveles(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "VCom/descripcion_niveles";
	}
	
	@GetMapping("/sedes")
	public String inicioSedes(HttpServletRequest request, Model modelo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "VCom/PaginaPrincipal-Sedes";
	}

}
