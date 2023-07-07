package it.uniroma3.siw.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Genre {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String name;
	
	@Column(length = 2000)
	private String description;
	
	@OneToOne
	private Image backgroundImage;
	
	@OneToOne
	private Image iconImage;
	
	@ManyToMany
	private List<Game> games;
	
	public Genre() {
		this.games = new ArrayList<>();
	}
	/*************************************
	 ********** GETTER E SETTER **********
	 *************************************/

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Image getIconImage() {
		return iconImage;
	}

	public void setIconImage(Image iconImage) {
		this.iconImage = iconImage;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	
	/***************************************
	 ********** EQUALS E HASHCODE **********
	 ***************************************/

	@Override
	public int hashCode() {
		return Objects.hash(Id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		return Objects.equals(name, other.name);
	}
	
}
