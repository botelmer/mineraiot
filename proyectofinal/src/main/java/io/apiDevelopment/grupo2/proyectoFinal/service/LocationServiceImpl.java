package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.apiDevelopment.grupo2.proyectoFinal.dto.LocationDTO;
import io.apiDevelopment.grupo2.proyectoFinal.exception.BadRequestException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.NotFoundException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Company;
import io.apiDevelopment.grupo2.proyectoFinal.model.Location;
import io.apiDevelopment.grupo2.proyectoFinal.repository.CompanyRepository;
import io.apiDevelopment.grupo2.proyectoFinal.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementación de la interfaz {@link LocationService} que gestiona la lógica de negocio para las ubicaciones.
 * <p>
 * Esta clase proporciona la implementación concreta de los servicios definidos en {@code LocationService},
 * incluyendo la obtención, creación, actualización y eliminación de ubicaciones, asegurando la asociación
 * con la compañía autenticada a través del contexto de seguridad.
 * Utiliza los repositorios {@link LocationRepository} y {@link CompanyRepository} para interactuar con la base de datos.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
	private final LocationRepository locationRepository;
	private final CompanyRepository companyRepository;
	
	/**
	 * Obtiene todas las ubicaciones asociadas a la compañía autenticada.
	 * <p>
	 * Este método recupera la clave de API de la compañía autenticada desde el {@link SecurityContextHolder}
	 * y utiliza esta clave para buscar todas las entidades {@link Location} asociadas a dicha compañía
	 * en la base de datos. Los resultados se transforman en una lista de {@link LocationDTO}.
	 * </p>
	 *
	 * @return Una lista de {@link LocationDTO} que representan todas las ubicaciones de la compañía autenticada.
	 * Si la compañía no tiene ubicaciones asociadas, la lista estará vacía.
	 */
	@Override
	public List<LocationDTO> getAllLocation() {
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		List<Location> locations = locationRepository.findByCompanyApiKey(apiKey); 

		return locations.stream().map((location) -> new LocationDTO(location)).toList();
	}

	/**
	 * Obtiene una ubicación específica por su ID, verificando que pertenezca a la compañía autenticada.
	 * <p>
	 * Este método primero valida y recupera la entidad {@link Location} utilizando el ID proporcionado
	 * y la clave de API de la compañía autenticada. Si la ubicación existe y está asociada a la compañía,
	 * se transforma en un {@link LocationDTO} y se retorna.
	 * </p>
	 *
	 * @param id El identificador único de la ubicación que se desea obtener.
	 * @return Un objeto {@link LocationDTO} que representa la ubicación encontrada.
	 * @throws NotFoundException Si no existe una ubicación con el ID proporcionado o si no está asociada a la compañía autenticada.
	 */
	@Override
	public LocationDTO getLocationById(Long id) {
		Location location = getValidatedLocation(id);
		return new LocationDTO(location);
	}

	/**
	 * Crea una nueva ubicación asociada a la compañía autenticada.
	 * <p>
	 * Este método recibe un {@link LocationDTO} con los datos de la nueva ubicación. Realiza las siguientes acciones:
	 * 1. Recupera la clave de API de la compañía autenticada.
	 * 2. Busca la entidad {@link Company} correspondiente a la clave de API.
	 * 3. Valida que la compañía exista.
	 * 4. Valida que los campos obligatorios del {@code LocationDTO} no estén vacíos.
	 * 5. Crea una nueva entidad {@link Location} a partir del DTO y la asocia con la compañía encontrada.
	 * 6. Persiste la nueva ubicación en la base de datos.
	 * 7. Actualiza el {@code companyName} en el {@code LocationDTO} para la respuesta.
	 * </p>
	 *
	 * @param locationDTO El objeto {@link LocationDTO} que contiene los datos de la nueva ubicación a crear.
	 * No debe ser nulo y debe contener información válida.
	 * @return El objeto {@link LocationDTO} que representa la ubicación recién creada, incluyendo el nombre de la compañía asociada.
	 * @throws NotFoundException   Si no se encuentra la compañía asociada a la clave de API del contexto de seguridad.
	 * @throws BadRequestException Si alguno de los campos obligatorios en {@code locationDTO} está vacío.
	 */
	@Override
	public LocationDTO createLocation(LocationDTO locationDTO) {
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<Company> company = companyRepository.findByApiKey(apiKey);
		
		if(company.isEmpty()) {
			throw new NotFoundException("Error compañia no encontrada.");
		}

		validateLocationDTO(locationDTO);
			
		Location location = new Location(locationDTO);
		
		location.setCompany(company.get());
		locationRepository.save(location);
		
		locationDTO.setCompanyName(company.get().getName());
		
		return locationDTO;
	}
	
	/**
	 * Actualiza la información de una ubicación existente, verificando que pertenezca a la compañía autenticada.
	 * <p>
	 * Este método recibe el ID de la ubicación a actualizar y un {@link LocationDTO} con los nuevos datos.
	 * Realiza las siguientes acciones:
	 * 1. Valida que los campos obligatorios del {@code LocationDTO} no estén vacíos.
	 * 2. Valida y recupera la entidad {@link Location} utilizando el ID proporcionado y la clave de API de la compañía autenticada.
	 * 3. Actualiza los campos de la entidad {@code Location} con los datos del DTO.
	 * 4. Persiste los cambios en la base de datos.
	 * </p>
	 *
	 * @param id          El identificador único de la ubicación que se desea actualizar.
	 * @param locationDTO El objeto {@link LocationDTO} que contiene los nuevos datos para la ubicación.
	 * No debe ser nulo y debe contener información válida.
	 * @return El objeto {@link LocationDTO} que representa la ubicación actualizada.
	 * @throws BadRequestException Si alguno de los campos obligatorios en {@code locationDTO} está vacío.
	 * @throws NotFoundException   Si no existe una ubicación con el ID proporcionado o si no está asociada a la compañía autenticada.
	 */
	@Override
	public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
		
		validateLocationDTO(locationDTO);
		
		Location location = getValidatedLocation(id);
		
		location.setName(locationDTO.getName());
		location.setCity(locationDTO.getCity());
		location.setCountry(locationDTO.getCountry());
		location.setMeta(locationDTO.getMeta());

		locationRepository.save(location);
		
		return new LocationDTO(location);
	}

	/**
	 * Elimina una ubicación por su ID, verificando que pertenezca a la compañía autenticada.
	 * <p>
	 * Este método primero valida y recupera la entidad {@link Location} utilizando el ID proporcionado
	 * y la clave de API de la compañía autenticada. Si la ubicación existe y está asociada a la compañía,
	 * se elimina de la base de datos.
	 * </p>
	 *
	 * @param id El identificador único de la ubicación que se desea eliminar.
	 * @return Un mensaje {@code String} indicando que la ubicación ha sido eliminada correctamente.
	 * @throws NotFoundException Si no existe una ubicación con el ID proporcionado o si no está asociada a la compañía autenticada.
	 */
	@Override
	public String deleteLocation(Long id) {
		getValidatedLocation(id);
		
		locationRepository.deleteById(id);			

		return "Location con id: " + id + " fue eliminado correctamente.";
	}
	
	/**
	 * Valida y recupera una entidad {@link Location} por su ID, asegurando que esté asociada a la compañía autenticada.
	 *
	 * @param id El identificador único de la ubicación a recuperar.
	 * @return La entidad {@link Location} encontrada y validada.
	 * @throws NotFoundException Si no existe una ubicación con el ID proporcionado o si no está asociada a la compañía autenticada.
	 */
	private Location getValidatedLocation(Long id) {
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		Optional<Location> locationOpt = locationRepository.findByIdAndCompanyApiKey(id, apiKey);
		
		if(locationOpt.isEmpty()) {
			throw new NotFoundException("No existe la localización con ese id o no esta asociado a la compañia.");
		}
		
		return locationOpt.get();
	}
	
	/**
	 * Valida que los campos obligatorios del objeto {@link LocationDTO} no estén nulos ni vacíos.
	 *
	 * @param locationDTO El objeto {@link LocationDTO} a validar.
	 * @throws BadRequestException Si alguno de los atributos 'name', 'city', 'country' o 'meta' en {@code locationDTO} es nulo o está en blanco.
	 */
	private void validateLocationDTO(LocationDTO locationDTO) {
		
		if(locationDTO.getName() == null || locationDTO.getName().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo name en el body.");
		}
		
		if(locationDTO.getCity() == null || locationDTO.getCity().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo city en el body.");
		}
		
		if(locationDTO.getCountry() == null || locationDTO.getCountry().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo country en el body.");
		}
		
		if(locationDTO.getMeta() == null || locationDTO.getMeta().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo meta en el body.");
		}

	}
}
