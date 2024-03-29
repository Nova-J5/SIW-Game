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
import it.uniroma3.siw.service.GameService;
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
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GlobalController globalController;
	
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
		
		if (!played.isEmpty()) {
		    int maxElements = Math.min(4, played.size());
		    for (int i = 0; i < maxElements; i++) {
		        if (played.get(i) != null) {
		            String attributeName;
		            if (i == 1) {
		                attributeName = "secondPlayed";
		            } else if (i == 2) {
		                attributeName = "thirdPlayed";
		            } else if (i == 3) {
		                attributeName = "fourthPlayed";
		            } else {
		                attributeName = "firstPlayed";
		            }
		            model.addAttribute(attributeName, played.get(i));
		        }
		    }
		}
		
		if (!currentlyPlaying.isEmpty()) {
		    int maxElements = Math.min(4, currentlyPlaying.size());

		    for (int i = 0; i < maxElements; i++) {
		        if (currentlyPlaying.get(i) != null) {
		            String attributeName;
		            
		            if (i == 1) {
		                attributeName = "secondCurrentlyPlaying";
		            } else if (i == 2) {
		                attributeName = "thirdCurrentlyPlaying";
		            } else if (i == 3) {
		                attributeName = "fourthCurrentlyPlaying";
		            } else {
		                attributeName = "firstCurrentlyPlaying";
		            }
		            model.addAttribute(attributeName, currentlyPlaying.get(i));
		        }
		    }
		}
		
		if (!reviews.isEmpty()) {
		    int maxElements = Math.min(4, reviews.size());
		    for (int i = 0; i < maxElements; i++) {
		        if (reviews.get(i) != null) {
		            String attributeName;
		            
		            if (i == 1) {
		                attributeName = "secondReviewed";
		            } else if (i == 2) {
		                attributeName = "thirdReviewed";
		            } else if (i == 3) {
		                attributeName = "fourthReviewed";
		            } else {
		                attributeName = "firstReviewed";
		            }
		            
		            model.addAttribute(attributeName, reviews.get(i));
		        }
		    }
		}

		return "user.html";
	}
	
	@GetMapping("formUpdateUser/{id}")
	private String formUpdateUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.findUserById(id);
		model.addAttribute("user", user);
		
		return "formUpdateUser.html";
	}
	
	@PostMapping("updateUser/{id}")
	public String updateUser(@PathVariable("id") Long id,
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
		
		return "redirect:/user/" + user.getId();
	}
	
	@GetMapping("/addPlayed/{id}")
	public String addPlayedGame(@PathVariable("id") Long id, Model model) {
		Game game = this.gameService.getGameById(id);
		User user = this.globalController.getCurrentUser();
		user.getPlayed().add(game);
		this.userService.saveUser(user);
		return "redirect:/game/" + game.getId();
	}
	
	@GetMapping("/addCurrentlyPlaying/{id}")
	public String addCurrentlyPlayingGame(@PathVariable("id") Long id, Model model) {
		Game game = this.gameService.getGameById(id);
		User user = this.globalController.getCurrentUser();
		user.getCurrentlyPlaying().add(game);
		this.userService.saveUser(user);
		return "redirect:/game/" + game.getId();
	}
	
	
	@GetMapping("/currentlyPlaying/{id}")
	private String getCurrentlyPlayingGames(@PathVariable("id") Long id, Model model) {
		model.addAttribute("games", this.userService.findUserById(id).getCurrentlyPlaying());
		return "userGames.html";
	}
	
	@GetMapping("/played/{id}")
	private String getPlayedGames(@PathVariable("id") Long id, Model model) {
		model.addAttribute("games", this.userService.findUserById(id).getPlayed());
		return "userGames.html";
	}
	
	@GetMapping("/removeFromPlayed/{id}")
	private String removeFromPlayed(@PathVariable("id") Long id, Model model) {
		Game game = this.gameService.getGameById(id);
		User user = this.globalController.getCurrentUser();
		user.getPlayed().remove(game);
		this.userService.saveUser(user);
		return "redirect:/game/" + game.getId();
	}
	
	@GetMapping("/removeFromCurrentlyPlayng/{id}")
	private String removeFromCurrentlyPlayng(@PathVariable("id") Long id, Model model) {
		Game game = this.gameService.getGameById(id);
		User user = this.globalController.getCurrentUser();
		user.getCurrentlyPlaying().remove(game);
		this.userService.saveUser(user);
		return "redirect:/game/" + game.getId();
	}
}

