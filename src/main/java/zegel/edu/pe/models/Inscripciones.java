		package zegel.edu.pe.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inscripciones")
public class Inscripciones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuarios_id")
	private Usuarios usuarios;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="competiciones_id")
	private Competiciones competiciones;

	public Inscripciones() {
		super();
	}

	public Inscripciones(Integer id, Usuarios usuarios_id, Competiciones competiciones_id) {
		super();
		this.id = id;
		this.usuarios = usuarios_id;
		this.competiciones = competiciones_id;
	}
	
	public Inscripciones(Usuarios usuarios_id, Competiciones competiciones_id) {
		super();
		this.usuarios = usuarios_id;
		this.competiciones = competiciones_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Competiciones getCompeticion() {
		return competiciones;
	}

	public void setCompeticion(Competiciones competicion) {
		this.competiciones = competicion;
	}

}
