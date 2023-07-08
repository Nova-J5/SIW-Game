package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.Optional;

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

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.GameRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("user/{id}")
	private String getUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("currentlyPlaying", user.getCurrentlyPlaying());
		model.addAttribute("played", user.getPlayed());
		model.addAttribute("reviews", this.reviewService.getReviewsByUser(user));
		
		return "user.html";
	}
	
	@GetMapping("formUpdateUser/{id}")
	private String formUpdateUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.findUserById(id);
		model.addAttribute("user", user);
		
		return "formUpdateUser.html";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
				
		if (!bindingResult.hasErrors()) {
			if (!file.isEmpty()) {
				Image img = new Image(file.getBytes());
				this.imageService.save(img);
				user.setImage(img);
			}		
			this.userService.saveUser(user);
			model.addAttribute("user", user);
			return "user.html";
		} else {
			return "formUpdateUser.html"; 
		}
	}
}


