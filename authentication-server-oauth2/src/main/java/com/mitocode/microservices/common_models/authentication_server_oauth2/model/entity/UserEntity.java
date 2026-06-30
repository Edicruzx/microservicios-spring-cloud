package com.mitocode.microservices.common_models.authentication_server_oauth2.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String[] roles;
}