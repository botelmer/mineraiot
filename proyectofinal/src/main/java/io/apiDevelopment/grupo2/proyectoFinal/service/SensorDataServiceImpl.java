package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataDTO;
import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataRequest;
import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorInputDTO;
import io.apiDevelopment.grupo2.proyectoFinal.exception.BadRequestException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.NotFoundException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Company;
import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;
import io.apiDevelopment.grupo2.proyectoFinal.model.SensorData;
import io.apiDevelopment.grupo2.proyectoFinal.repository.CompanyRepository;
import io.apiDevelopment.grupo2.proyectoFinal.repository.SensorDataRepository;
import io.apiDevelopment.grupo2.proyectoFinal.repository.SensorRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio {@link SensorDataService} para la gestión de datos de sensores.
 * Contiene la lógica de negocio para la creación y consulta de datos de sensores,
 * incluyendo la validación de entrada y la interacción con los repositorios.
 */
@Service
@RequiredArgsConstructor
public class SensorDataServiceImpl implements SensorDataService{
	private final SensorDataRepository sensorDataRepository;
	private final SensorRepository sensorRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	/**
     * Crea nuevos registros de datos de sensores a partir de la información proporcionada
     * en el objeto {@link SensorDataRequest}.
     * Valida la existencia del sensor asociado a la API Key en el contexto de seguridad
     * y los datos individuales de cada lectura del sensor antes de persistirlos.
     *
     * @param sensorDataRequest El objeto que contiene los datos de los sensores a crear.
     * @return Una lista de {@link SensorDataDTO} que representan los datos de sensores creados.
     * @throws NotFoundException Si no se encuentra el sensor asociado a la API Key.
     * @throws BadRequestException Si faltan atributos obligatorios en los datos del sensor.
     */
	@Override
	public List<SensorDataDTO> createSensorData(SensorDataRequest sensorDataRequest) {
		List<SensorData> sensorDataList = new ArrayList<SensorData>();
		
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		Optional<Sensor> sensor = sensorRepository.findByApiKey(apiKey);
		
		if(sensor.isEmpty()) {
			throw new NotFoundException("Sensor no encontrado");
		}
		
		for(SensorInputDTO sensorInputDTO : sensorDataRequest.getJsonData()) {
			validateSensorDataDTO(sensorInputDTO);
			
			SensorData sensorData = new SensorData(sensorInputDTO);
			sensorData.setSensor(sensor.get());
			sensorDataList.add(sensorData);
		}
		
		sensorDataRepository.saveAll(sensorDataList);
		
		return sensorDataList.stream().map((sensorData) -> new SensorDataDTO(sensorData)).toList();
	}

	/**
     * Obtiene una lista de datos de sensores dentro de un rango de tiempo específico
     * y para los sensores identificados por la cadena proporcionada.
     * Valida la existencia de la compañía asociada a la API Key en el contexto de seguridad
     * y verifica que existan registros de sensores para los IDs proporcionados y la compañía.
     *
     * @param from      El timestamp de inicio del rango de tiempo (en segundos desde la época).
     * @param to        El timestamp de fin del rango de tiempo (en segundos desde la época).
     * @param sensorIds Una cadena que contiene los IDs de los sensores separados por comas.
     * @return Una lista de {@link SensorDataDTO} que cumplen con los criterios de búsqueda.
     * @throws NotFoundException Si no se encuentra la compañía asociada a la API Key
     * o si no hay registros de sensores para los IDs y la compañía especificados.
     */
	@Override
	public List<SensorDataDTO> getSensorDataByRangeAndIds(Long from, Long to, String sensorIds) {
		LocalDateTime dateTimeFrom = transformEpochToLocalDateTime(from);
		LocalDateTime dateTimeTo = transformEpochToLocalDateTime(to);
		
		List<Integer> sensorIdList = stringToList(sensorIds);
		
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		System.out.println(apiKey);
		Optional<Company> company = companyRepository.findByApiKey(apiKey);
		
		if(company.isEmpty()) {
			throw new NotFoundException("Compañia no encontrada.");
		}
		
		List<SensorData> sensorDataList = sensorDataRepository.findByDatetimeBetweenAndSensorIdInAndSensorLocationCompanyId(dateTimeFrom, dateTimeTo, sensorIdList, company.get().getId());
		
		if(sensorDataList.isEmpty()){
			throw new NotFoundException("No hay registros del sensor o sensor no esta relacionado a la compañia.");
		}
		
		return sensorDataList.stream().map((sensorData) -> new SensorDataDTO(sensorData)).toList();
	}

	/**
     * Convierte una cadena de IDs de sensores separados por comas a una lista de enteros.
     *
     * @param str La cadena de IDs de sensores.
     * @return Una lista de enteros representando los IDs de los sensores.
     */
	private List<Integer> stringToList(String str){
		List<Integer> list = new ArrayList<Integer>();
		for(String aux : str.split(",")) {
			list.add(Integer.parseInt(aux));
		}
		return list;
	}
	
	/**
     * Convierte un timestamp de época (en segundos) a un objeto {@link LocalDateTime}
     * con la zona horaria de América/Santiago.
     *
     * @param epoch El timestamp de época a convertir.
     * @return El objeto {@link LocalDateTime} resultante.
     */
	private LocalDateTime transformEpochToLocalDateTime(Long epoch) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.of("America/Santiago"));
	}
	
	/**
     * Valida los atributos obligatorios del objeto {@link SensorInputDTO}.
     * Lanza una excepción {@link BadRequestException} si el 'datetime' falta o
     * si el mapa de 'data' es nulo o está vacío.
     *
     * @param sensorInputDTO El objeto {@link SensorInputDTO} a validar.
     * @throws BadRequestException Si faltan atributos obligatorios.
     */
	private void validateSensorDataDTO(SensorInputDTO sensorInputDTO) {
		
		if(sensorInputDTO.getDatetime() == null) {
			throw new BadRequestException("Error: Falta el atributo datetime en el body.");
		}
		
		if(sensorInputDTO.getData() == null || sensorInputDTO.getData().isEmpty()) {
			throw new BadRequestException("Error: No se encuentran atributos para registrar.");
		}
		
	}
}
