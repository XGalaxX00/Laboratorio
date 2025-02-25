package zegel.edu.pe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import zegel.edu.pe.models.Clubes;

public interface IClubesDao extends JpaRepository<Clubes, Integer>{
	
	Clubes findBySolicitudesId(Integer id);

}
