package com.mitocode.microservices.common_models.authentication_server_oauth2.service;

import com.mitocode.microservices.common_models.authentication_server_oauth2.model.entity.UserEntity;
import com.mitocode.microservices.common_models.authentication_server_oauth2.model.entity.core.UserCore;
import com.mitocode.microservices.common_models.authentication_server_oauth2.model.entity.dto.UserDTO;
import com.mitocode.microservices.common_models.authentication_server_oauth2.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CoreUserService implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDTO findUserByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setLastname(userEntity.getLastname());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setRoles(userEntity.getRoles());

        userDTO.setGrantedAuthorities(
                Arrays.stream(userEntity.getRoles())
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );

        return userDTO;
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return new UserCore(
                findUserByUsername(username)
        );
    }

}