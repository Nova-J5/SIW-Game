package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;
import jakarta.transaction.Transactional;


@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	
	@Transactional
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}
	
	@Transactional
    public Review getReview(Long id) {
        Optional<Review> result = this.reviewRepository.findById(id);
        return result.orElse(null);
    }

	@Transactional
	public List<Review> getReviewsByGame(Game game) {
		return this.reviewRepository.findByGame(game);
	}
	
	@Transactional
    public List<Review> getAllReviews() {
        List<Review> result = new ArrayList<>();
        Iterable<Review> iterable = this.reviewRepository.findAll();
        for(Review review : iterable)
            result.add(review);
        return result;
    }
	
	@Transactional
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}
	
}
