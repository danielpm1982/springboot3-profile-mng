package com.danielpm1982.springboot3_profile_manager.repository;

import com.danielpm1982.springboot3_profile_manager.entity.Person;
import java.util.List;

public interface PersonCustomRepository {
    public Person saveOrUpdate(Person person);

    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName);
}

/*
This is a fragment interface for adding custom implemented methods at PersonRepository.
This interface, or its impl class, should not be used as independent beans or Repositories.
This interface, and its impl class, should be extended by the PersonRepository, which is the Repository
bean to be used at the Service.
 */
