package it.uniroma3.siw.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Game {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String title;

	@NotNull
	@Min(1958)
	@Max(2023)
	private Integer year;
	
	@Column(length = 2000)
	private String description;

	@ManyToOne
	private Developer developer;
	
	@ManyToMany(mappedBy = "games")
	private List<Genre> genres;
	
	@ManyToMany(mappedBy = "games")
	private List<Platform> platforms;
	
	@OneToMany (mappedBy = "game")
	private List<Review> reviews;
	
	
	/*************************************
	 ********** GETTER E SETTER **********
	 *************************************/

	
	
	
	public Long getId() {
		return Id;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void seTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	
	/***************************************
	 ********** EQUALS E HASHCODE **********
	 ***************************************/

	@Override
	public int hashCode() {
		return Objects.hash(Id, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(year, other.year);
	}
	
	
}
