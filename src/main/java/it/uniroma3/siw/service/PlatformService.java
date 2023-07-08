package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.PlatformRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PlatformService {
	
	@Autowired
	private PlatformRepository platformRepository;
	
	@Transactional
	public Platform savePlatform(Platform platform) {
		return this.platformRepository.save(platform);
	}
	
	@Transactional
    public Platform getPlatform(Long id) {
        Optional<Platform> result = this.platformRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public Platform getPlatformByName(String name) {
		return this.platformRepository.findByName(name);
	}
	
	@Transactional
    public List<Platform> getPlatformsByYearOfRelease(Integer yearOfRelease) {
		return this.platformRepository.findByYearOfRelease(yearOfRelease);
    }
	
	@Transactional
	public List<Platform> getPlatformsByDeveloper(Developer developer) {
		return this.platformRepository.findByDeveloper(developer);
	}

	@Transactional
    public List<Platform> getAllPlatforms() {
        List<Platform> result = new ArrayList<>();
        Iterable<Platform> iterable = this.platformRepository.findAll();
        for(Platform platform : iterable)
            result.add(platform);
        return result;
    }
	
	@Transactional
	public void updatePlatform(Platform updatedPlatform) {
		this.platformRepository.save(updatedPlatform);
	}

	@Transactional
	public void deletePlatform(Long id) {
		this.platformRepository.deleteById(id);		
	}
	
	@Transactional
	public boolean alreadyExists(Platform platform) {
		return this.platformRepository.existsByNameAndYearOfRelease(platform.getName(), platform.getYearOfRelease());
	}

	public List<Platform> getAllPlatformsNotInGame(@Valid Game game) {
		return this.platformRepository.findPlatformNotInGame(game);
	}

	public void inizializePlatform(Platform platform, Developer developer) {
		platform.setDeveloper(developer);
		developer.getPlatformsProduced().add(platform);
		
	}

}
