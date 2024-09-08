-- this schema creation and data initialization file is to be used by Docker Compose when the DBMS container is being
-- created and started, as it could not be used later, by the Spring Boot App, after the DBMS container had already
-- been created. The mapping from this file directory and the corresponding directory at MySQL container is set at the
-- docker-compose.yml file through volumes mapping (volumes configuration maps whole directories - and all files inside,
-- not single files, unless additional and more complex configuration is used)
-- if you're not using Docker for initializing your DBMS, you can let Spring Boot App create the schema and populate
-- the DB for you through other sql init files, as schema.sql and data.sql, and only when the web app starts

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

INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonAFirstName', 'PersonALastName', 'ZZZ-PersonAUserName', 'ABC123456@', 'person-a@person.com');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonBFirstName', 'PersonBLastName', 'HHH-PersonBUserName', 'DEF123456@', 'person-b@person.com');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) VALUES (default, 'PersonCFirstName', 'PersonCLastName', 'AAA-PersonCUserName', 'GHI123456@', 'person-c@person.com');
