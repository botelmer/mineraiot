package io.apiDevelopment.grupo2.proyectoFinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.apiDevelopment.grupo2.proyectoFinal.dto.CompanyRequest;
import io.apiDevelopment.grupo2.proyectoFinal.service.CompanyService;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para manejar las operaciones relacionadas con las compañías.
 * Expone endpoints bajo la ruta base "/api/v1/company".
 */
@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;
	
    /**
     * Crea una nueva compañía en el sistema.
     *
     * Este endpoint maneja solicitudes POST en la ruta "/api/v1/company/".
     *
     * @param companyDTO Objeto que contiene los datos necesarios para crear la compañía.
     * @return Una respuesta HTTP con un mensaje indicando el resultado de la operación
     *         y el código de estado {@link HttpStatus#CREATED} si se crea exitosamente.
     */
	@PostMapping("/")
	public ResponseEntity<String> createCompany(@RequestBody CompanyRequest companyDTO) {
		return new ResponseEntity<String>(companyService.createCompany(companyDTO), HttpStatus.CREATED);
	}
	
}
