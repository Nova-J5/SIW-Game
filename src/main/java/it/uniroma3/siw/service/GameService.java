package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.repository.GameRepository;
import jakarta.transaction.Transactional;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Transactional
	public Game saveGame(Game game) {
		return this.gameRepository.save(game);
	}
	
	@Transactional
    public Game getGame(Long id) {
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
	public void deleteGame(Long id) {
		this.gameRepository.deleteById(id);		
	}
	
	@Transactional
	public boolean alreadyExists(Game game) {
		return this.gameRepository.existsByTitleAndYear(game.getTitle(), game.getYear());
	}

}
