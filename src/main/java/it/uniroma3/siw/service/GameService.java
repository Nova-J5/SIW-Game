package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.GameRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional
	public Game saveGame(Game game) {
		return this.gameRepository.save(game);
	}
	
	@Transactional
    public Game getGameById(Long id) {
        Optional<Game> result = this.gameRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public List<Game> getGamesByTitle(String title) {
		return this.gameRepository.findByTitle(title);
	}
	
	@Transactional
    public List<Game> getGamesByYear(Integer year) {
		return this.gameRepository.findByYear(year);
    }
	
	@Transactional
    public List<Game> getGamesByDeveloper(Developer developer) {
		return this.gameRepository.findByDeveloper(developer);
    }

	@Transactional
    public List<Game> getAllGames() {
        List<Game> result = new ArrayList<>();
        Iterable<Game> iterable = this.gameRepository.findAll();
        for(Game game : iterable)
            result.add(game);
        return result;
    }
	
	@Transactional
	public void updateGame(Game updatedGame) {
		this.gameRepository.save(updatedGame);
	}
	
	@Transactional
	public boolean alreadyExists(Game game) {
		return this.gameRepository.existsByTitleAndYear(game.getTitle(), game.getYear());
	}

	@Transactional
	public void inizializeGame(@Valid Game game, Developer developer) {
		game.setDeveloper(developer);
	}

	@Transactional
	public void modifyGame(Game game, String title, Integer year, String description, Developer developer) {
		game.setTitle(title);
		game.setYear(year);
		game.setDescription(description);
		game.setDeveloper(developer);
	}
	
	@Transactional
	public void deleteGame(Long id) {
		
		Game game = this.getGameById(id);
		Developer developer = game.getDeveloper();
		if(developer!=null) {
			developer.getGamesProduced().remove(game);
			this.developerService.saveDeveloper(developer);
		}
		
		List<Genre> genres = game.getGenres();
		for(Genre genre : genres) {
			genre.getGames().remove(game);
			this.genreService.saveGenre(genre);
		}
		
		List<Platform> platforms = game.getPlatforms();
		for(Platform platform : platforms) {
			platform.getGames().remove(game);
			this.platformService.savePlatform(platform);
		}
		
		List<User> users = this.userService.getAllUsers();
		for(User user : users) {
			user.getCurrentlyPlaying().remove(game);
			user.getPlayed().remove(game);
			this.userService.saveUser(user);
		}
		
		this.gameRepository.deleteById(id);
	}
	
	@Transactional
	public List<Game> getAllGamesWithNoGenre(){
		return this.gameRepository.findGamesWithEmptyGenres();
	}
	
}
