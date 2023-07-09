package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.service.GameService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.model.Game;
import it.uniroma3.siw.model.Image;

@Controller
public class ImageController {
	@Autowired
    private ImageService imageService;
	@Autowired
	private GameService gameService;

    @GetMapping("/display/image/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        Image img = this.imageService.findById(id);
        byte[] image = img.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } 
    
    @PostMapping("/admin/addImageToGame/{gameId}")
	public String addImageToGame(Model model, @PathVariable Long gameId, @RequestParam("file") MultipartFile file) throws IOException {
		Game game = gameService.getGameById(gameId);
		if (!file.isEmpty()) {
			Image img = new Image(file.getBytes());
			this.imageService.save(img);
			game.setImage(img);
			this.gameService.saveGame(game);
		}
		model.addAttribute("game", game);
		return "game.html";
	}
	
	@GetMapping("/admin/removeImageFromGame/{gameId}")
	public String removeImageFromGame(@PathVariable Long gameId, Model model) {
		Game game = this.gameService.getGameById(gameId);
		game.setImage(null);
		this.gameService.saveGame(game);
		model.addAttribute("game", game);
		return "game.html";
	
	}

}
