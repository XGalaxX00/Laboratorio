package zegel.edu.pe.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import jakarta.transaction.Transactional;
import zegel.edu.pe.models.Tipo_Usuario;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.UsuariosServices;

@Component
public class FormLogin implements UserDetailsService{
	
	@Autowired
	private UsuariosServices usuarioService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuarios usua = usuarioService.CorreoUsuario(username);
		
		if(usua == null) {
			throw new UsernameNotFoundException("No existe el usuario");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		Tipo_Usuario tipo = usua.getTipo_usuario();
		
		if (tipo != null) {
			authorities.add(new SimpleGrantedAuthority(tipo.getNombre()));
        } else {
            throw new UsernameNotFoundException("No tiene los permisos necesarios para ingresar");
        }
        return new User(usua.getCorreo(), usua.getContrasena(), authorities);
		
	}

}
