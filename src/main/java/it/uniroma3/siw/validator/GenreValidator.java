package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.service.GenreService;

@Component
public class GenreValidator implements Validator {
	
	@Autowired
	private GenreService genreService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Genre.class.equals(aClass);
		
	}

	@Override
	public void validate(Object o, Errors errors) {
		Genre genre = (Genre) o;
		if(genreService.alreadyExists(genre))
			errors.reject("genre.duplicate");

	}

}
