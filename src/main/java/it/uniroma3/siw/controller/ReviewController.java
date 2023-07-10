package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.ReviewValidator;
import jakarta.validation.Valid;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private GameService gameService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewValidator reviewValidator;
	
	@GetMapping("/default/formNewReview/{gameId}")
	public String formNewReview(@PathVariable("gameId") Long gameId, Model model) {
		Review review = new Review();
		model.addAttribute("review", review);
		return "default/formNewReview.html";
	}
	
	@PostMapping("/default/newReview/{gameId}/{userId}")
	public String newReview(@Valid @ModelAttribute("review") Review review, 
			@PathVariable("gameId") Long gameId, @PathVariable("userId") Long userId,
			BindingResult bindingResult, Model model) {
		
		Game game = this.gameService.getGameById(gameId);
		review.setGame(game);
		
		this.reviewValidator.validate(review, bindingResult);
		if(!bindingResult.hasErrors()) {
			User user = this.userService.findUserById(userId);
			
			review.setUser(user);
			//game.getReviews().add(review);
			//user.getReviews().add(review);
			this.reviewService.saveReview(review);
			
			model.addAttribute("game", game);
			this.gameService.saveGame(game);
			return "game.html";
		} else {
			return "default/formNewReview.html";
		}
	}
	
	@GetMapping("/reviews/{id}")
	private String getReviewedGames(@PathVariable("id") Long id, Model model) {
		User user =  this.userService.findUserById(id);
		model.addAttribute("reviews", this.reviewService.getReviewsByUser(user));
		return "reviewedGames.html";
	}
}
