package com.danielpm1982.springboot3_profile_manager.service;

import com.danielpm1982.springboot3_profile_manager.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);

    boolean existsById(Long id);

    Person saveOrUpdate(Person person);

    boolean deleteById(Long id);

    List<Person> findPersonByFirstNameOrderByUserNameAsc(String firstname);

    List<Person> findPersonByLastNameOrderByUserNameAsc(String lastName);

    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName);

    List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName);
}
/*
The Service layer is set between the Presentation layer (Controller) and
the Persistence layer (Repository). While Repositories are oriented towards the Entity
model (Domain), having us to implement a Repository for EACH Entity of the Domain Model,
the Service interfaces (and implementations) should be oriented towards the business logic
of the application (user required features), and not to the domain entities. Therefore,
you do not have to create one Service per Entity, or per Repository, you have to create
one Service per each feature or service turned available from the Controller, in order to
serve the user (either a REST Service consumer client app or a frontend app running
at the final user browser). That way, a single Service may need to have injected more than
one Repository Spring bean, for having access to multiple Repositories and Entities at once,
for a single request from the user.

In conclusion, not only the Service layer is responsible for decoupling the Presentation layer
from the Persistence layer, allowing any needed change at the Persistence layer without impacting
the implementation of the Presentation, and vice versa, but it also simplifies the (indirect) use
of the Repositories by the Controllers, both reducing the number of classes and methods, as turning
available a semantics oriented towards the logical business of the application, instead of to the
data itself. This decoupling also is essential for the reuse of the same Persistence layer for
different types of applications, only by changing the Presentation, without having to reimplement the
same Persistence layer again, for the same data. Also allows a centralized architecture for the Data,
and the development of different types of apps connected to that same centralized architecture.

It's ALWAYS a good practice to create the Service layer. The Presentation layer (Controllers)
should not even know the Persistence layer exists (or which kind of Persistence is being used),
and the only link of the Controllers to the Data (either a DB or other type of Data Store) should
be through the Service interfaces.
*/
