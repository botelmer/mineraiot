package io.apiDevelopment.grupo2.proyectoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;

/**
 * Interfaz del repositorio para las entidades {@link Sensor}, que extiende {@link JpaRepository} de Spring Data JPA.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la tabla 'sensor'.
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{
	/**
     * Busca un objeto {@link Sensor} en la base de datos por su clave de API única.
     *
     * @param apiKey La clave de API del sensor a buscar.
     * @return Un {@link Optional} que contendrá el objeto {@link Sensor} si se encuentra
     * un sensor con la clave de API especificada, o un {@link Optional#empty()}
     * si no se encuentra ningún sensor con esa clave de API.
     */
	Optional<Sensor> findByApiKey(String apiKey);
	
	/**
     * Busca una lista de objetos {@link Sensor} en la base de datos que pertenecen a una
     * compañía específica, identificada por su clave de API.
     *
     * @param companyApiKey La clave de API de la compañía a la que pertenecen los sensores.
     * @return Una {@link List} de objetos {@link Sensor} asociados a la compañía con la
     * clave de API especificada. Devuelve una lista vacía si no se encuentran sensores
     * para esa compañía.
     */
	List<Sensor> findByLocationCompanyApiKey(String companyApiKey);
	
	/**
     * Busca un objeto {@link Sensor} en la base de datos por su ID y la clave de API de la
     * compañía a la que pertenece su ubicación.
     *
     * @param id            El ID del sensor a buscar.
     * @param companyApiKey La clave de API de la compañía a la que pertenece la ubicación del sensor.
     * @return Un {@link Optional} que contendrá el objeto {@link Sensor} si se encuentra
     * un sensor con el ID especificado y cuya ubicación pertenece a la compañía con la
     * clave de API dada, o un {@link Optional#empty()} si no se encuentra ningún sensor
     * que cumpla ambos criterios.
     */
	Optional<Sensor> findByIdAndLocationCompanyApiKey(Long id, String companyApiKey);
}
