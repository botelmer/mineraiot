package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filtro de seguridad personalizado que maneja la autenticación basada en API Key
 * para distintos tipos de endpoints de la aplicación.
 * <p>
 * Este filtro se ejecuta una única vez por solicitud HTTP, gracias a la extensión
 * de {@link OncePerRequestFilter}, y utiliza instancias de {@link ApiKeyAuthCompany}
 * y {@link ApiKeyAuthSensor} para validar y establecer la autenticación en el contexto
 * de seguridad de Spring.
 * </p>
 *
 * <p>Comportamiento por endpoint:</p>
 * <ul>
 *   <li><b>location</b> y <b>sensor</b>: autenticación con API Key de empresa.</li>
 *   <li><b>sensor_data</b> (GET): autenticación con API Key de empresa.</li>
 *   <li><b>sensor_data</b> (POST): autenticación con API Key de sensor.</li>
 * </ul>
 *
 * <p>Si la autenticación es exitosa, se establece en el {@link SecurityContextHolder}.
 * En todos los casos, la ejecución continúa con el resto de la cadena de filtros.</p>
 *
 * @see ApiKeyAuthCompany
 * @see ApiKeyAuthSensor
 * @see SecurityContextHolder
 * @see OncePerRequestFilter
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter{

	private final ApiKeyAuthCompany apiKeyCompany;
	private final ApiKeyAuthSensor apiKeySensor;
	
    /**
     * Lógica del filtro que determina si se debe aplicar autenticación por API Key
     * y delega en los componentes correspondientes para establecer el contexto de seguridad.
     *
     * @param request  la solicitud HTTP entrante.
     * @param response la respuesta HTTP que será enviada al cliente.
     * @param filterChain la cadena de filtros restante.
     * @throws ServletException si ocurre un error en el proceso del filtro.
     * @throws IOException si ocurre un error de entrada/salida.
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String endpoint = request.getRequestURL().toString().split("/")[5];
	
		if(endpoint.equals("location") || endpoint.equals("sensor") ||
		  (endpoint.equals("sensor_data") && request.getMethod().equals("GET"))) {		
			if(apiKeyCompany.getApiKey(request).isEmpty()) {
				SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthToken(""));
			}else {				
				apiKeyCompany.getApiKey(request)
				.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
			}
		}
		
		if(endpoint.equals("sensor_data") && request.getMethod().equals("POST")) {
			if(apiKeySensor.getApiKey(request).isEmpty()) {
				SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthToken(""));
			}else {				
				apiKeySensor.getApiKey(request)
				.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}

	
	
}
