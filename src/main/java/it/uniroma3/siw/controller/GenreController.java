package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.repository.GenreRepository;
import it.uniroma3.siw.service.GenreService;

@Controller
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreService genreService;
	
	
	@GetMapping("/genre/{id}")
	public String getGenre(@PathVariable("id") Long id, Model model) {
		model.addAttribute("genre", this.genreRepository.findById(id).get());
		return "genre.html";
	}
	
	@GetMapping("/genres")
	public String showGames(Model model) {
		model.addAttribute("genres", this.genreRepository.findAll());
		return "genres.html";
	}
	
	@GetMapping("/admin/formNewGenre")
	public String formNewGenre(Model model) {
		model.addAttribute("genre", new Genre());
		return "admin/formNewGenre.html";
	}
	
	@PostMapping("/admin/newGenre")
	public String newGenre(@ModelAttribute("genre") Genre genre, Model model) {
		if (!genreRepository.existsByName(genre.getName())) {
			this.genreService.saveGenre(genre);
			model.addAttribute("genre", genre);
			return "genre.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo genere esiste già");
			return "admin/formNewGenre.html"; 
		}
	}
	
}
