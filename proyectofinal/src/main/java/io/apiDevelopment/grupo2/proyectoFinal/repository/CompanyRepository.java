package io.apiDevelopment.grupo2.proyectoFinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.apiDevelopment.grupo2.proyectoFinal.model.Company;

/**
 * Interfaz del repositorio para las entidades {@link Company}, que extiende {@link JpaRepository} de Spring Data JPA.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la tabla 'company'.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
	/**
     * Busca un objeto {@link Company} en la base de datos por su clave de API única.
     *
     * @param apiKey La clave de API de la compañía a buscar.
     * @return Un {@link Optional} que contendrá el objeto {@link Company} si se encuentra
     * una compañía con la clave de API especificada, o un {@link Optional#empty()}
     * si no se encuentra ninguna compañía con esa clave de API.
     */
	Optional<Company> findByApiKey(String apiKey); 
}
