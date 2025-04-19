package io.apiDevelopment.grupo2.proyectoFinal.jms;

import java.nio.charset.StandardCharsets;

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
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
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
	private final ObjectMapper objMapper = new ObjectMapper();
	private byte[] bytes = new byte[1024];
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
	 * @throws JMSException 
     */
	@JmsListener(destination = "tf-minera-01")
	public void messageConsumer(BytesMessage bytesMessage) throws JsonMappingException, JsonProcessingException, JMSException {
		try {
			String message = convertBytesToString(bytesMessage);
			System.out.println(message);
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
	 * Convierte el contenido de un {@link BytesMessage} a un {@link String} utilizando la codificación UTF-8.
	 * Para optimizar el rendimiento, este método reutiliza un buffer de bytes interno. Si la longitud
	 * del mensaje entrante excede la capacidad del buffer actual, se crea un nuevo buffer
	 * del tamaño necesario.
	 *
	 * @param bytesMessage El mensaje de bytes JMS del cual se leerá el contenido.
	 * @return El contenido del mensaje de bytes como un {@link String} decodificado en UTF-8.
	 * @throws JMSException Si ocurre un error al acceder al contenido del mensaje de bytes.
	 */
	private String convertBytesToString(BytesMessage bytesMessage) throws JMSException  {
		bytesMessage.reset();
		int bytesLength = (int) bytesMessage.getBodyLength();
		
		if(bytesLength > bytes.length) {
			bytes = new byte[bytesLength];
		}
		
		bytesMessage.readBytes(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
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
