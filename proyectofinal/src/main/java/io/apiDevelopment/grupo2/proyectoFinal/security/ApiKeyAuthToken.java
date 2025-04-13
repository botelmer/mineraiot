package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Clase personalizada que representa un token de autenticación basado en una API Key.
 * <p>
 * Esta clase extiende {@link AbstractAuthenticationToken} y se utiliza para almacenar
 * la API Key de un sensor o empresa que ha sido autenticado, junto con sus respectivas
 * autoridades, si las hay.
 * </p>
 * <p>
 * El token puede ser utilizado en el contexto de Spring Security para validar solicitudes
 * basadas en API Key y determinar si la petición es válida.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * ApiKeyAuthToken authToken = new ApiKeyAuthToken("apiKey-1234", AuthorityUtils.NO_AUTHORITIES);
 * }</pre>
 *
 * @see AbstractAuthenticationToken
 * @see org.springframework.security.core.GrantedAuthority
 */
public class ApiKeyAuthToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	private final String apiKey;


    /**
     * Crea un {@link ApiKeyAuthToken} no autenticado con la API Key proporcionada.
     *
     * @param apiKey la clave de la API utilizada para la autenticación.
     */
	public ApiKeyAuthToken(String apiKey) {
		super(null);
		this.apiKey = apiKey;
		super.setAuthenticated(false);
	}

    /**
     * Crea un {@link ApiKeyAuthToken} autenticado con la API Key y las autoridades proporcionadas.
     *
     * @param apiKey la clave de la API utilizada para la autenticación.
     * @param authorities las autoridades (roles o permisos) asociadas al token.
     */
	public ApiKeyAuthToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.apiKey = apiKey;
		super.setAuthenticated(true);
	}

    /**
     * Obtiene las credenciales del token. En este caso, las credenciales son nulas,
     * ya que solo se utiliza la API Key como principal.
     *
     * @return {@code null}, ya que la clave API no se considera como "credenciales".
     */
	@Override
	public Object getCredentials() {
		return null;
	}

    /**
     * Obtiene el principal del token, que en este caso es la API Key.
     *
     * @return la API Key asociada al token.
     */
	@Override
	public Object getPrincipal() {
		return this.apiKey;
	}

}
