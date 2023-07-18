package it.uniroma3.siw.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;

import it.uniroma3.siw.repository.GenreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GameService gameService;
	
	@Transactional
	public Genre saveGenre(Genre genre) {
		return this.genreRepository.save(genre);
	}
	
	@Transactional
    public Genre getGenreById(Long id) {
        Optional<Genre> result = this.genreRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public Genre getGenreByName(String name) {
		return this.genreRepository.findByNameIgnoreCase(name);
    }


	@Transactional
    public List<Genre> getAllGenres() {
        List<Genre> result = new ArrayList<>();
        Iterable<Genre> iterable = this.genreRepository.findAll();
        for(Genre genre : iterable)
            result.add(genre);
        return result;
    }
	
	@Transactional
	public void updateGenre(Genre updatedGenre) {
		this.genreRepository.save(updatedGenre);
	}
	
	@Transactional
	public boolean alreadyExists(Genre genre) {
		return this.genreRepository.existsByName(genre.getName());
	}

	@Transactional
	public List<Genre> getAllGenresNotInGame(@Valid Game game) {
		return this.genreRepository.findGenreNotInGame(game);
	}

	public void modifyGenre(Genre genre, String name, String description) {

		genre.setName(name);
		genre.setDescription(description);
		
	}
	
	@Transactional
	public void deleteGenre(Long id) {
		Genre genre = this.getGenreById(id);
		List<Game> games = genre.getGames();
		for(Game game : games) {
			game.getGenres().remove(genre);
			this.gameService.saveGame(game);
		}
		this.genreRepository.deleteById(id);
	}

}
