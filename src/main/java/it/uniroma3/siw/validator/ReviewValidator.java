package it.uniroma3.siw.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.controller.GlobalController;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.ReviewService;

@Component
public class ReviewValidator implements Validator {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private GlobalController globalController;

	@Override
	public boolean supports(Class<?> aClass) {
	      return Review.class.equals(aClass);

	}

	@Override
	public void validate(Object o, Errors errors) {
		Review review = (Review) o;
		Game game = review.getGame();
		List<Review> reviews =reviewService.findByUser(globalController.getCurrentUser());
		
		for(Review r : reviews) {
			if(r.getGame() == game) {
				errors.reject("review.duplicate");
			}
			System.out.println(r.getGame().getTitle() + game.getTitle());
			System.out.println("------check---------");
		}
	}

}
