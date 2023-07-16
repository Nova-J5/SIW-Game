package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;
import jakarta.validation.Valid;

public interface GameRepository extends CrudRepository<Game, Long> {
	
	public List<Game> findByTitle(String title);

	public List<Game> findByYear(Integer year);
	
	public List<Game> findByDeveloper(Developer developer);
			
    public boolean existsByTitleAndYear(String title, Integer year); 
    
    
}
