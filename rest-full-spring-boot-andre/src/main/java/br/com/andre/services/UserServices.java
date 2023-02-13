package br.com.andre.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.andre.exceptions.ResourceNotFoundException;
import br.com.andre.model.Person;
import br.com.andre.model.User;
import br.com.andre.repository.UserRepository;


@Service
public class UserServices implements UserDetailsService{

	private Logger logger = Logger.getLogger(UserServices.class.getName());
	
	@Autowired
	UserRepository userRepository;
	
	public UserServices(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Buscando uma User by name! " + username + "!");
		var user = userRepository.findByUsername(username);
		
		if(user != null) {
			return user;
		}else {
			throw new UsernameNotFoundException("Username " + username + " não encontrado");
		}
	}

}
