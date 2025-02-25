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
@Table (name = "resultados")
public class Resultados {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private Integer orden_merito;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuarios_id", referencedColumnName = "id")
	private Usuarios usuarios;
	
	public Resultados() {
	
	}
	
	public Resultados(Integer orden_merito, Usuarios usuarios) {
		this.orden_merito = orden_merito;
		this.usuarios = usuarios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrden_merito() {
		return orden_merito;
	}

	public void setOrden_merito(Integer orden_merito) {
		this.orden_merito = orden_merito;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}	

}
