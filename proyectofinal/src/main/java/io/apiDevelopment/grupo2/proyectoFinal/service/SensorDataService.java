package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.List;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataDTO;
import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDataRequest;

/**
 * Interfaz que define las operaciones para la gestión de datos de sensores.
 * Proporciona métodos para la creación y consulta de información de sensores.
 */
public interface SensorDataService {
	/**
     * Crea nuevos registros de datos de sensores a partir de la información proporcionada
     * en el objeto {@link SensorDataRequest}.
     *
     * @param sensorDataRequest El objeto que contiene los datos de los sensores a crear.
     * @return Una lista de {@link SensorDataDTO} que representan los datos de sensores creados.
     */
	List<SensorDataDTO> createSensorData(SensorDataRequest sensorDataRequest);
	
	/**
     * Obtiene una lista de datos de sensores dentro de un rango de tiempo específico
     * y para los sensores identificados por la cadena proporcionada.
     *
     * @param from    El timestamp de inicio del rango de tiempo (en segundos desde la época).
     * @param to      El timestamp de fin del rango de tiempo (en segundos desde la época).
     * @param sensors Una cadena que contiene los IDs de los sensores separados por algún delimitador (ej., coma).
     * @return Una lista de {@link SensorDataDTO} que cumplen con los criterios de búsqueda.
     */
	List<SensorDataDTO> getSensorDataByRangeAndIds(Long from, Long to, String sensors);
}
