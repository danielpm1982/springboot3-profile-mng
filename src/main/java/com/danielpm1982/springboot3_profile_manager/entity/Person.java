package com.danielpm1982.springboot3_profile_manager.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSON")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @Id
    @NotNull
    @Column("ID")
    @XmlElement
    private Long id;
    @NotBlank
    @Column("FIRST_NAME")
    @XmlElement
    private String firstName;
    @NotBlank
    @Column("LAST_NAME")
    @XmlElement
    private String lastName;
    @Size(message = "Username must have more than 6 chars and less than 19 chars",min = 6,max = 19)
    @Column("USER_NAME")
    @XmlElement
    private String userName;
    @Size(message = "Password must have exactly 8 chars",min = 8,max = 8)
    @Column("PASSWORD")
    @XmlElement
    private String password;
    @Email(message = "Email must be a well-formed email address")
    @Column("EMAIL")
    @XmlElement
    private String email;
}

/*
Even when not using JPA, these Entity stereotype Annotations are used at Spring Data JDBC interface
implementations. You must create your DB schema manually, though. With JPA you can auto-generate it.

Jakarta XML binding (JAXB) Annotations above (starting with @Xml) are for allowing the marshalling (serialization) of Person
instances to XML and return that, through the @ResponseBody, at the @Controller, to the client, when the client sends a request
whose header contains accepts = "application/xml"; if the request contains accepts = "application/json", then conversion of Person
instances will be done to JSON format instead and returned that, through the @ResponseBody, at the @Controller, to the client.
In both ways, all manipulation of JSON and XML APIs is done behind the scenes by the @ResponseBody (included at @RestController).
At the Entity level, as with Person.java, no Annotation is needed for JSON conversions (although some are available), but the @Xml
binding annotations above are mandatory for XML conversions. POM.xml dependencies must be imported, as
com.fasterxml.jackson.dataformat.jackson-dataformat-xml (Jackson - Java/JSON/XML conversions) and jakarta.xml.bind.jakarta.xml.bind-api
(XML binding Entity Annotations). Jackson also has its own binding Annotations, but they're a bit more verbose and increase external
coupling of Annotations at the Entity classes to external projects (as Jackson) - while Jakarta JAXB binding Annotations are closer to
Java API intrinsic development. JAXB binding Annotations are understood by Jackson, although JAXB only supports XML (for JSON, if you
needed any Annotation, you'd have to use Jackson's). So, the idea is to keep using JAXB binding Annotations at Entities' classes while
using Jackson for processing them, where the instances of those classes need to be manipulated (into JSON or XML). Just have in mind
Jakarta JAXB and Jackson are two completely separated projects and dependencies... though integratable. You could also use Jakarta JAXB
to, not only Annotate XML binding configurations at Entities, but to manipulate XML conversions as well, where it is needed. But with
Jackson you can manipulate both JSON as XML... and some do prefer Jackson over JAXB for processing conversions.
 */
