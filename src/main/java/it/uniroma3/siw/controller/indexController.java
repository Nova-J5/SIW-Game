package it.uniroma3.siw.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.service.GenreService;
import it.uniroma3.siw.service.PlatformService;

@Controller
public class indexController {

	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private GenreService genreService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<Platform> allPlatforms = (List<Platform>) this.platformService.getAllPlatforms();
		Platform platformActive = allPlatforms.get(0);
		model.addAttribute("platformActive", platformActive);
		allPlatforms.remove(platformActive);
		model.addAttribute("platforms", allPlatforms);
		List<Genre> genres = this.genreService.getAllGenres();
		Collections.sort(genres, Comparator.comparing(Genre :: getName));

		model.addAttribute("genres", genres);

		return "index.html";
	}
}
