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
import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private GameService gameService;
	@Autowired
	private GlobalController globalController;
	
	@GetMapping("/default/formNewReview/{gameId}")
	public String formNewReview(@PathVariable("gameId") Long gameId, Model model) {
		Review review = new Review();
		model.addAttribute("review", review);
		return "default/formNewReview.html";
	}
	
	@PostMapping("/default/newReview/{gameId}")
	public String newReview(@ModelAttribute("review") Review review, @PathVariable("gameId") Long gameId, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			Game game = this.gameService.getGameById(gameId);
			
			review.setGame(game);
			review.setUser(globalController.getCurrentUser());
			this.reviewService.saveReview(review);
			
			model.addAttribute("game", game);
			game.getReviews().add(review);
			this.gameService.saveGame(game);
			return "game.html";
		} else {
			return "default/formNewReview.html";
		}
	}
}
