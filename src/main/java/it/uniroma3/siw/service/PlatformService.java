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
	
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private GameService gameService;

	
	@Transactional
	public Platform savePlatform(Platform platform) {
		return this.platformRepository.save(platform);
	}
	
	@Transactional
    public Platform getPlatformById(Long id) {
        Optional<Platform> result = this.platformRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public Platform getPlatformByName(String name) {
		return this.platformRepository.findByNameIgnoreCase(name);
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
	public boolean alreadyExists(Platform platform) {
		return this.platformRepository.existsByNameAndYearOfRelease(platform.getName(), platform.getYearOfRelease());
	}

	@Transactional
	public List<Platform> getAllPlatformsNotInGame(@Valid Game game) {
		return this.platformRepository.findPlatformNotInGame(game);
	}

	@Transactional
	public void inizializePlatform(Platform platform, Developer developer) {
		platform.setDeveloper(developer);
		developer.getPlatformsProduced().add(platform);
		
	}
	@Transactional
	public void modifyPlatform(Platform platform, String name, Integer year, String description,
			String carouselDescription, Developer developer) {

		platform.setName(name);
		platform.setYearOfRelease(year);
		platform.setDescription(description);
		platform.setCarouselDescription(carouselDescription);
		platform.setDeveloper(developer);
		
	}
	
	
	@Transactional
	public void deletePlatform(Long id) {
		Platform platform = this.getPlatformById(id);
		Developer developer = platform.getDeveloper();
		if(developer!=null) {
			developer.getPlatformsProduced().remove(platform);
			this.developerService.saveDeveloper(developer);
		}
		List<Game> games = platform.getGames();
		for(Game game : games) {
			game.getPlatforms().remove(platform);
			this.gameService.saveGame(game);
		}
		this.platformRepository.deleteById(id);
	}

}
