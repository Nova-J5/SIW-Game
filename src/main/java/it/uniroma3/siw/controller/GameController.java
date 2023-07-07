package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.repository.DeveloperRepository;
import it.uniroma3.siw.repository.GameRepository;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.GenreRepository;
import it.uniroma3.siw.repository.PlatformRepository;
import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.validator.GameValidator;
import jakarta.validation.Valid;


@Controller
public class GameController {
	
	@Autowired 
	GameRepository gameRepository;
	
	@Autowired
	DeveloperRepository developerRepository;
	
	@Autowired
	GenreRepository genreRepository;
	
	@Autowired
	PlatformRepository platformRepository;
	
	@Autowired
	DeveloperService developerService;
	
	@Autowired
	GameService gameService;

	@Autowired
	private GameValidator gameValidator;
	
	@GetMapping("/game/{id}")
	public String getGame(@PathVariable("id") Long id, Model model) {
		model.addAttribute("game", this.gameRepository.findById(id).get());
		return "game.html";
	}

	@GetMapping("/games")
	public String showGames(Model model) {
		model.addAttribute("genres", this.genreRepository.findAll());
		return "games.html";
	}

	@PostMapping("/searchGames")
	public String searchGamesByYear(Model model, @RequestParam String str) {
		
		Genre genre = genreRepository.findByName(str);
		
		Platform platform = platformRepository.findByName(str);
		
		List<Game> titleGames = this.gameRepository.findByTitle(str);
		
		
		try {
			Integer year = Integer.parseInt(str);
			model.addAttribute("games", this.gameRepository.findByYear(year));
			
		} catch(Exception e) {
			if (genre != null) 
				model.addAttribute("games", genre.getGames());
	
			else if (platform != null)
			model.addAttribute("games", platform.getGames());
			
			else
				model.addAttribute("games", titleGames);
	
		}
		return "foundGames.html";
	}
	
	@GetMapping("/admin/formNewGame")
	public String formNewGame(Model model) {
		model.addAttribute("game", new Game());
		model.addAttribute("developers", this.developerRepository.findAll());
		//model.addAttribute("genres", this.genreRepository.findAll());
		//model.addAttribute("platforms", this.platformRepository.findAll());
		
		return "admin/formNewGame.html";
	}
	
	@PostMapping("/admin/newGame")
	public String newGame(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, Model model,
			@RequestParam Long developerId) {
		this.gameValidator.validate(game, bindingResult);
		if (!bindingResult.hasErrors()) {
			game.setDeveloper(this.developerService.getDeveloperById(developerId));
			this.gameService.saveGame(game);
			model.addAttribute("game", game);
			return "game.html";
		} else {
			return "admin/formNewGame.html"; 
		}
	}
	
}