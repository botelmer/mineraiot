package io.apiDevelopment.grupo2.proyectoFinal.dto;

import io.apiDevelopment.grupo2.proyectoFinal.model.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para la transferencia de información de la entidad {@code Location}.
 * <p>
 * Esta clase se utiliza para transportar datos de ubicaciones entre las diferentes capas de la aplicación.
 * Proporciona una representación simplificada de la entidad {@code Location}, facilitando la comunicación
 * entre la capa de presentación, la capa de servicio y potencialmente otras capas.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {
	
	/**
	 * Constructor que recibe un objeto {@link Location} y popula los campos del DTO.
	 * Incluye la obtención del nombre de la compañía asociada a la ubicación.
	 *
	 * @param location La entidad {@code Location} desde la cual se extraen los datos para el DTO.
	 */
	public LocationDTO(Location location) {
		this.name = location.getName();
		this.city = location.getCity();
		this.country = location.getCountry();
		this.meta = location.getMeta();
		this.companyName = location.getCompany().getName();
	}
	
	private String name;
	private String country;
	private String city;
	private String meta;
	private String companyName;
}
