package com.danielpm1982.springboot3_profile_manager.service;

import com.danielpm1982.springboot3_profile_manager.entity.Person;
import com.danielpm1982.springboot3_profile_manager.exception.PersonDeleteByIdFailedException;
import com.danielpm1982.springboot3_profile_manager.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public Person saveOrUpdate(Person person) {
        return personRepository.saveOrUpdate(person);
    }

    @Override
    public boolean deleteById(Long id) {
        if(personRepository.existsById(id)){
            personRepository.deleteById(id);
            if(personRepository.existsById(id)){
                throw new PersonDeleteByIdFailedException("Person ID = "+id+" exists at the Database but was not deleted !");
            }
            return true;
        } else{
            return false;
        }
    }

    @Override
    public List<Person> findPersonByFirstNameOrderByUserNameAsc(String firstname) {
        return personRepository.findPersonByFirstNameOrderByUserNameAsc(firstname);
    }

    @Override
    public List<Person> findPersonByLastNameOrderByUserNameAsc(String lastName) {
        return personRepository.findPersonByLastNameOrderByUserNameAsc(lastName);
    }

    @Override
    public List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName) {
        return personRepository.findPersonByLastNameOrderByFirstNameAsc(lastName);
    }

    @Override
    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName) {
        return personRepository.findPersonByFirstNameAndLastNameOrderByUserNameAsc(firstName, lastName);
    }
}

/*
Here, for simplicity, we reproduce the Repository implementation, although, in practice,
for multiple Entities and Repositories, the Service layer should be oriented towards the business
logic features and not the Data Model, as discussed at the text at the PersonService interface file.
Even so, the deleteById(Long) method here returns a different result (boolean) if compared to the
equivalent method at the Repository (void). The Service methods do not need to resemble in any way
the Data Model of the application, not on design, semantics, nomenclature or types. It should be
clear to the Controller, though, what service or feature each method is responsible for.
*/
