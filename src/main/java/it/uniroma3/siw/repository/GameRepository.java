package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Platform;

public interface GameRepository extends CrudRepository<Game, Long> {
	
	public List<Game> findByYear(Integer year);
	public List<Game> findByTitle(String title);
	
    public boolean existsByTitleAndYear(String title, Integer year); 
    
}
