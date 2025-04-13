package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.apiDevelopment.grupo2.proyectoFinal.dto.SensorDTO;
import io.apiDevelopment.grupo2.proyectoFinal.exception.BadRequestException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.NotFoundException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Location;
import io.apiDevelopment.grupo2.proyectoFinal.model.Sensor;
import io.apiDevelopment.grupo2.proyectoFinal.repository.LocationRepository;
import io.apiDevelopment.grupo2.proyectoFinal.repository.SensorRepository;
import lombok.RequiredArgsConstructor;


/**
 * Implementación del servicio para gestionar las operaciones relacionadas con sensores.
 * <p>
 * Esta clase maneja las operaciones CRUD de sensores (crear, obtener, actualizar, eliminar) a través de un servicio.
 * Se asegura de que las operaciones estén asociadas a una compañía, utilizando el API Key de la autenticación
 * para verificar la relación con las ubicaciones y sensores.
 * </p>
 * <p>
 * Utiliza el contexto de seguridad de Spring para obtener el API Key del usuario autenticado y realizar las búsquedas
 * dentro de los repositorios correspondientes.
 * </p>
 *
 * @see SensorDTO
 * @see SensorRepository
 * @see LocationRepository
 * @see BadRequestException
 * @see NotFoundException
 */
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService{
	private final SensorRepository sensorRepository;
	private final LocationRepository locationRepository;
	
    /**
     * Crea un nuevo sensor asociado a una ubicación específica.
     * <p>
     * El sensor se crea utilizando los datos proporcionados en el objeto {@link SensorDTO}. Además, se valida que la ubicación
     * proporcionada esté asociada a la compañía del usuario autenticado. El API Key del sensor es generado de manera aleatoria.
     * </p>
     *
     * @param sensorDTO objeto que contiene los datos del sensor a crear.
     * @return un mensaje de éxito que incluye el API Key del sensor creado.
     * @throws NotFoundException si la ubicación no se encuentra o no está asociada a la compañía del usuario autenticado.
     * @throws BadRequestException si alguno de los atributos requeridos en el {@link SensorDTO} está ausente o es inválido.
     */
	@Override
	public String createSensor(SensorDTO sensorDTO) {
		
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

		Optional<Location> location = locationRepository.findByNameAndCompanyApiKey(sensorDTO.getLocationName(), apiKey);
		
		if(location.isEmpty()) {
			throw new NotFoundException("Localización no encontrada.");
		}
		
		validateSensorDTO(sensorDTO);
		
		Sensor sensor = new Sensor(sensorDTO);
		sensor.setApiKey(UUID.randomUUID().toString());
		sensor.setLocation(location.get());
		
		sensorRepository.save(sensor);
		
		return "Sensor creado correctamente, la api key es: " + sensor.getApiKey();
	}


    /**
     * Obtiene todos los sensores asociados a la compañía del usuario autenticado.
     * <p>
     * Retorna una lista de {@link SensorDTO} que representan los sensores registrados bajo la compañía identificada por el API Key.
     * </p>
     *
     * @return una lista de objetos {@link SensorDTO} representando los sensores asociados.
     */
	@Override
	public List<SensorDTO> getAllSensors() {
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		List<Sensor> sensors = sensorRepository.findByLocationCompanyApiKey(apiKey);
	
		return sensors.stream().map((sensor) -> new SensorDTO(sensor)).toList();
	}

    /**
     * Obtiene un sensor por su ID.
     * <p>
     * Si el sensor no se encuentra o no está asociado a la compañía del usuario autenticado, se lanza una excepción {@link NotFoundException}.
     * </p>
     *
     * @param id el ID del sensor a obtener.
     * @return un objeto {@link SensorDTO} que representa el sensor con el ID especificado.
     * @throws NotFoundException si el sensor no se encuentra o no está asociado a la compañía.
     */
	@Override
	public SensorDTO getSensorById(Long id) {
		Sensor sensor = getValidatedSensor(id);
		return new SensorDTO(sensor);
	}

    /**
     * Actualiza los datos de un sensor específico.
     * <p>
     * El sensor es actualizado con los valores proporcionados en el {@link SensorDTO}. Si el sensor no se encuentra o no está asociado
     * a la compañía del usuario autenticado, se lanza una excepción {@link NotFoundException}.
     * </p>
     *
     * @param id el ID del sensor a actualizar.
     * @param sensorDTO el objeto {@link SensorDTO} con los nuevos valores para el sensor.
     * @return un objeto {@link SensorDTO} con los valores actualizados.
     * @throws NotFoundException si el sensor no se encuentra o no está asociado a la compañía.
     * @throws BadRequestException si los datos del sensor son inválidos.
     */
	@Override
	public SensorDTO updateSensor(Long id, SensorDTO sensorDTO) {
		validateSensorDTO(sensorDTO);
		
		Sensor sensor = getValidatedSensor(id);
		
		sensor.setCategory(sensorDTO.getCategory());
		sensor.setName(sensorDTO.getName());
		sensor.setMeta(sensorDTO.getMeta());
		
		sensorRepository.save(sensor);
		
		return new SensorDTO(sensor);
	}

    /**
     * Elimina un sensor especificado por su ID.
     * <p>
     * Si el sensor no se encuentra o no está asociado a la compañía del usuario autenticado, se lanza una excepción {@link NotFoundException}.
     * </p>
     *
     * @param id el ID del sensor a eliminar.
     * @return un mensaje indicando que el sensor fue eliminado exitosamente.
     * @throws NotFoundException si el sensor no se encuentra o no está asociado a la compañía.
     */
	@Override
	public String deleteSensor(Long id) {
		getValidatedSensor(id);
		
		sensorRepository.deleteById(id);
		
		return "Sensor "+ id + " eliminado.";
	}
	
    /**
     * Valida que un sensor exista y esté asociado a la compañía del usuario autenticado.
     * <p>
     * Si el sensor no existe o no está asociado correctamente, se lanza una excepción {@link NotFoundException}.
     * </p>
     *
     * @param id el ID del sensor a validar.
     * @return el sensor validado.
     * @throws NotFoundException si el sensor no existe o no está asociado a la compañía.
     */
	private Sensor getValidatedSensor(Long id) {
		String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		Optional<Sensor> sensorOpt = sensorRepository.findByIdAndLocationCompanyApiKey(id, apiKey);
		
		if(sensorOpt.isEmpty()) {
			throw new NotFoundException("No existe la localización con ese id o no esta asociado a la compañia.");
		}
		
		return sensorOpt.get();
	}

    /**
     * Valida los atributos requeridos de un {@link SensorDTO}.
     * <p>
     * Si algún atributo necesario está ausente o es inválido, se lanza una excepción {@link BadRequestException}.
     * </p>
     *
     * @param sensorDTO el objeto {@link SensorDTO} a validar.
     * @throws BadRequestException si faltan atributos requeridos en el {@link SensorDTO}.
     */
	private void validateSensorDTO(SensorDTO sensorDTO) {
		if(sensorDTO.getName() == null || sensorDTO.getName().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo name en el body.");
		}
		
		if(sensorDTO.getCategory() == null || sensorDTO.getCategory().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo category en el body.");
		}
		
		if(sensorDTO.getMeta() == null || sensorDTO.getMeta().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo meta en el body.");
		}

	}
}
