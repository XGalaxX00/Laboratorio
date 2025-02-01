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
@Table (name = "turnos_final")
public class Turnos_Final {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer ganador_final;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="competicion_id", referencedColumnName = "id")
	private Turnos_Semis competicion_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe1_id", referencedColumnName = "id")
	private Turnos_Semis compe1_id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compe2_id", referencedColumnName = "id")
	private Turnos_Semis compe2_id;

	public Turnos_Final() {
		super();
	}

	public Turnos_Final(Integer id, Integer ganador_final, Turnos_Semis competicion_id, Turnos_Semis compe1_id,
			Turnos_Semis compe2_id) {
		super();
		this.id = id;
		this.ganador_final = ganador_final;
		this.competicion_id = competicion_id;
		this.compe1_id = compe1_id;
		this.compe2_id = compe2_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGanador_final() {
		return ganador_final;
	}

	public void setGanador_final(Integer ganador_final) {
		this.ganador_final = ganador_final;
	}

	public Turnos_Semis getCompeticion_id() {
		return competicion_id;
	}

	public void setCompeticion_id(Turnos_Semis competicion_id) {
		this.competicion_id = competicion_id;
	}

	public Turnos_Semis getCompe1_id() {
		return compe1_id;
	}

	public void setCompe1_id(Turnos_Semis compe1_id) {
		this.compe1_id = compe1_id;
	}

	public Turnos_Semis getCompe2_id() {
		return compe2_id;
	}

	public void setCompe2_id(Turnos_Semis compe2_id) {
		this.compe2_id = compe2_id;
	}
}
