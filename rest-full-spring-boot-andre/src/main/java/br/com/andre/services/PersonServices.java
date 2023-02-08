package br.com.andre.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andre.exceptions.ResourceNotFoundException;
import br.com.andre.model.Person;
import br.com.andre.repository.PersonRepository;


@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository personRepository;
	
	public Person create(Person person) {
		logger.info("Criando Person");
		return personRepository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Atualizando Person");
		
		var entity = personRepository.findById(person.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Nenhum registro encontrado para este id"));
		
		entity.setFristName(person.getFristName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		entity.setAddress(person.getAddress());
		
		return personRepository.save(entity);
	}
	
	public void delete(Long id) {
		logger.info("Deletando Person");
		
		var entity = personRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Nenhum registro encontrado para este id"));
		 personRepository.delete(entity);
		
	}
	
	public Person findById(Long id) {
		
		logger.info("Buscando uma Pessoa!");
		
		Person person = new Person();
		person.setId(id);
		person.setFristName("André");
		person.setLastName("Farias");
		person.setGender("male");
		person.setAddress("João Pessoa");
		
		return personRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Nenhum registro encontrado para este id"));
	}
	
	public List<Person> findAll() {
		
		logger.info("Buscando todas as Pessoa!");
		
		return personRepository.findAll();
	}
}
