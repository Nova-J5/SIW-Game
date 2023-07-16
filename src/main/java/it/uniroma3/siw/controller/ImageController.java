package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.service.GenreService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.PlatformService;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Platform;

@Controller
public class ImageController {
	@Autowired
    private ImageService imageService;
	@Autowired
	private GameService gameService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private PlatformService platformService;
	@Autowired
	private DeveloperService developerService;

    @GetMapping("/display/image/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        Image img = this.imageService.findById(id);
        byte[] image = img.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } 
    
  //************************************* //
  // CONTROLLER PER IMMAGINI DEL GIOCO
  //************************************* //
    
    @PostMapping("/admin/addImageToGame/{gameId}")
	public String addImageForGameToGame(Model model, @PathVariable Long gameId, @RequestParam("file") MultipartFile file) throws IOException {
		Game game = gameService.getGameById(gameId);
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			game.setImage(img);
			this.gameService.saveGame(game);
		}
		return "redirect:/game/" + game.getId();
	}
	
	@GetMapping("/admin/removeImageFromGame/{gameId}")
	public String removeImageForGameFromGame(@PathVariable Long gameId, Model model) {
		Game game = this.gameService.getGameById(gameId);
		game.setImage(null);
		this.gameService.saveGame(game);
		return "redirect:/game/" + game.getId();
	
	}
	
	 @PostMapping("/admin/addImageForGamesToGame/{gameId}")
		public String addImageForGamesToGame(Model model, @PathVariable Long gameId, @RequestParam("file") MultipartFile file) throws IOException {
			Game game = gameService.getGameById(gameId);
			if (!file.isEmpty()) {
				Image img = new Image(file.getBytes());
				this.imageService.save(img);
				game.setImageForGames(img);
				this.gameService.saveGame(game);
			}
			return "redirect:/game/" + game.getId();
		}
	 
	 @GetMapping("/admin/removeImageForGamesFromGame/{gameId}")
		public String removeImageForGamesFromGame(@PathVariable Long gameId, Model model) {
			Game game = this.gameService.getGameById(gameId);
			game.setImageForGames(null);
			this.gameService.saveGame(game);
			return "redirect:/game/" + game.getId();
		
		}

	//************************************* //
	// CONTROLLER PER IMMAGINI DEL GENERE
	//************************************* //

    @PostMapping("/admin/addBackgroundImageToGenre/{genreId}")
	public String addBackgroundImageToGenre(Model model, @PathVariable Long genreId, @RequestParam("file") MultipartFile file) throws IOException {
		Genre genre = genreService.getGenreById(genreId);
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			genre.setBackgroundImage(img);
			this.genreService.saveGenre(genre);
		}
		model.addAttribute("genre", genre);
		return "genre.html";
	}
	
	@GetMapping("/admin/removeBackgroundImageFromGenre/{genreId}")
	public String removeBackgroundImageFromGenre(@PathVariable Long genreId, Model model) {
		Genre genre = this.genreService.getGenreById(genreId);
		genre.setBackgroundImage(null);
		this.genreService.saveGenre(genre);
		model.addAttribute("genre", genre);
		return "genre.html";
	
	}
	
	@PostMapping("/admin/addIconImageToGenre/{genreId}")
	public String addIconImageToGenre(Model model, @PathVariable Long genreId, @RequestParam("file") MultipartFile file) throws IOException {
		Genre genre = genreService.getGenreById(genreId);
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			genre.setIconImage(img);
			this.genreService.saveGenre(genre);
		}
		model.addAttribute("genre", genre);
		return "genre.html";
	}
	
	@GetMapping("/admin/removeIconImageFromGenre/{genreId}")
	public String removeIconImageFromGenre(@PathVariable Long genreId, Model model) {
		Genre genre = this.genreService.getGenreById(genreId);
		genre.setIconImage(null);
		this.genreService.saveGenre(genre);
		model.addAttribute("genre", genre);
		return "genre.html";
		
	}
	
	 //************************************* //
	  // CONTROLLER PER IMMAGINI DELLA PIATTAFORMA
	  //************************************* //
	    
	    @PostMapping("/admin/addImageToPlatform/{platformId}")
		public String addImageToPlatform(Model model, @PathVariable Long platformId, @RequestParam("file") MultipartFile file) throws IOException {
			Platform platform = platformService.getPlatformById(platformId);
			if (!file.isEmpty()) {
				Image img = new Image(file.getBytes());
				this.imageService.save(img);
				platform.setImage(img);
				this.platformService.savePlatform(platform);
			}
			model.addAttribute("platform", platform);
			return "platform.html";
		}
		
		@GetMapping("/admin/removeImageFromPlatform/{platformId}")
		public String removeImageFromPlatform(@PathVariable Long platformId, Model model) {
			Platform platform = this.platformService.getPlatformById(platformId);
			platform.setImage(null);
			this.platformService.savePlatform(platform);
			model.addAttribute("platform", platform);
			return "platform.html";
		
		}
		
		//************************************* //
	    // CONTROLLER PER IMMAGINI DELLO SVULUPPATORE
	    //************************************* //
		    
		    @PostMapping("/admin/addImageToDeveloper/{developerId}")
			public String addImageToDeveloper(Model model, @PathVariable Long developerId, @RequestParam("file") MultipartFile file) throws IOException {
				Developer developer = developerService.getDeveloperById(developerId);
				if (!file.isEmpty()) {
					Image img = new Image(file.getBytes());
					this.imageService.save(img);
					developer.setImage(img);
					this.developerService.saveDeveloper(developer);
				}
				model.addAttribute("developer", developer);
				return "developer.html";
			}
			
			@GetMapping("/admin/removeImageFromDeveloper/{developerId}")
			public String removeImageFromDeveloper(@PathVariable Long developerId, Model model) {
				Developer developer = this.developerService.getDeveloperById(developerId);
				developer.setImage(null);
				this.developerService.saveDeveloper(developer);
				model.addAttribute("developer", developer);
				return "developer.html";
			
			}
	
}
