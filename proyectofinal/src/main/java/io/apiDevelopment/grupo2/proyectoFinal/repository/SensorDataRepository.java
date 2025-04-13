package io.apiDevelopment.grupo2.proyectoFinal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.apiDevelopment.grupo2.proyectoFinal.model.SensorData;

/**
 * Interfaz del repositorio para las entidades {@link SensorData}, que extiende {@link JpaRepository} de Spring Data JPA.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la tabla 'sensor_data'.
 */
@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long>{
	
	/**
     * Encuentra una lista de entidades {@link SensorData} registradas dentro de un rango de fechas específico,
     * para una lista dada de IDs de sensores y pertenecientes a una compañía específica.
     *
     * @param startDate El {@link LocalDateTime} de inicio (inclusive) del rango de fechas.
     * @param endDate   El {@link LocalDateTime} de fin (inclusive) del rango de fechas.
     * @param sensorIds Una {@link List} de IDs de sensores por los cuales filtrar los datos.
     * @param companyId El ID de la compañía cuyos datos de sensores se están consultando.
     * @return Una {@link List} de entidades {@link SensorData} que se encuentran dentro del rango de
     * fechas especificado, pertenecen a uno de los sensores especificados y están asociados
     * con el ID de compañía dado. Devuelve una lista vacía si no se encuentran datos coincidentes.
     */
	List<SensorData> findByDatetimeBetweenAndSensorIdInAndSensorLocationCompanyId(LocalDateTime startDate, LocalDateTime endDate, List<Integer> sensorIds, Long companyId);
}
