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
@Table (name = "eventos")
public class Eventos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String nombre;
	private String fecha;
	private String hora;
	
	@ManyToOne()
	@JoinColumn(name="sedes_id")
	private Sedes sedes;
	
	@OneToOne()
	@JoinColumn(name="categorias_id", unique = true)
	private Categorias categorias;
	

	public Eventos() {

	}

	public Eventos(Integer id, String nombre, String fecha, String hora, Sedes sedes, Categorias categorias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.hora = hora;
		this.sedes = sedes;
		this.categorias = categorias;
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



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public String getHora() {
		return hora;
	}



	public void setHora(String hora) {
		this.hora = hora;
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
	
}
