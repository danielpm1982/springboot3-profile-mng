-- in case you're using normal DBMS services, WITHOUT Docker, this data.sql will be able to be used by the Spring Boot
-- Application, when this app starts, to populate the DB at the already running DBMS service (both for Spring Data JDBC
-- as Spring Data JPA)

-- in case you're using DBMS services through Docker or Docker Compose, you won't be able to use Spring Boot sql init files
-- after the DBMS container has already been created and is already running. In this case, you should use a specific sql init
-- file (e.g. docker-init.sql), mapped through Docker Compose yml file (using volumes)

INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonAFirstName', 'PersonALastName', 'ZZZ-PersonAUserName', 'ABC123456@', 'person-a@person.com');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonBFirstName', 'PersonBLastName', 'HHH-PersonBUserName', 'DEF123456@', 'person-b@person.com');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonCFirstName', 'PersonCLastName', 'AAA-PersonCUserName', 'GHI123456@', 'person-c@person.com');
