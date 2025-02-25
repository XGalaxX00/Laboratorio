package zegel.edu.pe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        return "login"; // Nombre de tu archivo HTML
    }
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "redirect:/inicio/inicio?logout=true";
	}

}