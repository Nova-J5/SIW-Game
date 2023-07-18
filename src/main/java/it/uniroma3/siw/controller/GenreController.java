package it.uniroma3.siw.controller;

import java.io.IOException;

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

import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.GenreRepository;
import it.uniroma3.siw.service.GenreService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.validator.GenreValidator;
import jakarta.validation.Valid;

@Controller
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreService genreService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private GenreValidator genreValidator;
	
	// ********************************************** //
	// CONTROLLER PER RICHIESTE DI UN UTENTE GENERICO
	//********************************************** //
	
	@GetMapping("/genre/{id}")
	public String getGenre(@PathVariable("id") Long id, Model model) {
		model.addAttribute("genre", this.genreRepository.findById(id).get());
		return "genre.html";
	}
	
	//************************************* //
	// CONTROLLER PER RICHIESTE DI UN ADMIN
	//************************************* //
	
	
	@GetMapping("/admin/formNewGenre")
	public String formNewGenre(Model model) {
		model.addAttribute("genre", new Genre());
		return "admin/formNewGenre.html";
	}
	
	@PostMapping("/admin/newGenre")
	public String newGenre(@Valid @ModelAttribute("genre") Genre genre, BindingResult bindingResult, Model model, @RequestParam("icon") MultipartFile icon,
			@RequestParam("background") MultipartFile background) throws IOException {
		
		this.genreValidator.validate(genre, bindingResult);
		if (!bindingResult.hasErrors()) {
			
			if (!icon.isEmpty()) {
				Image img = new Image(icon.getBytes());
				this.imageService.save(img);
				genre.setIconImage(img);
			}
			
			if (!background.isEmpty()) {
				Image img = new Image(background.getBytes());
				this.imageService.save(img);
				genre.setBackgroundImage(img);
			}	
			
			this.genreService.saveGenre(genre);
			model.addAttribute("genre", genre);
			return "genre.html";
		} else {
			return "admin/formNewGenre.html"; 
		}
	}
	
	@GetMapping("/admin/updateGenre/{genreId}")
	public String updateGenre(Model model, @PathVariable("genreId") Long genreId) {
		model.addAttribute("genre", this.genreService.getGenreById(genreId));
		
		return "admin/updateGenre.html";
	}
	
	@PostMapping("/admin/modifyGenre/{genreId}")
	public String modifyGenre(Model model, @PathVariable("genreId") Long genreId, @RequestParam("name") String name,
			 @RequestParam("icon") MultipartFile icon, @RequestParam("background") MultipartFile background) throws IOException {
		
		System.out.println("apre il metodo \n");
		Genre genre  = this.genreService.getGenreById(genreId);
		
		System.out.println("prende il genere \n");
		
		if (!icon.isEmpty()) {
			  Image imgIcon = new Image(icon.getBytes());
			  this.imageService.save(imgIcon);
			  genre.setIconImage(imgIcon);
		  }	
		
		if (!background.isEmpty()) {
			Image imgBackground = new Image(background.getBytes());
			this.imageService.save(imgBackground);
			genre.setBackgroundImage(imgBackground);
		}	
		
		System.out.println("finito controlli \n");
		
		this.genreService.modifyGenre(genre, name);
		System.out.println("modificato \n");
		this.genreService.saveGenre(genre);
		System.out.println("finito \n");
		return "redirect:/";
		}
	
	@GetMapping("/admin/deleteGenre/{id}")
	public String deleteGenre(@PathVariable("id") Long id, Model model) {
		this.genreService.deleteGenre(id);
		model.addAttribute("genres", this.genreService.getAllGenres());
		return "index.html";
	}
	
}
