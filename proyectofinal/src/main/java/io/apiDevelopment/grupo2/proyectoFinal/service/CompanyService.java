package io.apiDevelopment.grupo2.proyectoFinal.service;

import io.apiDevelopment.grupo2.proyectoFinal.dto.CompanyRequest;

/**
 * Interfaz que define los servicios para la gestión de compañías.
 * <p>
 * Esta interfaz declara los métodos que la capa de servicio de compañías debe implementar.
 * Define las operaciones disponibles para la manipulación de la información de las compañías,
 * abstrayendo la implementación concreta de la lógica de negocio.
 * </p>
 */
public interface CompanyService {
	
	/**
	 * Crea una nueva compañía en el sistema.
	 * <p>
	 * Este método define la operación para la creación de una nueva compañía utilizando la información
	 * proporcionada en el objeto {@link CompanyRequest}.
	 * </p>
	 *
	 * @param companyDTO El objeto {@link CompanyRequest} que contiene los datos necesarios para la creación de la compañía.
	 * No debe ser nulo.
	 * @return Un mensaje {@code String} indicando el resultado de la operación de creación.
	 * Este mensaje podría incluir información sobre el éxito o el fallo de la creación.
	 */
	String createCompany(CompanyRequest companyDTO);
}
