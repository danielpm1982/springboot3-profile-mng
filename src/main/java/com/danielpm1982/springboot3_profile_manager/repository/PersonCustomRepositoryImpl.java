package com.danielpm1982.springboot3_profile_manager.repository;

import com.danielpm1982.springboot3_profile_manager.entity.Person;
import com.danielpm1982.springboot3_profile_manager.exception.PersonSaveOrUpdateByIdFailedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {
    private final JdbcTemplate jdbcTemplate;
    public PersonCustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // manually implemented query using JdbcTemplate
    @Override
    public Person saveOrUpdate(Person person) {
        if(findById(person.getId())==null){
            String preparedStatement = "INSERT INTO PERSON(ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) " +
                    "VALUES(?,?,?,?,?,?)";
            jdbcTemplate.update(preparedStatement, person.getId(), person.getFirstName(), person.getLastName(),
                    person.getUserName(), person.getPassword(), person.getEmail());
            if(findById(person.getId())==null){
                throw new PersonSaveOrUpdateByIdFailedException("Person ID = "+person.getId()+" did not previously exist at the Database but was not inserted !");
            }
        } else{
            String preparedStatement = "UPDATE PERSON SET FIRST_NAME=?, LAST_NAME=?, USER_NAME=?, PASSWORD=?, EMAIL=? WHERE ID=?";
            jdbcTemplate.update(preparedStatement, person.getFirstName(), person.getLastName(), person.getUserName(),
                    person.getPassword(), person.getEmail(), person.getId());
            if(!Objects.equals(findById(person.getId()),person)){
                throw new PersonSaveOrUpdateByIdFailedException("Person ID = "+person.getId()+" previously existed at the Database but was not updated !");
            }
        }
        return findById(person.getId());
        // checks if the record exists for that ID at the DB. If not, inserts as a new record, else, updates the existing record.
        // CrudRepository uses a different strategy: if the user passes any ID value, it interprets as an eventual existing record
        // trying to be updated (not inserted as a new one), which is a bad design for when the user wants to set new record IDs
        // manually... as in this application.
        // Therefore, this custom saveOrUpdate() implementation, for this app, shall be used at the @PersonRepository, instead of the default
        // save() from the CrudRepository.
    }

    // definition of RowMapper for Person entity bean: sets how to map back SQL results to Person bean properties (not automatically, as in JPA)
    // this could be declared as a bean at a config class, and injected here, but wouldn't be used anywhere else, so it's better to declare each
    // entity RowMapper inside each respective Repository class... either as an anonymous inner class (for the RowMapper() functional interface),
    // as below, or as a lambda, passed as argument to the JdbcTemplate method, where it is needed (inside queryForObject(), for example).
    RowMapper<Person> personRowMapper = new RowMapper<Person>() {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person tempPerson = new Person();
            tempPerson.setId(rs.getLong(1));
            tempPerson.setFirstName(rs.getString(2));
            tempPerson.setLastName(rs.getString(3));
            tempPerson.setUserName(rs.getString(4));
            tempPerson.setPassword(rs.getString(5));
            tempPerson.setEmail(rs.getString(6));
            return tempPerson;
        }
    };

    // this method is only for being used internally at this class, not at the @PersonRepository, in which case it would be redundant
    private Person findById(Long personId){
        try {
            String preparedStatement = "SELECT * from PERSON where ID=?";
            return jdbcTemplate.queryForObject(preparedStatement, personRowMapper, personId);
        } catch (EmptyResultDataAccessException e){
            return null;
            // jdbcTemplate.queryForObject() will throw EmptyResultDataAccessException if it doesn't find the record at the DB, in this
            // case we just have to return null - as that is exactly what we wanted to now (if the record is or not present at the DB)
        }
    }

    // manually implemented query using JdbcTemplate
    @Override
    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName) {
        String preparedStatement = "SELECT * from PERSON where FIRST_NAME=? and LAST_NAME=? ORDER BY USER_NAME ASC";
        return jdbcTemplate.query(preparedStatement, personRowMapper, firstName, lastName);
    }
}
