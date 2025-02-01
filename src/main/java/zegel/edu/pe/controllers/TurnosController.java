package zegel.edu.pe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/turnos")
public class TurnosController {
	
	@GetMapping("/lista")
    public String listaTurnos(Model model) {
        return "turnos/enfrentamientos"; 
    }

}
