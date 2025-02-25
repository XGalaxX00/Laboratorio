package zegel.edu.pe.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "competiciones")
public class Competiciones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	
	@ManyToMany
    @JoinTable(name = "inscripciones",joinColumns = @JoinColumn(name = "competiciones_id"),inverseJoinColumns = @JoinColumn(name = "usuarios_id"))
    private List<Usuarios> usuarios = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="eventos_id", referencedColumnName = "id")
	private Eventos eventos;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sedes_id", referencedColumnName = "id")
	private Sedes sedes;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="categorias_id", referencedColumnName = "id")
	private Categorias categorias;
	
	@OneToMany(mappedBy = "competicion")
    private List<Turnos> turnos;

	public Competiciones() {

	}

	public Competiciones(Integer id, Eventos eventos, Sedes sedes, Categorias categorias,
			List<Usuarios> usuarios) {
		super();
		this.id = id;
		this.eventos = eventos;
		this.sedes = sedes;
		this.categorias = categorias;
		this.usuarios = usuarios;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Eventos getEventos() {
		return eventos;
	}



	public void setEventos(Eventos eventos) {
		this.eventos = eventos;
	}

	public Sedes getSedes() {
		return sedes;
	}



	public void setSedes(Sedes sedes) {
		this.sedes = sedes;
	}



	public Categorias getCategorias() {
		return categorias;
	}



	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Turnos> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turnos> turnos) {
		this.turnos = turnos;
	}
	
}
