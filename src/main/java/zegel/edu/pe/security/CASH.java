package zegel.edu.pe.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CASH implements AuthenticationSuccessHandler{

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        String redirectUrl = "/default";
        
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("juez")) {
            	redirectUrl = "/resultados/mostrar";
                break;
            } else if (authority.getAuthority().equals("admin")) {
            	redirectUrl = "/eventos/registro";
                break;
            } else if (authority.getAuthority().equals("competidor")) {
                redirectUrl = "/inicio/inicio";
                break;
            }
            
        }        
        response.sendRedirect(redirectUrl);
    }
}
