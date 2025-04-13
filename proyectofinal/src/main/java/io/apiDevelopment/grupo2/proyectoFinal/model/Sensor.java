package io.apiDevelopment.grupo2.proyectoFinal.model;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que define la entidad {@code Sensor} para la persistencia en la base de datos.
 * Representa un sensor dentro de una {@link Location}, con atributos como nombre, categoría, metadatos y clave de API.
 */
@Getter
@Setter
@Entity
@Table(name = "sensor")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
	/**
     * Constructor que permite crear una instancia de {@code Sensor} a partir de un {@link SensorDTO}.
     *
     * @param sensorDTO Objeto DTO que contiene los datos iniciales para el sensor.
     */
	public Sensor(SensorDTO sensorDTO) {
		this.name = sensorDTO.getName();
		this.category = sensorDTO.getCategory();
		this.meta = sensorDTO.getMeta();
	}
	
	/**
     * Identificador único del sensor.
     * Generado automáticamente por la base de datos.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Nombre del sensor.
     */
	@Column(name = "sensor_name")
	private String name;
	
	/**
     * Categoría o tipo del sensor (ej., temperatura, humedad, etc.).
     */
	@Column(name = "sensor_category")
	private String category;
	
	/**
     * Metadatos adicionales asociados al sensor.
     * Puede contener información específica en formato JSON u otro.
     */
	@Column(name = "sensor_meta")
	private String meta;
	
	/**
     * Clave de API única asociada al sensor.
     * Utilizada para la identificación y autenticación del sensor.
     */
	@Column(name = "sensor_api_key")
	private String apiKey;
	
	/**
     * Ubicación a la que pertenece este sensor.
     * Esta relación es de muchos a uno, donde muchos sensores pueden pertenecer a una misma ubicación.
     * La columna de clave foránea en la tabla 'sensor' que referencia a la tabla 'location' es 'id_location'.
     */
	@ManyToOne
	@JoinColumn(name = "id_location")
	private Location location;
}
