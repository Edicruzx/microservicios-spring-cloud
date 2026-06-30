package com.mitocode.microservices.commonmodels.model.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Valid
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserEntity extends GenericEntity<String> {

    @Id
    private String id;

    @Valid
    @Size(min = 8, max = 10, message = "El nombre no se encuentra dentro el standard")
    @NotNull
    private String name;

    @Valid
    @Size(min = 8, max = 10, message = "El apellido no se encuentra dentro el standard")
    @NotNull
    private String lastname;

    @Valid
    @Size(min = 6, max = 10, message = "El username no se encuentra dentro el standard")
    @NotNull
    private String username;

    private String email;
    private String password;
    private String[] roles;
}