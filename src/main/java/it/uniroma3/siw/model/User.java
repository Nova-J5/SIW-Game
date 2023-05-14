package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name;
	private String surname;
	private String email;
	
	@OneToMany
	private List<Game> currentlyPlaying;
	
	@OneToMany
	private List<Game> played;

	
	
    public List<Game> getCurrentlyPlaying() {
		return currentlyPlaying;
	}

	public void setCurrentlyPlaying(List<Game> currentlyPlaying) {
		this.currentlyPlaying = currentlyPlaying;
	}

	public List<Game> getPlayed() {
		return played;
	}

	public void setPlayed(List<Game> played) {
		this.played = played;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}