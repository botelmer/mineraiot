package io.apiDevelopment.grupo2.proyectoFinal.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de Transferencia de Datos (DTO) para la recepción de peticiones de datos de sensores.
 * Contiene la clave de API para la autenticación y la lista de datos de sensores a procesar.
 */
@Getter
@Setter
@NoArgsConstructor
public class SensorDataRequest {
	/**
     * Clave de API utilizada para autenticar la solicitud.
     * Esta clave identifica a la compañía o entidad que envía los datos.
     */
	@JsonProperty("api_key")
	private String apiKey;
	
	/**
     * Lista de objetos {@link SensorInputDTO}, cada uno conteniendo los datos de un sensor individual.
     * Estos datos incluyen información como la marca de tiempo y los valores del sensor.
     */
	@JsonProperty("json_data")
	private List<SensorInputDTO> jsonData;
}
