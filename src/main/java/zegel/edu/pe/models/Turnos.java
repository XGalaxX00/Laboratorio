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
@Table (name = "turnos")
public class Turnos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="competicion_id", referencedColumnName = "id")
	private Competiciones competicion;
	
	@ManyToOne
    @JoinColumn(name = "fase_id", nullable = false)
    private Fase fase;
	
	@ManyToOne
	@JoinColumn(name="compe1", referencedColumnName = "id")
	private Usuarios competidor1;
	
	@ManyToOne
	@JoinColumn(name="compe2", referencedColumnName = "id")
	private Usuarios competidor2;
	
	@ManyToOne
	@JoinColumn(name="ganador_com", referencedColumnName = "id")
	private Usuarios ganador;

	public Turnos() {
		super();
	}

	public Turnos(Integer id, Competiciones competicion, Fase fase, Usuarios competidor1, Usuarios competidor2,
			Usuarios ganador) {
		super();
		this.id = id;
		this.competicion = competicion;
		this.fase = fase;
		this.competidor1 = competidor1;
		this.competidor2 = competidor2;
		this.ganador = ganador;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Competiciones getCompeticion() {
		return competicion;
	}

	public void setCompeticion(Competiciones competicion) {
		this.competicion = competicion;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public Usuarios getCompetidor1() {
		return competidor1;
	}

	public void setCompetidor1(Usuarios competidor1) {
		this.competidor1 = competidor1;
	}

	public Usuarios getCompetidor2() {
		return competidor2;
	}

	public void setCompetidor2(Usuarios competidor2) {
		this.competidor2 = competidor2;
	}

	public Usuarios getGanador() {
		return ganador;
	}

	public void setGanador(Usuarios ganador) {
		this.ganador = ganador;
	}
	
}
