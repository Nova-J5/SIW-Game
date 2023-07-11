package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("user/{id}")
	private String getUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.findUserById(id);
		
		model.addAttribute("user", user);
		
		List<Game> currentlyPlaying = user.getCurrentlyPlaying();
		model.addAttribute("currentlyPlaying", currentlyPlaying);
		List<Game> played = user.getPlayed();
		model.addAttribute("played", played);
		List<Review> reviews = this.reviewService.getReviewsByUser(user);
		model.addAttribute("reviews", reviews);
		
		if(played.get(0) != null) 
			model.addAttribute("firstCurrentlyPlaying", played.get(0));
		if(played.get(1) != null) 
			model.addAttribute("secondCurrentlyPlaying", played.get(1));
		if(played.get(2) != null) 
			model.addAttribute("thirdCurrentlyPlaying", played.get(2));
		if(played.get(3) != null) 
			model.addAttribute("fourthCurrentlyPlaying", played.get(3));
		
		if(currentlyPlaying.get(0) != null) 
			model.addAttribute("firstPlayed", played.get(0));
		if(currentlyPlaying.get(1) != null) 
			model.addAttribute("secondPlayed", played.get(1));
		if(currentlyPlaying.get(2) != null) 
			model.addAttribute("thirdPlayed", played.get(2));
		if(currentlyPlaying.get(3) != null) 
			model.addAttribute("fourthPlayed", played.get(3));
		
		if(reviews.get(0) != null) 
			model.addAttribute("firstReviewed", reviews.get(0));
		if(reviews.get(1) != null) 
			model.addAttribute("secondReviewed", reviews.get(1));
		if(reviews.get(2) != null) 
			model.addAttribute("thirdReviewed", reviews.get(2));
		if(reviews.get(3) != null) 
			model.addAttribute("fourthReviewed", reviews.get(3));

		return "user.html";
	}
	
	@GetMapping("formUpdateUser/{id}")
	private String formUpdateUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.findUserById(id);
		model.addAttribute("user", user);
		
		return "formUpdateUser.html";
	}
	
	@PostMapping("updateUser/{id}")
	public String updateUser(@PathVariable("id") Long id, Model model,
			@RequestParam("name") String name, @RequestParam("surname") String surname,
			@RequestParam("email") String email, @RequestParam("file") MultipartFile file) throws IOException {
				
		User user = this.userService.findUserById(id);
		this.userService.updateUser(user, name, surname, email);
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			user.setImage(img);
		}		
		this.userService.saveUser(user);
		model.addAttribute("currentlyPlaying", user.getCurrentlyPlaying());
		model.addAttribute("played", user.getPlayed());
		model.addAttribute("reviews", this.reviewService.getReviewsByUser(user));
		model.addAttribute("user", user);
		return "user.html";
	}
	
}

