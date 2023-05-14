package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {
	
	public List<Game> findByYear(Integer year);

    public boolean existsByTitleAndYear(String title, Integer year); 
}
