package io.apiDevelopment.grupo2.proyectoFinal.dto;

import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad {@link Sensor}.
 * Se utiliza para exponer información específica de los sensores sin revelar
 * la estructura interna completa de la entidad.
 */
@Getter
@Setter
@NoArgsConstructor
public class SensorDTO {
	
	/**
     * Constructor que crea un {@code SensorDTO} a partir de una entidad {@link Sensor}.
     * Copia los campos relevantes de la entidad al DTO.
     *
     * @param sensor La entidad {@code Sensor} de la cual se extraerán los datos.
     */
	public SensorDTO (Sensor sensor) {
		this.name = sensor.getName();
		this.category = sensor.getCategory();
		this.meta = sensor.getMeta();
		this.locationId = sensor.getLocation().getId();
	}
	
	/**
	 * Nombre del sensor
	 */
	private String name;
	
	/**
     * Categoría o tipo del sensor (ej., temperatura, humedad, etc.).
     */
	private String category;
	
	/**
     * Metadatos adicionales asociados al sensor.
     * Puede contener información específica en formato JSON u otro.
     */
	private String meta;
	
	/**
     * Id de la ubicación a la que pertenece este sensor.
     */
	private Long locationId;
}
