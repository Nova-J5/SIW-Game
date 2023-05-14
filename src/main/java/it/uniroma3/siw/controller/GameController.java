package it.uniroma3.siw.controller;

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
import it.uniroma3.siw.validator.GameValidator;

@Controller
@Validated
public class GameController {
	
	@Autowired 
	GameRepository gameRepository;
	
	 //@Autowired
	 //private GameValidator gameValidator;

	
	@GetMapping("/index.html")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/formNewGame")
	public String formNewGame(Model model) {
		model.addAttribute("game", new Game());
		return "formNewGame.html";
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

	@GetMapping("/games/{id}")
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

	@PostMapping("/searchGamesByYear")
	public String searchGamesByYear(Model model, @RequestParam Integer year) {
		model.addAttribute("games", this.gameRepository.findByYear(year));
		return "foundGames.html";
	}
}
