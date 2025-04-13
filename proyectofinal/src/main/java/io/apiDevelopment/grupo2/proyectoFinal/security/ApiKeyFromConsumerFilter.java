package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.apiDevelopment.grupo2.proyectoFinal.exception.UnauthorizedException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;
import io.apiDevelopment.grupo2.proyectoFinal.repository.SensorRepository;
import lombok.RequiredArgsConstructor;

/**
 * Componente de Spring que actúa como un filtro para extraer y validar la API Key
 * proporcionada por un consumidor (por ejemplo, a través de un encabezado HTTP o parámetro).
 * Utiliza el {@link SensorRepository} para buscar un sensor asociado a la API Key.
 */
@Component
@RequiredArgsConstructor
public class ApiKeyFromConsumerFilter {
	private final SensorRepository sensorRepository;
	
	/**
     * Intenta obtener una autenticación basada en la API Key proporcionada.
     * Busca un sensor en la base de datos utilizando la API Key.
     * Si no se encuentra ningún sensor con la API Key dada, lanza una excepción
     * {@link UnauthorizedException} indicando que la clave no está autorizada.
     * Si se encuentra un sensor, crea un token de autenticación {@link ApiKeyAuthToken}
     * con la API Key.
     *
     * @param apiKey La API Key proporcionada por el consumidor.
     * @return Un objeto {@link Authentication} ({@link ApiKeyAuthToken}) si la API Key es válida.
     * @throws UnauthorizedException Si no se encuentra ningún sensor asociado a la API Key proporcionada.
     */
	public Authentication getAuthentication(String apiKey) {
		Optional<Sensor> sensor = sensorRepository.findByApiKey(apiKey);
		
		if(sensor.isEmpty()) {
			throw new UnauthorizedException("Api key no autorizada.");
		}
		
		return new ApiKeyAuthToken(apiKey);
	}
}
