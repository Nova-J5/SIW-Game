package it.uniroma3.siw.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
    
    @Autowired
    private ImageService imageService;

	
	@GetMapping("/formRegisterUser") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser.html";
	}
	
	@GetMapping("/login") 
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}
	
    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
    	Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "redirect:/";
        }
        return "redirect:/";
    }
    
	@PostMapping("/register")
	public String registerUser(
			@ModelAttribute("user") User user, BindingResult userBindingResult,
            @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
            @RequestParam("file") MultipartFile file, Model model) throws IOException {
		
		if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			if (!file.isEmpty()) {
				Image img = new Image(file.getBytes());
				this.imageService.save(img);
				user.setImage(img);
			}	
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
			return "formLogin.html";
		} else {
			return "formRegisterUser.html"; 
		}
	}
	
}
