package com.mitocode.microservices.authentication_server_oauth2.service.repository;

import com.mitocode.microservices.authentication_server_oauth2.model.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    public Optional<UserEntity> findByUsername(String username) {

      //  System.out.println(new);
        return findAll()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }


    private List<UserEntity> findAll() {

        UserEntity user1 = new UserEntity();
        user1.setId("1");
        user1.setName("Jonathan");
        user1.setLastname("Choy");
        user1.setUsername("ichoy");
        user1.setEmail("springcloud@mitocodenetwork.com");
        user1.setPassword(new BCryptPasswordEncoder().encode("mitocode"));  // mandar valor encriptado
        user1.setRoles(new String[]{"ROLE_USER"});


        UserEntity user2 = new UserEntity();
        user2.setId("2");
        user2.setName("Mitocode");
        user2.setLastname("Network");
        user2.setUsername("mitocode");
        user2.setEmail("admin@mitocodenetwork.com");
        user2.setPassword(new BCryptPasswordEncoder().encode("mitocode")); // mandar valor encriptado
        user2.setRoles(new String[]{"ROLE_ADMIN"});

       return List.of(user1, user2);

    }

}