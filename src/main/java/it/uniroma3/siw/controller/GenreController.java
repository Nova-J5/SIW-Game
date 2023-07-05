package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.repository.GenreRepository;

@Controller
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;
	
	
	@GetMapping("/genre/{id}")
	public String getGenre(@PathVariable("id") Long id, Model model) {
		model.addAttribute("genre", this.genreRepository.findById(id).get());
		return "genre.html";
	}
	
}
