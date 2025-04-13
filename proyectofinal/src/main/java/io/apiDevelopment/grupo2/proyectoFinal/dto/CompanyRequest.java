package io.apiDevelopment.grupo2.proyectoFinal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para la recepción de datos de creación o actualización de una compañía.
 * <p>
 * Esta clase se utiliza para transferir la información necesaria para crear o modificar una entidad
 * {@code Company} entre las diferentes capas de la aplicación, principalmente desde la capa de presentación
 * (controlador) hacia la capa de servicio.
 * </p>
 * <p>
 * Contiene los campos necesarios para la manipulación de la información básica de una compañía:
 * su nombre, nombre de usuario y contraseña.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class CompanyRequest {
	private String name;
	private String username;
	private String password;
}
