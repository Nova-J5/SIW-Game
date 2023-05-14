package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class User {
	
	@NotBlank
	@Column(unique = true)
	private String username;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Column(unique = true)
	private String email;
	
	
}
