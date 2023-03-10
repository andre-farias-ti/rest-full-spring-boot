package br.com.andre.services;

import java.util.List;
import java.util.logging.Logger;

import br.com.andre.controllers.PersonController;
import br.com.andre.dto.PersonDto;
import br.com.andre.exceptions.RequiredObjectIsNullException;
import br.com.andre.mapper.DozerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andre.exceptions.ResourceNotFoundException;
import br.com.andre.model.Person;
import br.com.andre.repository.PersonRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;

	public List<PersonDto> findAll() {

		logger.info("Finding all people!");

		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonDto.class);
		persons
				.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonDto findById(Long id) {

		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, PersonDto.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public PersonDto create(PersonDto person) {

		if (person == null) throw new RequiredObjectIsNullException();

		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonDto.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonDto update(PersonDto person) {

		if (person == null) throw new RequiredObjectIsNullException();

		logger.info("Updating one person!");

		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo =  DozerMapper.parseObject(repository.save(entity), PersonDto.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {

		logger.info("Deleting one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
