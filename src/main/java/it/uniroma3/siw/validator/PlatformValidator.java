package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.service.PlatformService;

@Component
public class PlatformValidator implements Validator {

	@Autowired
	private PlatformService platformService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Platform.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Platform platform = (Platform) o;
		if(this.platformService.alreadyExists(platform))
			errors.reject("platform.duplicate");
	}

}
