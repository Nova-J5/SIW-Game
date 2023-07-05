package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.repository.GameRepository;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.GenreRepository;
import it.uniroma3.siw.repository.PlatformRepository;


@Controller
public class GameController {
	
	@Autowired 
	GameRepository gameRepository;
	
	@Autowired
	GenreRepository genreRepository;
	
	@Autowired
	PlatformRepository platformRepository;
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/formNewGame")
	public String formNewGame(Model model) {
		model.addAttribute("game", new Game());
		return "formNewGame.html";
	}

	@GetMapping("/game/{id}")
	public String getGame(@PathVariable("id") Long id, Model model) {
		model.addAttribute("game", this.gameRepository.findById(id).get());
		return "game.html";
	}

	@GetMapping("/games")
	public String showGames(Model model) {
		model.addAttribute("games", this.gameRepository.findAll());
		return "games.html";
	}
	
	@GetMapping("/formSearchGamesByYear")
	public String formSearchGamesByYear() {
		return "formSearchGamesByYear.html";
	}
	
	@PostMapping("/games")
	public String newGame(@ModelAttribute("game") Game game, Model model) {
		if (!gameRepository.existsByTitleAndYear(game.getTitle(), game.getYear())) {
			this.gameRepository.save(game); 
			model.addAttribute("game", game);
			return "game.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo gioco esiste già");
			return "formNewGame.html"; 
		}
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
}