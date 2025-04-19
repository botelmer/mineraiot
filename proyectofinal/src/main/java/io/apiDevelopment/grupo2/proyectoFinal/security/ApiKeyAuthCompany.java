package io.apiDevelopment.grupo2.proyectoFinal.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.apiDevelopment.grupo2.proyectoFinal.exception.UnauthorizedException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Company;
import io.apiDevelopment.grupo2.proyectoFinal.repository.CompanyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Componente encargado de manejar la autenticación basada en API Key para entidades {@link Company}.
 * <p>
 * Este componente extrae la cabecera <code>API-KEY</code> de una petición HTTP entrante
 * y valida su existencia en el repositorio de compañías. Si la clave es válida, se genera
 * un {@link Authentication} sin autoridades.
 * </p>
 *
 * <p>Este componente es utilizado principalmente en filtros personalizados de seguridad
 * para validar que la compañía que realiza la petición tiene una clave de acceso válida.</p>
 *
 * <p>Ejemplo de cabecera esperada:</p>
 * <pre>
 *     GET /api/v1/location/
 *     Headers:
 *     API-KEY: abc123xyz
 * </pre>
 *
 * @see Company
 * @see ApiKeyAuthToken
 * @see CompanyRepository
 * @see org.springframework.security.core.Authentication
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthCompany {
	private final CompanyRepository companyRepository;
	

    /**
     * Extrae la cabecera <code>API-KEY</code> de la solicitud HTTP y valida si corresponde
     * a una compañía registrada.
     *
     * @param request la solicitud HTTP entrante.
     * @return un {@link Optional} conteniendo el objeto {@link Authentication} generado si la clave es válida;
     *         o vacío si no se encuentra una compañía con esa API Key.
     */
	public Optional<Authentication> getApiKey(HttpServletRequest request){
		String providedKey = request.getHeader("API-KEY");
		
		Optional<Company> apiKey = companyRepository.findByApiKey(providedKey);
		
		if(apiKey.isEmpty()) {
			throw new UnauthorizedException("Api key no autorizada.");
		}
		
		return Optional.of(new ApiKeyAuthToken(apiKey.get().getApiKey(), AuthorityUtils.NO_AUTHORITIES));
	}
}
