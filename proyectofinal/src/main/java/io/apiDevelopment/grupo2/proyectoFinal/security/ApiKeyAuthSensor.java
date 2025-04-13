package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.apiDevelopment.grupo2.proyectoFinal.exception.UnauthorizedException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;
import io.apiDevelopment.grupo2.proyectoFinal.repository.SensorRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Componente de seguridad encargado de autenticar solicitudes provenientes de sensores
 * mediante una API Key proporcionada en la cabecera HTTP.
 * <p>
 * Este componente busca la clave proporcionada en el repositorio de sensores
 * y, si la encuentra válida, genera un token de autenticación sin autoridades asignadas.
 * </p>
 *
 * <p>Se utiliza principalmente para validar solicitudes POST a recursos como <code>/sensor_data</code>.</p>
 *
 * <p>Ejemplo de cabecera esperada:</p>
 * <pre>
 *     POST /api/v1/sensor_data/
 *     Headers:
 *     API-KEY: sensor-abc123
 * </pre>
 *
 * @see Sensor
 * @see ApiKeyAuthToken
 * @see SensorRepository
 * @see org.springframework.security.core.Authentication
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthSensor {
	private final SensorRepository sensorRepository;
	
    /**
     * Extrae la API Key de la cabecera <code>API-KEY</code> de la solicitud HTTP
     * y valida su existencia en el sistema.
     *
     * @param request la solicitud HTTP entrante.
     * @return un {@link Optional} conteniendo el {@link Authentication} generado si la clave es válida;
     *         o vacío si no se encuentra un sensor con dicha clave.
     */
	public Optional<Authentication> extract(HttpServletRequest request){
		
		String providedKey = request.getHeader("API-KEY");
		
		Optional<Sensor> apiKey = sensorRepository.findByApiKey(providedKey);
		
		if(apiKey.isEmpty()) {
			throw new UnauthorizedException("Api key no autorizada.");
		}
		
		return Optional.of(new ApiKeyAuthToken(apiKey.get().getApiKey(), AuthorityUtils.NO_AUTHORITIES));
	}
}
