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

import io.apiDevelopment.grupo2.proyectoFinal.dto.LocationDTO;
import io.apiDevelopment.grupo2.proyectoFinal.service.LocationService;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar las operaciones relacionadas con ubicaciones (Locations).
 * <p>
 * Este controlador expone endpoints bajo el prefijo <code>/api/v1/location</code> para realizar
 * operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las ubicaciones de una empresa.
 * </p>
 *
 * <p>Se utiliza {@link LocationService} para delegar la lógica de negocio.</p>
 *
 */
@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
	private final LocationService locationService;
	
	
	
    /**
     * Obtiene una lista de todas las ubicaciones registradas.
     *
     * @return una {@link ResponseEntity} con la lista de {@link LocationDTO}
     *         y el código de estado HTTP 302 (FOUND).
     */
	@GetMapping("/")
	public ResponseEntity<List<LocationDTO>> getLocations(){
		return new ResponseEntity<List<LocationDTO>>(locationService.getAllLocation(), HttpStatus.FOUND);
	}
	/**
     * Obtiene los datos de una ubicación específica por su ID.
     *
     * @param id el identificador único de la ubicación.
     * @return una {@link ResponseEntity} con el {@link LocationDTO} correspondiente
     *         y el código de estado HTTP 302 (FOUND).
     */
	@GetMapping("/{id}")
	public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id){
		return new ResponseEntity<LocationDTO>(locationService.getLocationById(id), HttpStatus.FOUND);
	}
	
	/**
     * Crea una nueva ubicación en el sistema.
     *
     * @param locationDTO los datos de la ubicación a crear.
     * @return una {@link ResponseEntity} con el {@link LocationDTO} creado
     *         y el código de estado HTTP 201 (CREATED).
     */
	@PostMapping("/")
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO){
		return new ResponseEntity<LocationDTO>(locationService.createLocation(locationDTO), HttpStatus.CREATED);
	}
	
    /**
     * Actualiza los datos de una ubicación existente identificada por su ID.
     *
     * @param locationDTO los nuevos valores de la ubicación.
     * @param id el identificador de la ubicación a actualizar.
     * @return una {@link ResponseEntity} con el {@link LocationDTO} actualizado
     *         y el código de estado HTTP 200 (OK).
     */
	@PutMapping("/{id}")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO, @PathVariable Long id){
		return new ResponseEntity<LocationDTO>(locationService.updateLocation(id, locationDTO), HttpStatus.OK);
	}
	
	 /**
     * Elimina una ubicación específica del sistema.
     *
     * @param id el identificador de la ubicación a eliminar.
     * @return una {@link ResponseEntity} con un mensaje de confirmación
     *         y el código de estado HTTP 200 (OK).
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLocation(@PathVariable Long id){
		return new ResponseEntity<String>(locationService.deleteLocation(id), HttpStatus.OK);
	}
	
}
