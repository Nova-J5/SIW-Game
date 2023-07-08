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
import it.uniroma3.siw.model.Platform;
import it.uniroma3.siw.repository.DeveloperRepository;
import it.uniroma3.siw.repository.PlatformRepository;
import it.uniroma3.siw.service.DeveloperService;
import it.uniroma3.siw.service.ImageService;
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

	@Autowired
	private ImageService imageService;
	
	
	// ********************************************** //
	// CONTROLLER PER RICHIESTE DI UN UTENTE GENERICO
	//********************************************** //

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
	
	
	//************************************* //
	// CONTROLLER PER RICHIESTE DI UN ADMIN
	//************************************* //
	
	@GetMapping("/admin/formNewPlatform")
	public String formNewPlatform(Model model) {
		model.addAttribute("platform", new Platform());
		model.addAttribute("developers", this.developerRepository.findAll());
		
		return "admin/formNewPlatform.html";
	}
	
	@PostMapping("/admin/newPlatform")
	public String newPlatform(@ModelAttribute("platform") Platform platform, BindingResult bindingResult, Model model,
			@RequestParam("developerId") Long developerId, @RequestParam("file") MultipartFile file) throws IOException {
		
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			platform.setImage(img);
		}
		
		//da aggiungere il validator
		if (!platformRepository.existsByNameAndYearOfRelease(platform.getName(), platform.getYearOfRelease()) && !bindingResult.hasErrors()) {
			Developer developer = this.developerService.getDeveloperById(developerId);
			
			this.platformService.inizializePlatform(platform,developer);
			
			this.developerService.saveDeveloper(developer);
			this.platformService.savePlatform(platform);
			model.addAttribute("platform", platform);
			return "platform.html";
		} else {
			return "admin/formNewPlatform.html"; 
		}
	}
	
}
