package io.apiDevelopment.grupo2.proyectoFinal.model;

import java.util.List;


import io.apiDevelopment.grupo2.proyectoFinal.dto.LocationDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que define la entidad {@code Location} para la persistencia en la base de datos.
 * Representa una ubicación geográfica asociada a una {@link Company} y que puede contener múltiples {@link Sensor}es.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="location")
@NoArgsConstructor
public class Location {
	/**
     * Constructor que permite crear una instancia de {@code Location} a partir de un {@link LocationDTO}.
     *
     * @param locationDTO Objeto DTO que contiene los datos iniciales para la ubicación.
     */
	public Location (LocationDTO locationDTO) {
		this.name = locationDTO.getName();
		this.city = locationDTO.getCity();
		this.country = locationDTO.getCountry();
		this.meta = locationDTO.getMeta();
	}
	
	/**
     * Identificador único de la ubicación.
     * Generado automáticamente por la base de datos.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
     * Nombre de la ubicación.
     */
	@Column(name = "location_name")
	private String name;
	
	/**
     * País de la ubicación.
     */
	@Column(name = "location_country")
	private String country;
	
	/**
     * Ciudad de la ubicación.
     */
	@Column(name = "location_city")
	private String city;
	
	/**
     * Metadatos adicionales asociados a la ubicación.
     * Puede contener información adicional en formato JSON u otro.
     */
	@Column(name = "location_meta")
	private String meta;

	/**
     * Empresa a la que pertenece esta ubicación.
     * Esta relación es de muchos a uno, donde muchas ubicaciones pueden pertenecer a una misma empresa.
     * La columna de clave foránea en la tabla 'location' que referencia a la tabla 'company' es 'id_company'.
     */
	@ManyToOne
	@JoinColumn(name = "id_company")
	private Company company;
	
	/**
     * Lista de sensores asociados a esta ubicación.
     * Esta relación es de uno a muchos, donde una ubicación puede tener múltiples sensores.
     * Los sensores se cargan de forma lazy (solo cuando se acceden) y el mapeo se realiza
     * mediante el campo 'location' en la entidad {@link Sensor}.
     */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	private List<Sensor> sensors;
	
}
