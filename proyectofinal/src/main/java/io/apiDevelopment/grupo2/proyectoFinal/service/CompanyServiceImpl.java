package io.apiDevelopment.grupo2.proyectoFinal.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import io.apiDevelopment.grupo2.proyectoFinal.dto.CompanyRequest;
import io.apiDevelopment.grupo2.proyectoFinal.exception.BadRequestException;
import io.apiDevelopment.grupo2.proyectoFinal.exception.UnauthorizedException;
import io.apiDevelopment.grupo2.proyectoFinal.model.Admin;
import io.apiDevelopment.grupo2.proyectoFinal.model.Company;
import io.apiDevelopment.grupo2.proyectoFinal.repository.AdminRepository;
import io.apiDevelopment.grupo2.proyectoFinal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementación de la interfaz {@link CompanyService} que gestiona la lógica de negocio para las compañías.
 * <p>
 * Esta clase proporciona la implementación concreta de los servicios definidos en {@code CompanyService},
 * incluyendo la creación de nuevas compañías y la gestión de su información.
 * Utiliza los repositorios {@link CompanyRepository} y {@link AdminRepository} para interactuar con la base de datos.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{
	private final CompanyRepository companyRepository;
	private final AdminRepository adminRepository;
	
	/**
	 * Crea una nueva compañía en el sistema, validando la solicitud y autenticando al administrador.
	 * <p>
	 * Este método recibe un objeto {@link CompanyRequest} con los datos de la nueva compañía.
	 * Realiza las siguientes acciones:
	 * 1. Valida que los campos obligatorios en la solicitud no estén vacíos.
	 * 2. Busca un administrador por el nombre de usuario proporcionado.
	 * 3. Verifica que la contraseña proporcionada coincida con la contraseña encriptada del administrador.
	 * 4. Si la autenticación es exitosa, crea una nueva entidad {@link Company}, le asigna un nombre y genera una clave de API única.
	 * 5. Persiste la nueva compañía en la base de datos utilizando el {@link CompanyRepository}.
	 * </p>
	 *
	 * @param companyRequest El objeto {@link CompanyRequest} que contiene el nombre de la nueva compañía,
	 * el nombre de usuario del administrador y la contraseña del administrador.
	 * No debe ser nulo y debe contener información válida.
	 * @return Un mensaje {@code String} indicando que la compañía ha sido creada y proporcionando su clave de API generada.
	 * @throws BadRequestException    Si alguno de los campos obligatorios en {@code companyRequest} está vacío.
	 * @throws UnauthorizedException Si no se encuentra un administrador con el nombre de usuario proporcionado
	 * o si la contraseña proporcionada no coincide con la contraseña del administrador.
	 */
	@Override
	public String createCompany(CompanyRequest companyRequest) {
		
		validateRequest(companyRequest);
		
		Optional<Admin> adminOpt = adminRepository.findByUsername(companyRequest.getUsername());
		
		
		if(adminOpt.isEmpty() || !isPasswordValid(companyRequest.getPassword(), adminOpt.get().getPassword())) {
			throw new UnauthorizedException("Acceso denegado.");
		}
		
		Company company = new Company();
		company.setName(companyRequest.getName());
		company.setApiKey(UUID.randomUUID().toString());
		companyRepository.save(company);
		
		return "Compañia creada, su api key es: " + company.getApiKey();
	}
	
	/**
	 * Verifica si una contraseña plana coincide con una contraseña encriptada utilizando BCrypt.
	 *
	 * @param password         La contraseña en texto plano a verificar.
	 * @param passwordEncrypted La contraseña encriptada con la que se comparará la contraseña plana.
	 * @return {@code true} si la contraseña plana coincide con la contraseña encriptada; {@code false} en caso contrario.
	 */
	private boolean isPasswordValid(String password, String passwordEncrypted) {
		return BCrypt.checkpw(password, passwordEncrypted);
	}
	
	/**
	 * Valida que los campos obligatorios del objeto {@link CompanyRequest} no estén nulos ni vacíos.
	 *
	 * @param companyRequest El objeto {@link CompanyRequest} a validar.
	 * @return {@code true} si todos los campos obligatorios son válidos; de lo contrario, lanza una {@link BadRequestException}.
	 * @throws BadRequestException Si alguno de los atributos 'name', 'username' o 'password' en {@code companyRequest} es nulo o está en blanco.
	 */
	private boolean validateRequest(CompanyRequest companyRequest) {
		if(companyRequest.getName() == null || companyRequest.getName().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo name en el body.");
		}
		
		if(companyRequest.getUsername() == null || companyRequest.getUsername().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo username en el body.");
		}
		
		if(companyRequest.getPassword() == null || companyRequest.getPassword().isBlank()) {
			throw new BadRequestException("Error: Falta el atributo password en el body.");
		}
		
		return true;
	}
}
