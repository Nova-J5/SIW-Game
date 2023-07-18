package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;

import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.service.GenreService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.PlatformService;
import it.uniroma3.siw.validator.GameValidator;
import jakarta.validation.Valid;


@Controller
public class GameController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private DeveloperService developerService;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private GameService gameService;

	@Autowired
	private GameValidator gameValidator;

	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private GenreService genreService;
	
	
	private void inzializeAddPlatformsAndGenres(Model model, Game game) {
		model.addAttribute("game", game);
		model.addAttribute("platformInGame", game.getPlatforms());
		model.addAttribute("genresInGame", game.getGenres());
		model.addAttribute("platformNotInGame", this.platformService.getAllPlatformsNotInGame(game));
		model.addAttribute("genresNotInGame", this.genreService.getAllGenresNotInGame(game));
	}
	
	// ********************************************** //
	// CONTROLLER PER RICHIESTE DI UN UTENTE GENERICO
	//********************************************** //
	
	@GetMapping("/game/{id}")
	public String getGame(@PathVariable("id") Long id, Model model) {
		Game game = this.gameService.getGameById(id);
		model.addAttribute("game", game );
		User user = this.globalController.getCurrentUser();
		if(user != null) {
			model.addAttribute("giocato", user.getPlayed().contains(game));
			model.addAttribute("giocando", user.getCurrentlyPlaying().contains(game));
		}
		return "game.html";
	}

	@GetMapping("/games")
	public String showGames(Model model) {
		List<Genre> genres = this.genreService.getAllGenres();
		Collections.sort(genres, Comparator.comparing(Genre :: getName));
		model.addAttribute("genres", genres);
		model.addAttribute("games", this.gameService.getAllGamesWithNoGenre());
		return "games.html";
	}

	@PostMapping("/searchGames")
	public String searchGamesByYear(Model model, @RequestParam String str) {
		
		Genre genre = genreService.getGenreByName(str);
		
		Platform platform = this.platformService.getPlatformByName(str);
		
		List<Game> titleGames = this.gameService.getGamesByTitle(str);
		
		model.addAttribute("ricerca", str);
		
		try {
			Integer year = Integer.parseInt(str);
			model.addAttribute("games", this.gameService.getGamesByYear(year));
			
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
	
	
	//************************************* //
	// CONTROLLER PER RICHIESTE DI UN ADMIN
	//************************************* //
	
	
	@GetMapping("/admin/formNewGame")
	public String formNewGame(Model model) {
		model.addAttribute("game", new Game());
		model.addAttribute("developers", this.developerService.getAllDevelopers());
		
		return "admin/formNewGame.html";
	}
	
	@PostMapping("/admin/newGame")
	public String newGame(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, Model model,
			@RequestParam Long developerId, @RequestParam("file") MultipartFile imageForGame ,
			@RequestParam("file2") MultipartFile imageForGames) throws IOException {
		
		this.gameValidator.validate(game, bindingResult);
		
		if (!bindingResult.hasErrors()) {
				
			//se volete inserire un immagine in un form aggiungete questo if nel controller e il file come argomento
			//in html va messo nella riga di input type="file" name="file" e nella riga del form enctype="multipart/form-data"
			if (!imageForGame.isEmpty()) {
				Image img = new Image(imageForGame.getBytes());
				this.imageService.save(img);
				game.setImage(img);
			}	
			
			if (!imageForGames.isEmpty()) {
				Image img = new Image(imageForGames.getBytes());
				this.imageService.save(img);
				game.setImageForGames(img);
			}	
			
			Developer developer = this.developerService.getDeveloperById(developerId);
			this.gameService.inizializeGame(game,developer);
			this.developerService.saveDeveloper(developer);
			this.gameService.saveGame(game);
			
			
			this.inzializeAddPlatformsAndGenres(model,game);
			return "admin/addPlatformsAndGenres.html";
		} else {
			return "admin/formNewGame.html"; 
		}
	}
		
	@GetMapping("/admin/removePlatform/{gameId}/{platformId}")
	public String removePlatform(Model model, @PathVariable("gameId") Long gameId,
			@PathVariable("platformId") Long platformId) {
		
		Game game = this.gameService.getGameById(gameId);
		Platform platform = this.platformService.getPlatformById(platformId);
		game.getPlatforms().remove(platform);
		platform.getGames().remove(game);
		
		this.gameService.saveGame(game);
		this.platformService.savePlatform(platform);
		
		this.inzializeAddPlatformsAndGenres(model,game);
		return "admin/addPlatformsAndGenres.html";
	}
	
	@GetMapping("/admin/addPlatform/{gameId}/{platformId}")
	public String addPlatform(Model model, @PathVariable("gameId") Long gameId,
			@PathVariable("platformId") Long platformId) {
		
		Game game = this.gameService.getGameById(gameId);
		Platform platform = this.platformService.getPlatformById(platformId);
		game.getPlatforms().add(platform);
		platform.getGames().add(game);
		
		this.gameService.saveGame(game);
		this.platformService.savePlatform(platform);
		
		this.inzializeAddPlatformsAndGenres(model,game);
		return "admin/addPlatformsAndGenres.html";
	}
	
	@GetMapping("/admin/removeGenre/{gameId}/{genreId}")
	public String removeGenre(Model model, @PathVariable("gameId") Long gameId,
			@PathVariable("genreId") Long genreId) {
		
		Game game = this.gameService.getGameById(gameId);
		Genre genre = this.genreService.getGenreById(genreId);
		game.getGenres().remove(genre);
		genre.getGames().remove(game);
		
		this.gameService.saveGame(game);
		this.genreService.saveGenre(genre);
		
		this.inzializeAddPlatformsAndGenres(model,game);
		return "admin/addPlatformsAndGenres.html";
	}
	
	@GetMapping("/admin/addGenre/{gameId}/{genreId}")
	public String addGenre(Model model, @PathVariable("gameId") Long gameId,
			@PathVariable("genreId") Long genreId) {
		
		Game game = this.gameService.getGameById(gameId);
		Genre genre = this.genreService.getGenreById(genreId);
		game.getGenres().add(genre);
		genre.getGames().add(game);
		
		this.gameService.saveGame(game);
		this.genreService.saveGenre(genre);
		
		this.inzializeAddPlatformsAndGenres(model,game);
		return "admin/addPlatformsAndGenres.html";
	}
	
	@GetMapping("/admin/updatePlatAndGen/{id}")
	public String editGame(Model model, @PathVariable("id") Long id) {
		Game game = this.gameService.getGameById(id);
		this.inzializeAddPlatformsAndGenres(model,game);
		return "admin/addPlatformsAndGenres.html";
	}
	
	@GetMapping("/admin/updateTitleAndDesc/{gameId}")
	public String formUpdateTitleAndDesc(Model model, @PathVariable("gameId") Long gameId) {
		model.addAttribute("game", this.gameService.getGameById(gameId));
		model.addAttribute("developers", this.developerService.getAllDevelopers());
		
		return "admin/updateTitleAndDesc.html";
	}
	
	@PostMapping("/admin/saveTitleAndDesc/{gameId}")
	public String updateTitleAndDesc(Model model, @PathVariable("gameId") Long gameId, @RequestParam("title") String title,
			@RequestParam("year") Integer year, @RequestParam("developerId") Long developerId,
			@RequestParam("description") String description){
		Game game = this.gameService.getGameById(gameId);
		this.gameService.modifyGame(game, title, year, description, this.developerService.getDeveloperById(developerId));
		this.gameService.saveGame(game);
		return "redirect:/game/" + game.getId();
	}
	
	@GetMapping("/admin/deleteGame/{id}")
	public String deleteGame(@PathVariable("id") Long id, Model model) {
		this.gameService.deleteGame(id);
		return "redirect:/games";
	}
}
	
