package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Platform;
import jakarta.validation.Valid;

public interface PlatformRepository extends CrudRepository<Platform, Long>{

	public Platform findByNameIgnoreCase(String name);
	
	public List<Platform> findByYearOfRelease(Integer yearOfRelease);
	
	public List<Platform> findByDeveloper(Developer developer);
	
	public boolean existsByNameAndYearOfRelease(String name, Integer yearOfRelease);

	@Query("SELECT a FROM Platform a WHERE NOT EXISTS " +
	           "(SELECT f FROM Game f JOIN f.platforms fa WHERE fa = a AND f = :game)")
	public List<Platform> findPlatformNotInGame(@Valid Game game);
	
}
