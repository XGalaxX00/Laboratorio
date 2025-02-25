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
@Table (name = "solicitudes")
public class Solicitudes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String nombre_club;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="encargado_id", referencedColumnName = "id")
	private Usuarios encargado;
	
	private String motivo;
	
	private String imagen;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="estados_id", referencedColumnName = "id")
	private Estados estados;
	
	public Solicitudes() {

	}
	
	public Solicitudes(Integer id, String nombre_club, Usuarios encargado, String motivo, String imagen, Estados estados) {
		super();
		this.id = id;
		this.nombre_club = nombre_club;
		this.encargado = encargado;
		this.motivo = motivo;
		this.imagen = imagen;
		this.estados = estados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre_club() {
		return nombre_club;
	}

	public void setNombre_club(String nombre_club) {
		this.nombre_club = nombre_club;
	}

	public Usuarios getEncargado() {
		return encargado;
	}

	public void setEncargado(Usuarios encargado) {
		this.encargado = encargado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}
	
}
