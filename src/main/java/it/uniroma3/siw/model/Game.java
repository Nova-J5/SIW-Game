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
	
	@OneToOne
	private Image image;
	
	@OneToOne
	private Image imageForGames;
	
	@ManyToMany(mappedBy = "games")
	private List<Genre> genres;
	
	@ManyToMany(mappedBy = "games")
	private List<Platform> platforms;
	
	@OneToMany (mappedBy = "game", cascade = CascadeType.REMOVE)
	private List<Review> reviews;
	
	public Game() {
		this.genres = new ArrayList<>();
		this.platforms = new ArrayList<>();
		this.reviews = new ArrayList<>();
	}

	public String printStars(int score) {
		String s = "";
		for(int i=0; i<score; i++) {
			if (i==0)
				s="&#9733";
			else
				s = s + "&#9733";
		}
		for(int i=score; i<5; i++)
			s = s + "&#9734" ;
		return s;
	}
	
	public int intAvgScore() {
		return (int)this.avgScore();
	}
	
	public float avgScore() {
		float mediaSommata=0;
		for(Review r: this.reviews) {
			mediaSommata += r.getScore();
		}
		Float media =  (float)mediaSommata/reviews.size();
		return (Math.round(media*10)/10f);
				
	}
	
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImageForGames() {
		return imageForGames;
	}

	public void setImageForGames(Image imageForGames) {
		this.imageForGames = imageForGames;
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
