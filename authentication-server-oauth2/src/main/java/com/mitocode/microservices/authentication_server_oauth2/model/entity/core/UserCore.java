package com.mitocode.microservices.authentication_server_oauth2.model.entity.core;

import com.mitocode.microservices.authentication_server_oauth2.model.entity.dto.UserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
public class UserCore implements UserDetails {

    private final UserDTO userDTO;

    public UserCore(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDTO.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}