package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.PlatformRepository;

public class PlatformController {
	
	@Autowired
	private PlatformRepository platformRepository;
	

	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/formNewPlatform")
	public String formNewPlatform(Model model) {
		model.addAttribute("platform", new Platform());
		return "formNewPlatform.html";
	}

	@GetMapping("/platforms/{id}")
	public String getPlatform(@PathVariable("id") Long id, Model model) {
		model.addAttribute("platform", this.platformRepository.findById(id).get());
		return "platform.html";
	}

	@GetMapping("/platforms")
	public String showPlatforms(Model model) {
		model.addAttribute("platforms", this.platformRepository.findAll());
		return "platforms.html";
	}
	
}