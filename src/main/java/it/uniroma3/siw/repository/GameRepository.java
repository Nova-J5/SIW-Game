package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {
	
	public List<Game> findByTitle(String title);

	public List<Game> findByYear(Integer year);
	
	public List<Game> findByDeveloper(Developer developer);
			
    public boolean existsByTitleAndYear(String title, Integer year); 
    
}
