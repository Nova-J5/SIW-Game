package it.uniroma3.siw.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	private CredentialsService credentialsService;
	
    @ModelAttribute("userDetails")
    public UserDetails getUser() {
        UserDetails user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        
        return user;
    }
    
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        UserDetails user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    	if(user!=null)
    	{
    		return this.credentialsService.getCredentialsByUsername(user.getUsername()).getUser();
    	}
    	return null;
    }
    
    
    
}