package com.mitocode.microservices.common_models.authentication_server_oauth2.model.entity.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserDTO {

    private String id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String[] roles;

    private Collection<? extends GrantedAuthority> grantedAuthorities;
}