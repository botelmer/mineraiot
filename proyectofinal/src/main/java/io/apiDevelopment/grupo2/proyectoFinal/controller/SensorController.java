package io.apiDevelopment.grupo2.proyectoFinal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDTO;
import io.apiDevelopment.grupo2.proyectoFinal.service.SensorService;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de sensores.
 * <p>
 * Expone endpoints para crear, obtener, actualizar y eliminar información de los sensores.
 * Todas las rutas base de este controlador comienzan con "/api/v1/sensor".
 */
@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
public class SensorController {
	private final SensorService sensorService;
	
	/**
	 * Crea un nuevo sensor.
	 * <p>
	 * Este endpoint recibe un objeto {@link SensorDTO} en el cuerpo de la petición y lo persiste
	 * a través del servicio {@link SensorService}.
	 *
	 * @param sensor El objeto {@link SensorDTO} que representa el sensor a crear. No debe ser nulo.
	 * @return {@link ResponseEntity} con un mensaje indicando el resultado de la creación y un código de estado HTTP {@link HttpStatus#CREATED} si la creación fue exitosa.
	 */
	@PostMapping("/")
	public ResponseEntity<String> createSensor(@RequestBody SensorDTO sensor){
		return new ResponseEntity<String>(sensorService.createSensor(sensor), HttpStatus.CREATED); 
	}
	
	
	/**
	 * Obtiene todos los sensores registrados.
	 * <p>
	 * Este endpoint devuelve una lista de todos los objetos {@link SensorDTO} almacenados en el sistema.
	 *
	 * @return {@link ResponseEntity} con una lista de {@link SensorDTO} y un código de estado HTTP {@link HttpStatus#OK}.
	 * Si no hay sensores registrados, la lista estará vacía.
	 */
	@GetMapping("/")
	public ResponseEntity<List<SensorDTO>> getAllSensor(){
		return new ResponseEntity<List<SensorDTO>>(sensorService.getAllSensor(), HttpStatus.OK); 
	}
	
	
	/**
	 * Obtiene un sensor específico por su ID.
	 * <p>
	 * Este endpoint busca un sensor en base al ID proporcionado en la ruta.
	 *
	 * @param id El identificador único del sensor que se desea obtener. Debe ser un valor numérico.
	 * @return {@link ResponseEntity} con el objeto {@link SensorDTO} correspondiente al ID proporcionado y un código de estado HTTP {@link HttpStatus#OK} si el sensor existe.
	 * Si el sensor no se encuentra, se retornará un código de estado HTTP {@link HttpStatus#NOT_FOUND} (implementado en la capa de servicio).
	 */
	@GetMapping("/{id}")
	public ResponseEntity<SensorDTO> getSensorById(@PathVariable Long id){
		return new ResponseEntity<SensorDTO>(sensorService.getSensorById(id), HttpStatus.OK); 
	}
	
	/**
	 * Actualiza la información de un sensor existente.
	 * <p>
	 * Este endpoint recibe el ID del sensor a actualizar a través de la ruta y un objeto {@link SensorDTO}
	 * en el cuerpo de la petición con los datos modificados.
	 *
	 * @param id     El identificador único del sensor que se desea actualizar. Debe ser un valor numérico.
	 * @param sensor El objeto {@link SensorDTO} que contiene los nuevos datos del sensor. No debe ser nulo.
	 * @return {@link ResponseEntity} con el objeto {@link SensorDTO} actualizado y un código de estado HTTP {@link HttpStatus#OK} si la actualización fue exitosa.
	 * Si el sensor con el ID proporcionado no existe, se retornará un código de estado HTTP {@link HttpStatus#NOT_FOUND} (implementado en la capa de servicio).
	 */
	@PutMapping("/{id}")
	public ResponseEntity<SensorDTO> updateSensor(@PathVariable Long id, @RequestBody SensorDTO sensor){
		return new ResponseEntity<SensorDTO>(sensorService.updateSensor(id, sensor), HttpStatus.OK); 
	}
	
	/**
	 * Elimina un sensor por su ID.
	 * <p>
	 * Este endpoint elimina el sensor cuyo ID coincide con el proporcionado en la ruta.
	 *
	 * @param id El identificador único del sensor que se desea eliminar. Debe ser un valor numérico.
	 * @return {@link ResponseEntity} con un mensaje indicando el resultado de la eliminación y un código de estado HTTP {@link HttpStatus#OK} si la eliminación fue exitosa.
	 * Si el sensor con el ID proporcionado no existe, se retornará un código de estado HTTP {@link HttpStatus#NOT_FOUND} (implementado en la capa de servicio).
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSensor(@PathVariable Long id){
		return new ResponseEntity<String>(sensorService.deleteSensor(id), HttpStatus.OK); 
	}
}
