package io.apiDevelopment.grupo2.proyectoFinal.exception;

/**
 * Excepción personalizada que representa una condición de recurso no encontrado (HTTP 404).
 * <p>
 * Esta excepción se lanza cuando el sistema no puede localizar un recurso solicitado,
 * como una entidad con un ID específico que no existe en la base de datos.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * if (location == null) {
 *     throw new NotFoundException("Ubicación no encontrada con el ID proporcionado.");
 * }
 * }</pre>
 *
 * @see RuntimeException
 * @see org.springframework.http.HttpStatus#NOT_FOUND
 */
public class NotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
    /**
     * Crea una nueva instancia de {@code NotFoundException} con el mensaje especificado.
     *
     * @param msg el mensaje detallado que describe la causa de la excepción.
     */
	public NotFoundException(String msg) {
		super(msg);
	}
}
