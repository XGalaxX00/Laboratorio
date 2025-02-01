package zegel.edu.pe.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "clubes")
public class Clubes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String nombre;
	
	@OneToOne
    @JoinColumn(name = "encargado_id", referencedColumnName = "id")
    private Usuarios encargado;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitudes_id", referencedColumnName = "id")
    private Solicitudes solicitudes;

	public Clubes() {
	
	}

	public Clubes(Integer id, String nombre, Usuarios encargado, Solicitudes solicitudes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.encargado = encargado;
		this.solicitudes = solicitudes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuarios getEncargado() {
		return encargado;
	}

	public void setEncargado(Usuarios encargado) {
		this.encargado = encargado;
	}

	public Solicitudes getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Solicitudes solicitudes) {
		this.solicitudes = solicitudes;
	}

}
