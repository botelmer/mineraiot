package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Configuración de seguridad personalizada para la aplicación.
 * <p>
 * Esta clase utiliza Spring Security para definir cómo se gestionan las solicitudes HTTP
 * y se protegen los recursos de la aplicación. Se utiliza un filtro personalizado de
 * autenticación basado en API Key {@link ApiKeyAuthFilter} que es añadido antes del
 * filtro de autenticación predeterminado de nombre {@link UsernamePasswordAuthenticationFilter}.
 * </p>
 *
 * <p>La configuración de seguridad permite:</p>
 * <ul>
 *   <li>Deshabilitar la protección CSRF, que no es necesaria para solicitudes basadas en API.</li>
 *   <li>Permitir el acceso sin autenticación al endpoint <code>/api/v1/company/</code> para solicitudes POST.</li>
 *   <li>Exigir autenticación para todos los demás endpoints bajo <code>/api/**</code>.</li>
 * </ul>
 *
 * @see ApiKeyAuthFilter
 * @see UsernamePasswordAuthenticationFilter
 * @see HttpSecurity
 * @see SecurityFilterChain
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final ApiKeyAuthFilter authFilter;
	
    /**
     * Configura el {@link SecurityFilterChain} que se utilizará para gestionar las solicitudes HTTP.
     * <p>
     * Esta configuración define cómo se deben manejar las solicitudes de autenticación y autorización.
     * Se desactiva la protección CSRF, se permiten ciertas rutas sin autenticación y se agregan filtros
     * personalizados para la autenticación por API Key.
     * </p>
     *
     * @param http la configuración de seguridad HTTP de Spring Security.
     * @return un {@link SecurityFilterChain} configurado.
     * @throws Exception si ocurre un error durante la configuración de seguridad.
     */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(httpRequest -> {
				httpRequest.requestMatchers(HttpMethod.POST, "/api/v1/company/").permitAll();
				httpRequest.requestMatchers("/api/**").authenticated();
			})
			.exceptionHandling(exceptionHandler -> {
				exceptionHandler.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					String errorJson = String.format("{\"message\": \"Acceso denegado: No tienes permisos para acceder a este recurso.\", \"timestamp\": \"%s\"}", LocalDateTime.now());
					response.getWriter().write(errorJson);
				});
			})
			.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
