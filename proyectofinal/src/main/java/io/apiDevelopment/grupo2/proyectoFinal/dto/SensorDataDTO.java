package io.apiDevelopment.grupo2.proyectoFinal.dto;

import java.time.LocalDateTime;
import java.util.Map;

import io.apiDevelopment.grupo2.proyectoFinal.model.SensorData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad {@link SensorData}.
 * Se utiliza para exponer información específica de los datos de sensores sin revelar
 * la estructura interna completa de la entidad.
 */
@Getter
@Setter
@NoArgsConstructor
public class SensorDataDTO {
	/**
     * Constructor que crea un {@code SensorDataDTO} a partir de una entidad {@link SensorData}.
     * Copia los campos relevantes de la entidad al DTO.
     *
     * @param sensorData La entidad {@code SensorData} de la cual se extraerán los datos.
     */
	public SensorDataDTO(SensorData sensorData) {
		this.datetime = sensorData.getDatetime();
		this.data = sensorData.getData();
		this.sensorName = sensorData.getSensor().getName();
	}
	
	/**
     * Fecha y hora en la que se registró el dato del sensor.
     */
	private LocalDateTime datetime;
	
	/**
     * Mapa que contiene los datos específicos del sensor (ej., temperatura, humedad, etc.).
     * Las claves y valores dentro de este mapa pueden variar según el tipo de sensor.
     */
	private Map<String, Object> data;
	
	/**
     * Nombre del sensor al que pertenecen estos datos.
     */
	private String sensorName;
}
