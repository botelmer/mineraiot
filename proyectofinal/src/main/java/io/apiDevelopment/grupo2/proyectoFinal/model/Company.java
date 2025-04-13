package io.apiDevelopment.grupo2.proyectoFinal.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que define la entidad {@code Company} para la persistencia en la base de datos.
 * Representa una empresa dentro del sistema, incluyendo su nombre, clave de API y sus ubicaciones asociadas.
 */
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
	/**
     * Identificador único de la empresa.
     * Generado automáticamente por la base de datos.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Nombre de la empresa.
     */
	@Column(name = "company_name")
	private String name;
	
	/**
     * Clave de API única asociada a la empresa.
     * Utilizada para la identificación y autenticación de la empresa.
     */
	@Column(name = "company_api_key")
	private String apiKey;
	
	/**
     * Lista de ubicaciones asociadas a esta empresa.
     * Esta relación es de uno a muchos, donde una empresa puede tener múltiples ubicaciones.
     * Las ubicaciones se cargan de forma lazy (solo cuando se acceden) y todas las operaciones
     * en cascada (ej., eliminación de la empresa) se propagan a las ubicaciones.
     * El mapeo se realiza mediante el campo 'company' en la entidad {@link Location}.
     */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
	private List<Location> locations;
}
