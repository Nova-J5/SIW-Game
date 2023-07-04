package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Platform;

public interface PlatformRepository extends CrudRepository<Platform, Long>{

	public Platform findByName(String name);
	
	public List<Platform> findByYearOfRelease(Integer yearOfRelease);
	
	public List<Platform> findByDeveloper(Developer developer);
	
	public boolean existsByNameAndYearOfRelease(String name, Integer yearOfRelease);
	
}
