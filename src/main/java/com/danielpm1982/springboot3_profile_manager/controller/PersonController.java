package com.danielpm1982.springboot3_profile_manager.controller;

import com.danielpm1982.springboot3_profile_manager.dto.PersonDTO;
import com.danielpm1982.springboot3_profile_manager.entity.Person;
import com.danielpm1982.springboot3_profile_manager.mapper.DTOToObjectMapper;
import com.danielpm1982.springboot3_profile_manager.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {
    private final PersonService personService;
    private final DTOToObjectMapper<PersonDTO, Person> myPersonMapper;
//    private final ObjectMapper myObjectMapper; // no need for explicitly manipulation of Jackson API when marshalling or unmarshalling Java objects
//    private final ObjectMapper myXmlMapper; // no need for explicitly manipulation of Jackson API when marshalling or unmarshalling Java objects

    public PersonController(
            PersonService personService, DTOToObjectMapper<PersonDTO, Person> myPersonMapper,
            ObjectMapper myObjectMapper, @Qualifier("xmlMapper") ObjectMapper myXmlMapper) {
        this.personService = personService;
        this.myPersonMapper = myPersonMapper;
//        this.myObjectMapper = myObjectMapper;
//        this.myXmlMapper = myXmlMapper;
    }
    // ObjectMapper bean injection ambiguity above is resolved with @Primary (at config.Config class), for "myObjectMapper" bean, and with @Qualifier,
    // for "myXmlMapper". It could also have been used the strategy to name the parameter variables of such two beans above exactly with the names of
    // those beans - myObjectMapper -> objectMapper and myXmlMapper -> xmlMapper. Then, Spring would resolve it automatically.
    // DTOToObjectMapper bean injection ambiguity above is resolved with @Priority (@Order won't work), at mapper.PersonMapper.

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Page<Person>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(personService.findAll(pageable));
        // Query results are divided in pages, according to the page size, and only the current page results are shown at the response body
        // of the current endpoint response - along with metadata showing how many pages exist and what's the actual page being shown,
        // as well as the sortBy property chosen and if at an ascending or descending order... all that with a 200 status code, for both
        // empty or not empty result list (at the response body).
        // The returned response body data may be structured in JSON or XML format according to what's been set at the received request header
        // field "accept", if "application/json" or "application/xml", respectively.
        // For different results (of other pages), or different Sort criteria, the user simply has to change the request params in a new request
        // to this same endpoint.
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Person> findById(@PathVariable("id") Long personId){
        Optional<Person> result = personService.findById(personId);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        // if the PersonService Optional<Person> contains any value, this value is returned at the response body of this endpoint response,
        // with a 200 status code. The returned response body data may be structured in JSON or XML format according to what's been set at the
        // received request header field "accept", if "application/json" or "application/xml", respectively.
        // if the PersonService Optional<Person> contains no value, nothing is returned at the response body of this endpoint response,
        // with a 404 status code.
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    ResponseEntity<Person> existsById(@PathVariable("id") Long personId){
        boolean result = personService.existsById(personId);
        if (result){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
        // similar to @GetMapping("/{id}") but without returning anybody, only status codes (200 or 404).
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Person> deleteById(@PathVariable("id") Long personId) {
        boolean result = personService.deleteById(personId);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        // if the PersonService boolean is true, it means the resource was found and deleted. Nothing is returned at the response body of this
        // endpoint, with a 204 status code.
        // if the PersonService boolean is false, it means the resource was not found (consequently not deleted). Nothing is returned at the
        // response body of this endpoint, with a 404 status code.
        // throws (internally) a PersonDeleteByIdFailedException RuntimeException if the resource was found, but not deleted.
        // See other related Exceptions at GlobalExceptionHandler.java class.
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Person> saveOrUpdateById(@PathVariable("id") Long personId, @Valid @RequestBody PersonDTO personDTO){
        Person personToBeSavedOrUpdated = myPersonMapper.objectDTOToObject(personDTO);
        personToBeSavedOrUpdated.setId(personId);   // overrides any id passed at the @RequestBody json by the id passed at the URI (pathVariable)
        Person result = personService.saveOrUpdate(personToBeSavedOrUpdated);
        return ResponseEntity.ok(result);
        // this endpoint uses the service that calls the @PersonRepository custom implemented method saveOrUpdate(Person), and not the save(Person)
        // method from the CrudRepository. That way, inside the custom implementation of the saveOrUpdate(Person) method, it is checked if the
        // passed personId exists at the DB, if not, it creates a new Person and inserts it at the DB. If the personId already exists, instead, it
        // proceeds with an update. If no Exception is thrown along the way, it always returns a Person object at the response body, with a 200
        // status code.
        // Unmarshalling (deserializing) from JSON to PersonDTO, at the request - when MIME type is set as "application/json", is done automatically
        // (because of @RequestBody), as well as the marshalling of the Person instance back to JSON format, at the response (because of @RestController,
        // which includes @ResponseBody). No need to explicitly use Jackson here, although the dependency import is mandatory, at the POM.xml, for
        // Spring to be able to use it behind the scenes. In case the request MIME type is set as "application/xml", also the unmarshalling from XML to
        // PersonDTO, as well as the marshalling from Person to XML, is done automatically by the @RequestBody and @ResponseBody, respectively. No need
        // to explicitly use Jackson or JAXB here for processing the XML marshallings/unmarshallings.
        // Mapping from PersonDTO to Person (which are both Java Object types), on the other hand, is done through the custom implemented PersonMapper.class
        // Person objectDTOToObject(PersonDTO) method, while no mapping back, from Person to PersonDTO, is used (Person is the object serialized back at
        // the response, not PersonDTO). It could be used PersonDTO at the response, though, by simply mapping the resulting Person to PersonDTO, using
        // the PersonMapper.class PersonDTO objectToObjectDTO(Person) method. At this @Controller class it's optional, as both have the same properties,
        // but it would be necessary in case the PersonDTO had different properties regarding Person (which DTOs generally do regarding their respective
        // Entity classes).
        // These Mappers are configured as beans, at config.Config class or at mapper folder - and injected here, and the bindings are done at the Entity
        // classes, that is, at entity.Person and dto.PersonDTO classes. Any ambiguities on injections are solved through the four possible strategies
        // described at config.Config class.
        // throws (internally) a PersonSaveOrUpdateByIdFailedException RuntimeException, if the resource didn't previously exist, but was not inserted,
        // or if the resource did previously exist, but was not updated.
        // See other related Exceptions at GlobalExceptionHandler.java class.
    }

    @GetMapping("/byFirstNameOrderByUserNameAsc/{first-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameOrderByUserNameAsc(@PathVariable("first-name") String firstName){
        return ResponseEntity.ok(personService.findPersonByFirstNameOrderByUserNameAsc(firstName));
        // this endpoint calls the service/repository custom method that takes the firstName and returns, at the response body, a List of Person
        // instances with that same firstName, ordered by the userName. The List<Person> can be empty, or have 1 or more elements. Either way,
        // this endpoint always returns a 200 status code.
    }

    @GetMapping("/byLastNameOrderByUserNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByUserNameAsc(@PathVariable("last-name") String lastName){
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByUserNameAsc(lastName));
        // this endpoint calls the service/repository custom method that takes the lastName and returns, at the response body, a List of Person
        // instances with that same lastName, ordered by the userName. The List<Person> can be empty, or have 1 or more elements. Either way,
        // this endpoint always returns a 200 status code.
    }

    @GetMapping("/byLastNameOrderByFirstNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByFirstNameAsc(@PathVariable("last-name") String lastName){
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByFirstNameAsc(lastName));
        // this endpoint calls the service/repository custom method that takes the lastName and returns, at the response body, a List of Person
        // instances with that same lastName, ordered by the firstName. The List<Person> can be empty, or have 1 or more elements. Either way,
        // this endpoint always returns a 200 status code.
    }

    @GetMapping("/byFirstNameAndLastNameOrderByUserNameAsc/{first-name}/{last-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameAndLastNameOrderByUserNameAsc(@PathVariable("first-name") String firstName,
                                                                                    @PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(personService.findPersonByFirstNameAndLastNameOrderByUserNameAsc(firstName, lastName));
        // this endpoint calls the service/repository custom method that takes the firstName and the lastName and returns, at the response body,
        // a List of Person instances with both same firstName and lastName, ordered by the userName. The List<Person> can be empty, or have 1 or
        // more elements. Either way, this endpoint always returns a 200 status code.
    }
}

// As the @Service layer classes, the @Controller classes also should be oriented towards the business of the application, with some endpoints'
// methods eventually involving more than one entity. Controllers should NOT have access or use @Repository classes directly, only via @Service
// classes, in order to keep the separation between layers and guarantee easier reuse of the layers, with the least possible coupling between them...
// this also being critical for better maintenance quality.
// In this case, as with the @Service classes, the same name for methods have been used only for didactic purposes.
// For the DTOs, also they should reflect the data at the requests, not the data from the entities, or repositories. In this case, it has just
// replicated the entities, but in a more realistic application it would not.
