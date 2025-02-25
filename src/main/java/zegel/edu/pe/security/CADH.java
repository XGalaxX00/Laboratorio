package zegel.edu.pe.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CADH implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		request.getSession().setAttribute("permissionMessage", "No tienes los permisos suficientes");
		
		String referer = request.getHeader("Referer");
		
		if (referer != null) {
			response.sendRedirect(referer);
		} else {
			response.sendRedirect("/inicio/perfil");
		}
		
	}

}
