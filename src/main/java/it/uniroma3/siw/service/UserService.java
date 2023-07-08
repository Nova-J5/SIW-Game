package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;
	
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
    
	@Transactional
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	@Transactional
	public User findUserById(Long id) {
		Optional<User> result = userRepository.findById(id);
		return result.orElse(null);
	}
	

}
