package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.repository.DeveloperRepository;

@Component
public class DeveloperValidator implements Validator {

	@Autowired 
	private DeveloperRepository developerRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
	      return Developer.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Developer developer = (Developer)o;
	    if (developer.getName()!=null && developer.getYearOfFoundation()!=null
			&& developerRepository.existsByNameAndYearOfFoundation(developer.getName(), developer.getYearOfFoundation())) {

	    	errors.reject("developer.duplicate");
	    }
	}
}
