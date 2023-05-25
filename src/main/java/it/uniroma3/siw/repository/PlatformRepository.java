package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Platform;

public interface PlatformRepository extends CrudRepository<Platform, Long>{

	public Platform findByName(String name);
}
