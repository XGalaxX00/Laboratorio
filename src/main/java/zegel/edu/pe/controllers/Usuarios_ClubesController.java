package zegel.edu.pe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zegel.edu.pe.dao.IUsuarios_ClubesDao;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.models.Usuarios_Clubes;
import zegel.edu.pe.services.ClubesServices;
import zegel.edu.pe.services.UsuariosServices;
import zegel.edu.pe.services.Usuarios_ClubesServices;

@Controller
@RequestMapping("/usuarioclub")
public class Usuarios_ClubesController {
	@Autowired
	private UsuariosServices usuS;
	@Autowired
	private ClubesServices cluS;
	@Autowired
	private Usuarios_ClubesServices ucS;
	@Autowired
	private IUsuarios_ClubesDao ucDao;
	
	@PostMapping("/clubes/{clubes_id}")
	public String inscribirAClub(@PathVariable Integer clubes_id, RedirectAttributes redirectAttributes) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    Usuarios usuarioAutenticado = usuS.CorreoUsuario(username);

	    if (usuarioAutenticado == null) {
	        return "redirect:/login";
	    }

	    Integer usuarios_id = usuarioAutenticado.getId();
	    Optional<Usuarios_Clubes> existente = ucDao.findByUsuarios_id(usuarios_id);

	    if (existente.isPresent()) {
	        redirectAttributes.addFlashAttribute("error", "¡Ya estás inscrito en un club!");
	        return "redirect:/inicio/clubes";  // Redirige a la misma página con el mensaje de error
	    }

	    ucS.actualizarUsuarioPorClub(usuarios_id, clubes_id);
	    redirectAttributes.addFlashAttribute("success", "Registro Exitoso");
	    return "redirect:/inicio/clubes";
	}

	
	@GetMapping("/{clubes_id}/usuarios")
    @ResponseBody
    public List<Usuarios> obtenerUsuarios(@PathVariable Integer clubes_id) {
        return ucS.obtenerUsuariosDeClub(clubes_id);
    }
	
}
