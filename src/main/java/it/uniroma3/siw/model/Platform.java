package it.uniroma3.siw.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Platform {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String name;
	
	@NotNull
	@Max(2023)
	private Integer yearOfRelease;
	
	@Column(length = 2000)
	private String description;
	
	// Descrizione della piattaforma usata nel carosello quindi dovrà essere molto corta //
	@Column(length = 200)
	private String carouselDescription;
	
	@ManyToOne
	private Developer developer;
	
	@OneToOne
	private Image image;
	
	@ManyToMany
	private List<Game> games;
	
	
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

	public Integer getYearOfRelease() {
		return yearOfRelease;
	}

	public void setYearOfRelease(Integer yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	
	
	public String getCarouselDescription() {
		return carouselDescription;
	}

	public void setCarouselDescription(String carouselDescription) {
		this.carouselDescription = carouselDescription;
	}

	/***************************************
	 ********** EQUALS E HASHCODE **********
	 ***************************************/

	@Override
	public int hashCode() {
		return Objects.hash(Id, yearOfRelease);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Platform other = (Platform) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(yearOfRelease, other.yearOfRelease);
	}
	
	
}
