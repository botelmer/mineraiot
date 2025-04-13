package io.apiDevelopment.grupo2.proyectoFinal.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorInputDTO;
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
 * Entidad JPA que representa los datos de un sensor en la base de datos.
 * Almacena la marca de tiempo de la lectura, los datos específicos del sensor en formato JSONB
 * y la relación con el sensor al que pertenecen estos datos.
 */
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensor_data")
public class SensorData {
	
	/**
     * Constructor que crea una instancia de {@code SensorData} a partir de un {@link SensorInputDTO}.
     * Convierte el timestamp Unix proporcionado en el DTO a un {@link LocalDateTime}
     * con la zona horaria de América/Santiago y copia los datos del sensor.
     *
     * @param sensorInputDTO El DTO que contiene la información para crear los datos del sensor.
     */
	public SensorData(SensorInputDTO sensorInputDTO) {
		this.datetime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sensorInputDTO.getDatetime()), ZoneId.of("America/Santiago"));
		this.data = sensorInputDTO.getData();
	}
	
	/**
     * Identificador único de este registro de datos del sensor.
     * Generado automáticamente por la base de datos.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Fecha y hora en la que se registró el dato del sensor.
     * Almacenado con la zona horaria de América/Santiago.
     */
	private LocalDateTime datetime;
	
	/**
     * Mapa que contiene los datos específicos del sensor (ej., temperatura, humedad, etc.).
     * Persistido en la base de datos como un tipo JSONB para flexibilidad en la estructura de los datos.
     * Utiliza {@link JdbcTypeCode} para especificar el tipo de columna JDBC como JSON.
     */
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private Map<String, Object> data;
	
	/**
     * Relación muchos-a-uno con la entidad {@link Sensor}.
     * Representa el sensor al que pertenecen estos datos.
     * La clave foránea 'id_sensor' en la tabla 'sensor_data' referencia al ID del sensor.
     */
	@ManyToOne
	@JoinColumn(name = "id_sensor")
	private Sensor sensor;
}
