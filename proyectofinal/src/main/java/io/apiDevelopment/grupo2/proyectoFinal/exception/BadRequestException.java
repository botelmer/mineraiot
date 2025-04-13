package io.apiDevelopment.grupo2.proyectoFinal.exception;

/**
 * Excepción personalizada que representa un error de solicitud incorrecta (Bad Request).
 * <p>
 * Esta excepción se lanza cuando una solicitud del cliente no cumple con los requisitos
 * esperados por el servidor, como parámetros inválidos o mal formateados.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * if (input == null) {
 *     throw new BadRequestException("El parámetro 'input' no puede ser nulo.");
 * }
 * }</pre>
 *
 */
public class BadRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
    /**
     * Crea una nueva instancia de {@code BadRequestException} con el mensaje especificado.
     *
     * @param msg el mensaje detallado de la excepción.
     */
	public BadRequestException(String msg) {
		super(msg);
	}
}
