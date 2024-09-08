package com.danielpm1982.springboot3_profile_manager.repository;

import com.danielpm1982.springboot3_profile_manager.entity.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonRepository extends ListCrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long>, PersonCustomRepository {

    // custom query with @Query annotation and named parameters (no support for positional numbered parameters)
    @Query(value = "SELECT * FROM PERSON where FIRST_NAME= :firstName ORDER BY USER_NAME ASC")
    List<Person> findPersonByFirstNameOrderByUserNameAsc(@Param("firstName") String firstname);

    // custom query with @Query annotation, traditional SQL table alias and named parameters
    @Query(value = "SELECT * FROM PERSON p where p.LAST_NAME= :lastName ORDER BY p.USER_NAME ASC")
    List<Person> findPersonByLastNameOrderByUserNameAsc(@Param("lastName") String lastName);

    // auto-generated query inferred from method name (query methods)
    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName);

    /*
    Spring Data JDBC X Spring Data JPA :

    You cannot use JPQL or Criteria, at @Repository implementation classes, when using Spring Data JDBC, only when using Spring Data JPA.
    With Spring Data JDBC, you have to use pure JDBC/SQL or JdbcTemplate (see PersonCustomRepositoryImpl), with additional work in mapping
    back results from the SQL query to Entity bean properties' values (see PersonCustomRepository interface and its Impl class).

    Spring Data JDBC supports only named parameters (not positional parameters) at @Query. String-based queries do not support pagination
    nor accept Sort, PageRequest, and Limit as a query parameter as for these queries the query would be required to be rewritten. Queries
    may contain SpEL expressions where bind variables are allowed. Such a SpEL expression will get replaced with a bind variable and the
    variable gets bound to the result of the SpEL expression.

    Named Queries are supposed to be available at Spring Data JDBC through properties' definitions, at jdbc-named-queries.properties, but I've
    not managed to test that. Annotations as @NamedQuery, @NamedQueries, @NamedNativeQuery and @NamedNativeQueries, from
    org.hibernate.annotations package, are not available at Spring Data JDBC. These are available and pretty functional at Spring Data JPA,
    at which you can set Named Queries through properties' definitions, at jpa-named-queries.properties, or through the just mentioned Annotations.

    There's not much advantage in using Spring Data JDBC over using Spring Data JPA. Other than the above-mentioned, some features, as
    spring.jpa.hibernate.ddl-auto , also are not available (although liquibase is natively integrated), as well as lazy loading, JPA caching
    and other JPA features, especially automated mappings. All these you gotta do manually.

    With Spring Data JDBC You can use @Query with SQL statements; automated-generated queries from query methods' names; implement methods
    entirely manually (using JdbcTemplate, RowMappers and SQL) - see PersonCustomRepository interface and its Impl class; or you can enjoy
    Spring Data interfaces with standard CRUD JDBC implementations. There's some marginally significant gain on performance when compared
    Spring Data JDBC against Spring Data JPA, because of the lesser overhead, but for most projects it wouldn't be a significant criteria in
    choosing Spring Data JDBC over Spring Data JPA... only for extreme performance-critical projects.

    In the case of manually implemented custom Repository methods, another interface (not declared as Repository) should be created and
    implemented (including these custom methods implementations), and then extended by the Repository interface along with the CrudRepository,
    as done here at this Repository interface. Custom methods are implemented at the PersonCustomRepository implementation, and the
    PersonCustomRepository interface extended by this one. All that because you can't inject beans (including JdbcTemplate) at interfaces...
    you need a concrete class for that (PersonCustomRepositoryImpl), as well as the respective interface (PersonCustomRepository) to be extended
    by the Repository interface. Then, only the Repository interface should be injected and used at the Service as the actual Repository bean.
    The other interface is considered a fragment of this Repository bean, and not an independent bean or Repository itself. You can create how
    many fragment interfaces (and respective implementations) you want and later extend all of them from the actual Repository interface...
    inheriting all custom implemented methods, to be used along with the CrudRepository methods, at the Service, through this Repository single
    interface.

    More info on differences between using Spring Data JDBC or Spring Data JPA, see Documentation.
    */
}
