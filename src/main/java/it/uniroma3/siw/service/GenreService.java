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
	
	@Transactional
	public Genre saveGenre(Genre genre) {
		return this.genreRepository.save(genre);
	}
	
	@Transactional
    public Genre getGenre(Long id) {
        Optional<Genre> result = this.genreRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public Genre getGenreByName(String name) {
		return this.genreRepository.findByName(name);
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
	public void deleteGenre(Long id) {
		this.genreRepository.deleteById(id);		
	}
	
	@Transactional
	public boolean alreadyExists(Genre genre) {
		return this.genreRepository.existsByName(genre.getName());
	}

	public List<Genre> getAllGenresNotInGame(@Valid Game game) {
		return this.genreRepository.findGenreNotInGame(game);
	}

	public Genre getGenreById(Long genreId) {
		return this.genreRepository.findById(genreId).get();
	}

}
