package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.List;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDTO;

/**
 * Interfaz que define los servicios para la gestión de sensores.
 * <p>
 * Esta interfaz declara los métodos que la capa de servicio de sensores debe implementar.
 * Define las operaciones disponibles para la manipulación de la información de los sensores,
 * abstrayendo la implementación concreta de la lógica de negocio.
 * </p>
 */
public interface SensorService {
	
	/**
	 * Obtiene todos los sensores registrados en el sistema para la compañía autenticada.
	 *
	 * @return Una lista de objetos {@link SensorDTO}, cada uno representando un sensor.
	 * La lista estará vacía si no hay sensores registrados para la compañía.
	 */
	List<SensorDTO> getAllSensor();
	
	/**
	 * Obtiene un sensor específico por su ID, verificando que pertenezca a la compañía autenticada.
	 *
	 * @param id El identificador único del sensor que se desea obtener.
	 * @return Un objeto {@link SensorDTO} que representa el sensor encontrado.
	 * Si no se encuentra ningún sensor con el ID proporcionado o si no pertenece a la compañía,
	 * se podría retornar {@code null} o lanzar una excepción, dependiendo de la implementación.
	 */
	SensorDTO getSensorById(Long id);
	
	/**
	 * Crea un nuevo sensor en el sistema.
	 *
	 * @param sensorDTO El objeto {@link SensorDTO} que contiene los datos del sensor a crear.
	 * No debe ser nulo y debe contener información válida.
	 * @return Un mensaje {@code String} indicando el resultado de la operación de creación.
	 * Este mensaje podría incluir información sobre el éxito o el fallo de la creación.
	 */
	String createSensor(SensorDTO sensorDTO);
	
	/**
	 * Actualiza la información de un sensor existente, verificando que pertenezca a la compañía autenticada.
	 *
	 * @param id           El identificador único del sensor que se desea actualizar.
	 * @param newSensorDTO El objeto {@link SensorDTO} que contiene los nuevos datos para el sensor.
	 * No debe ser nulo y debe contener información válida.
	 * @return El objeto {@link SensorDTO} que representa el sensor actualizado.
	 * Si no se encuentra ningún sensor con el ID proporcionado o si no pertenece a la compañía,
	 * se podría retornar {@code null} o lanzar una excepción, dependiendo de la implementación.
	 */
	SensorDTO updateSensor(Long id, SensorDTO newSensorDTO);
	
	/**
	 * Elimina un sensor del sistema por su ID, verificando que pertenezca a la compañía autenticada.
	 *
	 * @param id El identificador único del sensor que se desea eliminar.
	 * @return Un mensaje {@code String} indicando el resultado de la operación de eliminación.
	 * Este mensaje podría incluir información sobre el éxito o el fallo de la eliminación.
	 * Si no se encuentra ningún sensor con el ID proporcionado o si no pertenece a la compañía,
	 * se podría indicar un fallo en la operación.
	 */
	String deleteSensor(Long id);
}
