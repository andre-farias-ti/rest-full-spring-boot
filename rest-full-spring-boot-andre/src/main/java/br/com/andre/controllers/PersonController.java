package br.com.andre.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andre.model.Person;
import br.com.andre.services.PersonServices;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

	@Autowired
	private PersonServices personServices;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findByPerson(@PathVariable(value = "id") Long id) throws Exception {
		
		return personServices.findById(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAllPerson(){
		
		return personServices.findAll();
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Person criatePerson(@RequestBody Person person){
		
		return personServices.create(person);
	}
	
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Person updatePerson(@RequestBody Person person){
		
		return personServices.update(person);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id") Long id) throws Exception {
		 personServices.delete(id);
		 return ResponseEntity.noContent().build();
	}
	
}
