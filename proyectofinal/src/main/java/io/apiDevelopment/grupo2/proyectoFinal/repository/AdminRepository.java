package io.apiDevelopment.grupo2.proyectoFinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.apiDevelopment.grupo2.proyectoFinal.model.Admin;

/**
 * Interfaz del repositorio para las entidades {@link Admin}, que extiende {@link JpaRepository} de Spring Data JPA.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la tabla 'admin'.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	/**
     * Busca un objeto {@link Admin} en la base de datos por su nombre de usuario.
     *
     * @param username El nombre de usuario del administrador a buscar.
     * @return Un {@link Optional} que contendrá el objeto {@link Admin} si se encuentra
     * un administrador con el nombre de usuario especificado, o un {@link Optional#empty()}
     * si no se encuentra ningún administrador con ese nombre de usuario.
     */
	Optional<Admin> findByUsername(String username);
}
