package it.uniroma3.siw.controller;

import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	
	// ********************************************** //
	// CONTROLLER PER RICHIESTE DI UN UTENTE GENERICO
	//********************************************** //
	
	
	@GetMapping("/developer/{id}")
	public String getDeveloper(@PathVariable("id") Long id, Model model) {
		model.addAttribute("developer", this.developerService.getDeveloperById(id));
		return "developer.html";
	}

	@GetMapping("/developers")
	public String showDevelopers(Model model) {
		List<Developer> developers = this.developerService.getAllDevelopers();
		Collections.sort(developers, Comparator.comparing(Developer :: getName));
		model.addAttribute("developers", developers);
		return "developers.html";
	}
	
	
	//************************************* //
	// CONTROLLER PER RICHIESTE DI UN ADMIN
	//************************************* //
	
	@GetMapping("/admin/formNewDeveloper")
	public String formNewDeveloper(Model model) {
		model.addAttribute("developer", new Developer());		
		return "admin/formNewDeveloper.html";
	}
	
	@PostMapping("/admin/newDeveloper")
	public String newDeveloper(@Valid @ModelAttribute("developer") Developer developer, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		this.developerValidator.validate(developer, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			if (!file.isEmpty()) {
				Image img = new Image(file.getBytes());
				this.imageService.save(img);
				developer.setImage(img);
			}	
			
			this.developerService.saveDeveloper(developer);
			model.addAttribute("developer", developer);
			return "developer.html";
		} else {
			return "admin/formNewDeveloper.html"; 
		}
	}
	
	@GetMapping("/admin/updateDeveloper/{developerId}")
	public String updateDeveloper(Model model, @PathVariable("developerId") Long developerId) {
		model.addAttribute("developer", this.developerService.getDeveloperById(developerId));
		
		return "admin/updateDeveloper.html";
	}
	
	@PostMapping("/admin/modifyDeveloper/{developerId}")
	public String modifyDeveloper(Model model, @PathVariable("developerId") Long developerId, @RequestParam("name") String name,
			@RequestParam("year") Integer year, @RequestParam("description") String description){
		Developer developer  = this.developerService.getDeveloperById(developerId);
		this.developerService.modifyDeveloper(developer, name, year, description);
		this.developerService.saveDeveloper(developer);
		model.addAttribute("developer", developer);
		return "developer.html";
	}
	
	@GetMapping("/admin/deleteDeveloper/{id}")
	public String deleteDeveloper(@PathVariable("id") Long id, Model model) {
		this.developerService.deleteDeveloper(id);
		model.addAttribute("developers", this.developerService.getAllDevelopers());
		return "developers.html";
	}
	
}
