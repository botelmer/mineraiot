package io.apiDevelopment.grupo2.proyectoFinal.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de Transferencia de Datos (DTO) para la entrada de datos de sensores.
 * Permite recibir datos dinámicos de sensores sin necesidad de definir campos específicos
 * para cada posible métrica.
 */
@NoArgsConstructor
public class SensorInputDTO {
	/**
     * Marca de tiempo (timestamp) en segundos desde la época (Unix epoch)
     * en la que se registró el dato del sensor.
     */
	@Getter
	@Setter
	private Long datetime;
	
	/**
     * Mapa que contiene los datos específicos del sensor. Las claves de este mapa
     * representan los nombres de las métricas (ej., "temperatura", "humedad", "presion")
     * y los valores son los valores correspondientes.
     * <p>
     * Este campo utiliza las anotaciones {@link JsonAnyGetter} y {@link JsonAnySetter}
     * de Jackson para permitir la deserialización flexible de propiedades JSON adicionales
     * que no coinciden con campos explícitos en esta clase.
     */
	private Map<String, Object> data = new HashMap<>();
	
	/**
     * Getter para el mapa de datos del sensor.
     * Utilizado por Jackson para serializar las entradas de este mapa como propiedades
     * JSON individuales.
     *
     * @return El mapa de datos del sensor.
     */
	@JsonAnyGetter
    public Map<String, Object> getData() {
        return data;
    }

	/**
     * Setter para las propiedades dinámicas del sensor.
     * Utilizado por Jackson para deserializar propiedades JSON que no coinciden con
     * el campo 'datetime'. Estas propiedades se almacenan en el mapa 'data'.
     *
     * @param name  El nombre de la propiedad JSON.
     * @param value El valor de la propiedad JSON.
     */
    @JsonAnySetter
    public void setData(String name, Object value) {
        if (!name.equals("datetime")) {
        	data.put(name, value);
        }
    }
}
