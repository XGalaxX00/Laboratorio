package zegel.edu.pe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Security {

	private final CASH successHandler;
	
	private final CADH accessDeniedHandler;
	
	public Security(CASH successHandler, CADH accessDeniedHandler) {
        this.successHandler = successHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }
	
	
	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
        http.authorizeHttpRequests((auth) -> auth
        		
        		.requestMatchers("/login/**","/usuarios/registro","/inicio/inicio", "/inicio/categorias",
        				"/inicio/niveles","/inicio/sedes","/inicio/noticias", "/turnos/registrar-ganadores","/turnos/registrarGanadoresSemifinal")
                .permitAll()
                .requestMatchers("/inicio/**").hasAnyAuthority("competidor","admin")
                .requestMatchers("/usuarios/**","/scn/**","/eventos/**",
                		"/competiciones/**","/turnos/**","/clubes/**","/resultados/**","/inicio/**").hasAuthority("admin")
                .requestMatchers("/resultados/mostrar").hasAuthority("juez")
                .anyRequest().authenticated()
        )
        .formLogin((login) -> login
        		.loginPage("/login")
        		.successHandler(successHandler)	
                .failureUrl("/login?error=true")
                .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/inicio/inicio?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
				.exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler));
            return http.build();
        }
	
	@Bean
	WebSecurityCustomizer webCus() {
		return (web) -> web.ignoring().requestMatchers("/css/*", "/imagen/*", "/script/*", "/Img-com/*");
	}
}