package io.apiDevelopment.grupo2.proyectoFinal.jms;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataRequest;
import io.apiDevelopment.grupo2.proyectoFinal.exception.BadRequestException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.NotFoundException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.UnauthorizedException;
import io.apiDevelopment.grupo2.proyectoFinal.security.ApiKeyFromConsumerFilter;
import io.apiDevelopment.grupo2.proyectoFinal.service.SensorDataService;
import lombok.RequiredArgsConstructor;

/**
 * Componente que actúa como consumidor de mensajes JMS desde la cola "testing" de ActiveMQ.
 * Recibe mensajes en formato JSON, los deserializa a un objeto {@link SensorDataRequest},
 * realiza validaciones básicas, autentica la solicitud utilizando la API Key proporcionada
 * y delega la creación de los datos de sensores al servicio {@link SensorDataService}.
 */
@Component
@RequiredArgsConstructor
public class ActiveMQConsumer {
	
	private final ApiKeyFromConsumerFilter apiKeyFromConsumerFilter;
	private final SensorDataService sensorDataService;
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class);
	
	/**
     * Listener JMS que escucha mensajes en la cola "testing".
     * Intenta deserializar el mensaje JSON a un objeto {@link SensorDataRequest},
     * valida la presencia de los atributos obligatorios, autentica la API Key
     * y persiste los datos del sensor utilizando el servicio correspondiente.
     * Captura y loguea excepciones específicas que puedan ocurrir durante el proceso.
     *
     * @param message El mensaje recibido desde la cola JMS en formato JSON.
     * @throws JsonMappingException Si ocurre un error durante el mapeo del JSON a la clase {@link SensorDataRequest}.
     * @throws JsonProcessingException Si ocurre un error durante el procesamiento del JSON.
     */
	@JmsListener(destination = "testing")
	public void messageConsumer(String message) throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			SensorDataRequest sensorDataRequest = objMapper.readValue(message, SensorDataRequest.class);
			
			validateSensorDataRequest(sensorDataRequest);
			
			String apiKey = sensorDataRequest.getApiKey();
			
			SecurityContextHolder.getContext().setAuthentication(apiKeyFromConsumerFilter.getAuthentication(apiKey));
			sensorDataService.createSensorData(sensorDataRequest);
		} catch (BadRequestException e) {
			logger.error(e.getMessage());
		} catch (UnauthorizedException e) {
			logger.error(e.getMessage());
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
		} catch (NumberFormatException  e) {
			logger.error(e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
     * Realiza validaciones básicas sobre el objeto {@link SensorDataRequest} recibido.
     * Verifica que los atributos 'api_key' y 'json_data' no sean nulos o estén vacíos.
     * Lanza una excepción {@link BadRequestException} si alguna de las validaciones falla.
     *
     * @param sensorDataRequest El objeto {@link SensorDataRequest} a validar.
     * @throws BadRequestException Si la 'api_key' o 'json_data' faltan o están vacíos.
     */
	private void validateSensorDataRequest(SensorDataRequest sensorDataRequest) {
		
		if(sensorDataRequest.getApiKey() == null || sensorDataRequest.getApiKey().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo api_key en el body o esta en blanco.");
		}
		
		if(sensorDataRequest.getJsonData() == null || sensorDataRequest.getJsonData().isEmpty()) {
			throw new BadRequestException("Error: Falta el atributo json_data en el body o esta vacio.");
		}
	}
}
