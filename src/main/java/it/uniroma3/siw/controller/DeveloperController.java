package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.validator.DeveloperValidator;
import jakarta.validation.Valid;

@Controller
public class DeveloperController {
	
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private DeveloperValidator developerValidator;	
	
	@GetMapping("/developer/{id}")
	public String getDeveloper(@PathVariable("id") Long id, Model model) {
		model.addAttribute("developer", this.developerService.getDeveloperById(id));
		return "developer.html";
	}

	@GetMapping("/developers")
	public String showDevelopers(Model model) {
		model.addAttribute("developers", this.developerService.getAllDevelopers());
		return "developers.html";
	}
	
	@GetMapping("/admin/formNewDeveloper")
	public String formNewDeveloper(Model model) {
		model.addAttribute("developer", new Developer());		
		return "admin/formNewDeveloper.html";
	}
	
	@PostMapping("/admin/newDeveloper")
	public String newDeveloper(@Valid @ModelAttribute("developer") Developer developer, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			developer.setImage(img);
		}		
		
		this.developerValidator.validate(developer, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			this.developerService.saveDeveloper(developer);
			model.addAttribute("developer", developer);
			return "developer.html";
		} else {
			return "admin/formNewDeveloper.html"; 
		}
	}
	
}
