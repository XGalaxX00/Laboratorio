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
@Table(name = "incripciones")
public class Inscripciones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuarios_id", referencedColumnName = "id")
	private Usuarios usuarios_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="competiciones_id", referencedColumnName = "id")
	private Competiciones competiciones_id;

	public Inscripciones() {
		super();
	}

	public Inscripciones(Integer id, Usuarios usuarios_id, Competiciones competiciones_id) {
		super();
		this.id = id;
		this.usuarios_id = usuarios_id;
		this.competiciones_id = competiciones_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuarios getUsuarios_id() {
		return usuarios_id;
	}

	public void setUsuarios_id(Usuarios usuarios_id) {
		this.usuarios_id = usuarios_id;
	}

	public Competiciones getCompeticiones_id() {
		return competiciones_id;
	}

	public void setCompeticiones_id(Competiciones competiciones_id) {
		this.competiciones_id = competiciones_id;
	}

}
