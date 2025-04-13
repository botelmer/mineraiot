package io.apiDevelopment.grupo2.proyectoFinal.exception;

/**
 * Excepción personalizada que representa una condición de acceso no autorizado (HTTP 401).
 * <p>
 * Esta excepción se lanza cuando un usuario intenta acceder a un recurso protegido
 * sin tener credenciales válidas o no cumple con las políticas de autorización.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * if (!user.isAuthorized()) {
 *     throw new UnauthorizedException("No tienes permiso para acceder a este recurso.");
 * }
 * }</pre>
 *
 * @see RuntimeException
 * @see org.springframework.http.HttpStatus#UNAUTHORIZED
 */
public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

    /**
     * Crea una nueva instancia de {@code UnauthorizedException} con el mensaje especificado.
     *
     * @param msg el mensaje detallado que describe la causa de la excepción.
     */
	public UnauthorizedException(String msg) {
		super(msg);
	}
}
