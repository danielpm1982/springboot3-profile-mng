package com.danielpm1982.springboot3_profile_manager.mapper;

import com.danielpm1982.springboot3_profile_manager.dto.ObjectDTO;
import org.springframework.stereotype.Component;

@Component
public interface DTOToObjectMapper<S extends ObjectDTO, T> {

    public S objectToObjectDTO(T object);

    public T objectDTOToObject(S objectDTO);
}
