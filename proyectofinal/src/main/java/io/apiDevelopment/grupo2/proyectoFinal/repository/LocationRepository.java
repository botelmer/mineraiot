package io.apiDevelopment.grupo2.proyectoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.apiDevelopment.grupo2.proyectoFinal.model.Location;

/**
 * Interfaz del repositorio para las entidades {@link Location}, que extiende {@link JpaRepository} de Spring Data JPA.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la tabla 'location'.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
	/**
     * Busca un objeto {@link Location} en la base de datos por su ID y la clave de API de la compañía a la que pertenece.
     *
     * @param id     El ID de la ubicación a buscar.
     * @param apiKey La clave de API de la compañía asociada a la ubicación.
     * @return Un {@link Optional} que contendrá el objeto {@link Location} si se encuentra
     * una ubicación con el ID especificado y perteneciente a la compañía con la clave de API dada,
     * o un {@link Optional#empty()} si no se encuentra ninguna ubicación que cumpla ambos criterios.
     */
	Optional<Location> findByIdAndCompanyApiKey(Long id, String apiKey); 
	
	/**
     * Busca un objeto {@link Location} en la base de datos por su nombre y la clave de API de la compañía a la que pertenece.
     *
     * @param name   El nombre de la ubicación a buscar.
     * @param apiKey La clave de API de la compañía asociada a la ubicación.
     * @return Un {@link Optional} que contendrá el objeto {@link Location} si se encuentra
     * una ubicación con el nombre especificado y perteneciente a la compañía con la clave de API dada,
     * o un {@link Optional#empty()} si no se encuentra ninguna ubicación que cumpla ambos criterios.
     */
	Optional<Location> findByNameAndCompanyApiKey(String name, String apiKey);
	
	/**
     * Busca todas las ubicaciones {@link Location} en la base de datos asociadas a una compañía específica mediante su clave de API.
     *
     * @param apiKey La clave de API de la compañía para la cual se desean buscar las ubicaciones.
     * @return Una {@link List} de objetos {@link Location} que pertenecen a la compañía con
     * la clave de API especificada. Devuelve una lista vacía si no hay ubicaciones asociadas a esa clave de API.
     */
	List<Location> findByCompanyApiKey(String apiKey);
	
}
