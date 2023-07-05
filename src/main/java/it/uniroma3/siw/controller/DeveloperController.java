package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.repository.DeveloperRepository;

@Controller
public class DeveloperController {
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	
	@GetMapping("/developer/{id}")
	public String getDeveloper(@PathVariable("id") Long id, Model model) {
		model.addAttribute("developer", this.developerRepository.findById(id).get());
		return "developer.html";
	}

	@GetMapping("/developers")
	public String showDevelopers(Model model) {
		model.addAttribute("developers", this.developerRepository.findAll());
		return "developers.html";
	}
	
}
