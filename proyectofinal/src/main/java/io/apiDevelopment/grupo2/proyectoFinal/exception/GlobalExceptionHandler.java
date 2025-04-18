package io.apiDevelopment.grupo2.proyectoFinal.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para el controlador de la aplicación.
 * <p>
 * Esta clase utiliza la anotación {@link ControllerAdvice} para capturar
 * y manejar excepciones personalizadas lanzadas por los controladores
 * de forma centralizada, devolviendo respuestas HTTP adecuadas según el tipo
 * de excepción.
 * </p>
 *
 * <p>Las excepciones que maneja incluyen:</p>
 * <ul>
 *   <li>{@link BadRequestException}: representa errores de solicitud inválida (HTTP 400).</li>
 *   <li>{@link NotFoundException}: representa recursos no encontrados (HTTP 404).</li>
 *   <li>{@link UnauthorizedException}: representa accesos no autorizados (HTTP 401).</li>
 * </ul>
 *
 * <p>Ejemplo de comportamiento:</p>
 * Si un controlador lanza una {@code NotFoundException}, esta clase interceptará la excepción
 * y devolverá una respuesta con el código de estado HTTP 404 y el mensaje correspondiente.
 * 
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see org.springframework.http.ResponseEntity
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link BadRequestException}.
     *
     * @param ex la excepción lanzada.
     * @return una respuesta con el mensaje de error y el estado HTTP 400 (Bad Request).
     */
	@ExceptionHandler(value = { BadRequestException.class })
	public ResponseEntity<String> handleBadRequest(BadRequestException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
    /**
     * Maneja las excepciones de tipo {@link NotFoundException}.
     *
     * @param ex la excepción lanzada.
     * @return una respuesta con el mensaje de error y el estado HTTP 404 (Not Found).
     */
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<String> handleNotFound(NotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
    /**
     * Maneja las excepciones de tipo {@link UnauthorizedException}.
     *
     * @param ex la excepción lanzada.
     * @return una respuesta con el mensaje de error y el estado HTTP 401 (Unauthorized).
     */
	@ExceptionHandler(value = { UnauthorizedException.class })
	public ResponseEntity<String> handleUnauthorized(UnauthorizedException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Maneja las excepciones de tipo {@link DataIntegrityViolationException}.
	 * Esta excepción se lanza cuando se intenta realizar una operación que viola
	 * una restricción de integridad de la base de datos, como una violación
	 * de clave foránea o una restricción de unicidad.
	 *
	 * <p>Este método examina la causa subyacente de la excepción para determinar
	 * el tipo específico de violación y devuelve una respuesta HTTP apropiada.
	 *
	 * @param ex La excepción {@code DataIntegrityViolationException} capturada.
	 * @return Una respuesta {@code ResponseEntity} que indica el resultado del
	 * manejo de la excepción. Puede ser un {@link HttpStatus#CONFLICT} (409)
	 * si se detecta una violación de clave foránea o de unicidad, o
	 * {@link HttpStatus#INTERNAL_SERVER_ERROR} (500) para otros errores
	 * de integridad no manejados específicamente.
	 */
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex){
		ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
		
		if(constraintViolationException.getSQLState().equals("23503")) {
			return new ResponseEntity<String>("No se puede eliminar el registro porque está siendo utilizado.", HttpStatus.CONFLICT);
		}
		
		if(constraintViolationException.getSQLState().equals("23505")) {
			return new ResponseEntity<String>("Conflicto: valor duplicado.", HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<String>("Ha ocurido un error interno en el servidor, su solicitud no ha podido ser procesada.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
