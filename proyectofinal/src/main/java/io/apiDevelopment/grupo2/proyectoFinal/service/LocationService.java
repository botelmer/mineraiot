package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.List;

import io.apiDevelopment.grupo2.proyectoFinal.dto.LocationDTO;

/**
 * Interfaz que define los servicios para la gestión de ubicaciones.
 * <p>
 * Esta interfaz declara los métodos que la capa de servicio de ubicaciones debe implementar.
 * Define las operaciones disponibles para la manipulación de la información de las ubicaciones,
 * abstrayendo la implementación concreta de la lógica de negocio.
 * </p>
 */
public interface LocationService {
	
	/**
	 * Obtiene todas las ubicaciones registradas en el sistema.
	 *
	 * @return Una lista de objetos {@link LocationDTO}, cada uno representando una ubicación.
	 * La lista estará vacía si no hay ubicaciones registradas.
	 */
	List<LocationDTO> getAllLocation();
	
	/**
	 * Obtiene una ubicación específica por su ID.
	 *
	 * @param id El identificador único de la ubicación que se desea obtener.
	 * @return Un objeto {@link LocationDTO} que representa la ubicación encontrada.
	 * Si no se encuentra ninguna ubicación con el ID proporcionado, se podría retornar {@code null}
	 * o lanzar una excepción, dependiendo de la implementación.
	 */
	LocationDTO getLocationById(Long id);
	
	/**
	 * Crea una nueva ubicación en el sistema.
	 *
	 * @param locationDTO El objeto {@link LocationDTO} que contiene los datos de la nueva ubicación a crear.
	 * No debe ser nulo y debe contener información válida.
	 * @return El objeto {@link LocationDTO} que representa la ubicación recién creada, incluyendo cualquier
	 * información generada por el sistema (como su ID).
	 */
	LocationDTO createLocation(LocationDTO locationDTO);
	
	/**
	 * Actualiza la información de una ubicación existente.
	 *
	 * @param id          El identificador único de la ubicación que se desea actualizar.
	 * @param locationDTO El objeto {@link LocationDTO} que contiene los nuevos datos para la ubicación.
	 * No debe ser nulo y debe contener información válida.
	 * @return El objeto {@link LocationDTO} que representa la ubicación actualizada.
	 * Si no se encuentra ninguna ubicación con el ID proporcionado, se podría retornar {@code null}
	 * o lanzar una excepción, dependiendo de la implementación.
	 */
	LocationDTO updateLocation(Long id, LocationDTO locationDTO);
	
	/**
	 * Elimina una ubicación del sistema por su ID.
	 *
	 * @param id El identificador único de la ubicación que se desea eliminar.
	 * @return Un mensaje {@code String} indicando el resultado de la operación de eliminación.
	 * Este mensaje podría incluir información sobre el éxito o el fallo de la eliminación.
	 * Si no se encuentra ninguna ubicación con el ID proporcionado, se podría indicar un fallo
	 * en la operación.
	 */
	String deleteLocation(Long id);
}
