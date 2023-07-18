package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;
import jakarta.validation.Valid;

public interface GenreRepository extends CrudRepository<Genre, Long>{
	
	public Genre findByNameIgnoreCase(String name);
		
	public boolean existsByName(String name);

	@Query("SELECT a FROM Genre a WHERE NOT EXISTS " +
	           "(SELECT f FROM Game f JOIN f.genres fa WHERE fa = a AND f = :game)")
	public List<Genre> findGenreNotInGame(@Valid Game game);
	
}
