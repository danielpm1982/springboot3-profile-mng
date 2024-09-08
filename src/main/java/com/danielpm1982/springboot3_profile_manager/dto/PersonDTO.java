package com.danielpm1982.springboot3_profile_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PersonDTO implements ObjectDTO{
    @NotNull
    @XmlElement
    private Long id;
    @NotBlank
    @XmlElement
    private String firstName;
    @NotBlank
    @XmlElement
    private String lastName;
    @Size(message = "Username must have more than 6 chars and less than 19 chars",min = 6,max = 19)
    @XmlElement
    private String userName;
    @Size(message = "Password must have exactly 8 chars",min = 8,max = 8)
    @XmlElement
    private String password;
    @Email(message = "Email must be a well-formed email address")
    @XmlElement
    private String email;
}

// we can't set this DTO as a Record type, otherwise ModelMapper won't work... and therefore mapper folder classes also won't.
// we would have to implement manual mapper classes for DTOs.
// Thankfully we have Lombok ! We don't need Record types.
