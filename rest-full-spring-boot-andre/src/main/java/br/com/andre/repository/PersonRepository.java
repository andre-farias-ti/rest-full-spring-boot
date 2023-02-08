package br.com.andre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.andre.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	
}
