package io.apiDevelopment.grupo2.proyectoFinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Clase que define la entidad {@code Admin} para la persistencia en la base de datos.
 * Representa a un administrador dentro del sistema.
 */
@Getter
@Entity
@Table(name = "admin")
public class Admin{
	/**
     * Identificador único del administrador.
     * Generado automáticamente por la base de datos.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  id;

	/**
     * Nombre de usuario del administrador.
     * Utilizado para la autenticación.
     */
	private String username;
	
	/**
     * Contraseña del administrador.
     * Debe ser almacenada de forma segura (ej., utilizando hashing).
     */
	private String password;

}
