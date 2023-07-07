package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.DeveloperRepository;
import it.uniroma3.siw.repository.PlatformRepository;
import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.PlatformService;

@Controller
public class PlatformController {
	
	@Autowired
	private PlatformRepository platformRepository;
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private DeveloperService developerService;
	

	@GetMapping("/platform/{id}")
	public String getPlatform(@PathVariable("id") Long id, Model model) {
		model.addAttribute("platform", this.platformRepository.findById(id).get());
		return "platform.html";
	}

	@GetMapping("/platforms")
	public String showPlatforms(Model model) {
		model.addAttribute("platforms", this.platformRepository.findAll());
		return "platforms.html";
	}
	
	@GetMapping("/admin/formNewPlatform")
	public String formNewPlatform(Model model) {
		model.addAttribute("platform", new Platform());
		model.addAttribute("developers", this.developerRepository.findAll());
		
		return "admin/formNewPlatform.html";
	}
	
	@PostMapping("/admin/newPlatform")
	public String newPlatform(@ModelAttribute("platform") Platform platform, 
			@RequestParam("developerId") Long developerId, Model model) {
		if (!platformRepository.existsByNameAndYearOfRelease(platform.getName(), platform.getYearOfRelease())) {
			platform.setDeveloper(this.developerService.getDeveloper(developerId));
			this.platformService.savePlatform(platform);
			model.addAttribute("platform", platform);
			return "platform.html";
		} else {
			model.addAttribute("messaggioErrore", "Questa piattaforma esiste già");
			return "admin/formNewPlatform.html"; 
		}
	}
	
}
