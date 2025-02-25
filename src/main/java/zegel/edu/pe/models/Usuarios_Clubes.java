package zegel.edu.pe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "usuarios_clubes")
public class Usuarios_Clubes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="usuarios_id", referencedColumnName = "id")
	private Usuarios usuarios;
	
	@ManyToOne
	@JoinColumn(name="clubes_id", referencedColumnName = "id")
	private Clubes clubes;

	public Usuarios_Clubes() {
		super();
	}
	
	public Usuarios_Clubes(Integer id, Usuarios usuarios, Clubes clubes) {
		this.id = id;
		this.usuarios = usuarios;
		this.clubes = clubes;
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

	public Clubes getClubes() {
		return clubes;
	}

	public void setClubes(Clubes clubes) {
		this.clubes = clubes;
	}

}
