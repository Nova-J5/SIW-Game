package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
		model.addAttribute("genres", this.genreService.getAllGenres());
		return "index.html";
	}
}
