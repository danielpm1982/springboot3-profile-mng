package com.danielpm1982.springboot3_profile_manager.mapper;

import com.danielpm1982.springboot3_profile_manager.dto.PersonDTO;
import com.danielpm1982.springboot3_profile_manager.entity.Person;
import jakarta.annotation.Priority;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
//@Order(Ordered.LOWEST_PRECEDENCE)
@Priority(PriorityOrdered.LOWEST_PRECEDENCE)
@Log
public class FooPersonMapper implements DTOToObjectMapper<PersonDTO, Person>{
    private final ModelMapper modelMapper;

    public FooPersonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        log.warning("FoolPersonMapper, with lowest-precedence order, loaded at Application Context !");
    }

    @Override
    public PersonDTO objectToObjectDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public Person objectDTOToObject(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
