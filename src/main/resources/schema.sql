-- in case you're using normal DBMS services, WITHOUT Docker, this schema.sql will be able to be used by the Spring Boot
-- Application, when this app starts, to create the schema of the DB at the already running DBMS service (both for Spring
-- Data JDBC as Spring Data JPA)

-- in case you're using DBMS services through Docker or Docker Compose, you won't be able to use Spring Boot sql init files
-- after the DBMS container has already been created and is already running. In this case, you should use a specific sql init
-- file (e.g. docker-init.sql), mapped through Docker Compose yml file (using volumes)

-- if you're using JPA spring.jpa.hibernate.ddl-auto, you should use no schema.sql init file, as it is JPA
-- who is going to create the initial schema


drop table if exists PERSON;

create table PERSON(
    ID int not null AUTO_INCREMENT,
    FIRST_NAME varchar(100) not null,
    LAST_NAME varchar(100) not null,
    USER_NAME varchar(100) not null,
    PASSWORD varchar(100) not null,
    EMAIL varchar(100) not null,
    PRIMARY KEY (ID)
);
