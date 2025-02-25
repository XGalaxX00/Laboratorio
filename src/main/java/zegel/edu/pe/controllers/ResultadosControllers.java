package zegel.edu.pe.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/resultados")
public class ResultadosControllers {

	@GetMapping("/mostrar")
	public String mostrarResultados(HttpServletRequest request, Model modelo){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "resultados/mostrar_resultados";
	}
	
	@GetMapping("/registrar")
	public String registrarResultados(HttpServletRequest request, Model modelo){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		modelo.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
		modelo.addAttribute("currentUri", request.getRequestURI());
		return "resultados/registrar_resultados";
	}
}
