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
@Table (name = "turnos_semis")
public class Turnos_Semis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer ganador_1;
	private Integer ganador_2;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="competicion_id", referencedColumnName = "id")
	private Competiciones competiciones;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe1_id", referencedColumnName = "id")
	private Inscripciones compe1_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe2_id", referencedColumnName = "id")
	private Inscripciones compe2_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe3_id", referencedColumnName = "id")
	private Inscripciones compe3_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe4_id", referencedColumnName = "id")
	private Inscripciones compe4_id;

	public Turnos_Semis() {
		super();
	}

	public Turnos_Semis(Integer id, Integer ganador_1, Integer ganador_2, Competiciones competiciones,
			Inscripciones compe1_id, Inscripciones compe2_id, Inscripciones compe3_id, Inscripciones compe4_id) {
		super();
		this.id = id;
		this.ganador_1 = ganador_1;
		this.ganador_2 = ganador_2;
		this.competiciones = competiciones;
		this.compe1_id = compe1_id;
		this.compe2_id = compe2_id;
		this.compe3_id = compe3_id;
		this.compe4_id = compe4_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGanador_1() {
		return ganador_1;
	}

	public void setGanador_1(Integer ganador_1) {
		this.ganador_1 = ganador_1;
	}

	public Integer getGanador_2() {
		return ganador_2;
	}

	public void setGanador_2(Integer ganador_2) {
		this.ganador_2 = ganador_2;
	}

	public Competiciones getCompeticiones() {
		return competiciones;
	}

	public void setCompeticiones(Competiciones competiciones) {
		this.competiciones = competiciones;
	}

	public Inscripciones getCompe1_id() {
		return compe1_id;
	}

	public void setCompe1_id(Inscripciones compe1_id) {
		this.compe1_id = compe1_id;
	}

	public Inscripciones getCompe2_id() {
		return compe2_id;
	}

	public void setCompe2_id(Inscripciones compe2_id) {
		this.compe2_id = compe2_id;
	}

	public Inscripciones getCompe3_id() {
		return compe3_id;
	}

	public void setCompe3_id(Inscripciones compe3_id) {
		this.compe3_id = compe3_id;
	}

	public Inscripciones getCompe4_id() {
		return compe4_id;
	}

	public void setCompe4_id(Inscripciones compe4_id) {
		this.compe4_id = compe4_id;
	}
	
}
