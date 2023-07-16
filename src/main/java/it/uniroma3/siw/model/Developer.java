package it.uniroma3.siw.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Developer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String name;
	
	@NotNull
	@Max(2023)
	private Integer yearOfFoundation;
	
	@Column(length = 2000)
	private String description;
	
	@OneToOne
	private Image image;
	
	@OneToMany(mappedBy = "developer")
	private List<Game> gamesProduced;
	
	@OneToMany(mappedBy = "developer")
	private List<Platform> platformsProduced;
	
	
	public Developer() {
		this.gamesProduced = new ArrayList<>();
		this.platformsProduced = new ArrayList<>();
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

	public Integer getYearOfFoundation() {
		return yearOfFoundation;
	}

	public void setYearOfFoundation(Integer yearOfFoundation) {
		this.yearOfFoundation = yearOfFoundation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}
	
	public List<Game> getGamesProduced() {
		return gamesProduced;
	}

	public void setGamesProduced(List<Game> gamesProduced) {
		this.gamesProduced = gamesProduced;
	}

	public List<Platform> getPlatformsProduced() {
		return platformsProduced;
	}

	public void setPlatformsProduced(List<Platform> platformsProduced) {
		this.platformsProduced = platformsProduced;
	}

	
	/***************************************
	 ********** EQUALS E HASHCODE **********
	 ***************************************/
	
	@Override
	public int hashCode() {
		return Objects.hash(Id, yearOfFoundation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Developer other = (Developer) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(yearOfFoundation, other.yearOfFoundation);
	}

}
