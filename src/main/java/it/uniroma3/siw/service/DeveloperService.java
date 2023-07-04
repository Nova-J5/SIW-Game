package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.model.Developer;
import it.uniroma3.siw.repository.DeveloperRepository;
import jakarta.transaction.Transactional;

public class DeveloperService {

	@Autowired
	private DeveloperRepository developerRepository;

	
	@Transactional
	public Developer addDeveloper(Developer developer) {
		return this.developerRepository.save(developer);
	}
	
	@Transactional
    public Developer getDeveloper(Long id) {
        Optional<Developer> result = this.developerRepository.findById(id);
        return result.orElse(null);
    }
	
	@Transactional
	public List<Developer> getDevelopersByName(String name) {
		return this.developerRepository.findByName(name);
	}
	
	@Transactional
    public List<Developer> getDevelopersByYearOfFoundation(Integer yearOfFoundation) {
		return this.developerRepository.findByYearOfFoundation(yearOfFoundation);
    }
	
	@Transactional
    public List<Developer> getAllDevelopers() {
        List<Developer> result = new ArrayList<>();
        Iterable<Developer> iterable = this.developerRepository.findAll();
        for(Developer developer : iterable)
            result.add(developer);
        return result;
    }
	
	@Transactional
	public void updateDeveloper(Developer updatedDeveloper) {
		this.developerRepository.save(updatedDeveloper);
	}

	@Transactional
	public void deleteDeveloper(Long id) {
		this.developerRepository.deleteById(id);		
	}
	
	@Transactional
	public boolean alreadyExists(Developer developer) {
		return this.developerRepository.existsByNameAndYearOfFoundation(developer.getName(), developer.getYearOfFoundation());
	}
	
}
