package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.repository.GameRepository;

public class GameValidator implements Validator {

	@Autowired 
	private GameRepository gameRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
	      return Game.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Game game = (Game)o;
	    if (game.getTitle()!=null && game.getYear()!=null
			&& gameRepository.existsByTitleAndYear(game.getTitle(), game.getYear())) {
	    	/* 
	    	 * Specifica che c'è stato un errore nella validazione e registra
	    	 * un codice di errore (la stringa "game.duplicate")
	    	 */
	    	errors.reject("game.duplicate");
	    }
		
	}

}
