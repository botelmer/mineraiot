package io.apiDevelopment.grupo2.proyectoFinal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataDTO;
import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataRequest;
import io.apiDevelopment.grupo2.proyectoFinal.service.SensorDataService;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de datos de sensores.
 * Expone endpoints para la creación y consulta de datos de sensores.
 */
@RestController
@RequestMapping("/api/v1/sensor_data")
@RequiredArgsConstructor
public class SensorDataController {
	private final SensorDataService sensorDataService;
	
	/**
     * Endpoint para crear nuevos datos de sensores.
     * Recibe un objeto {@link SensorDataRequest} en el cuerpo de la petición y delega
     * la creación de los datos al servicio {@link SensorDataService}.
     *
     * @param sensorDataRequest El objeto que contiene los datos de los sensores a crear.
     * @return Una respuesta {@link ResponseEntity} que contiene una lista de {@link SensorDataDTO}
     * creados y un estado HTTP {@code 201 Created}.
     */
	@PostMapping("/")
	public ResponseEntity<List<SensorDataDTO>> createSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
		return new ResponseEntity<List<SensorDataDTO>>(sensorDataService.createSensorData(sensorDataRequest), HttpStatus.CREATED);
	}
	
	/**
     * Endpoint para obtener datos de sensores dentro de un rango de tiempo específico
     * y para una lista de IDs de sensores.
     *
     * @param from      El timestamp de inicio del rango de tiempo (en segundos desde la época).
     * @param to        El timestamp de fin del rango de tiempo (en segundos desde la época).
     * @param sensorIds Una cadena de IDs de sensores separados por comas.
     * @return Una respuesta {@link ResponseEntity} que contiene una lista de {@link SensorDataDTO}
     * que cumplen con los criterios de búsqueda y un estado HTTP {@code 302 Found}.
     */
	@GetMapping("/")
	public ResponseEntity<List<SensorDataDTO>> getSensorDataByRangeAndIds(@RequestParam Long from, @RequestParam Long to, @RequestParam(value = "sensor_id") String sensorIds){
		return new ResponseEntity<List<SensorDataDTO>>(sensorDataService.getSensorDataByRangeAndIds(from, to, sensorIds), HttpStatus.FOUND);
	}
}
