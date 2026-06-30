package com.mitocode.microservices.userservice.repository;

import com.mitocode.microservices.commonmodels.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(path = "user")
public interface UserRepository extends CrudRepository<UserEntity, String> {

    @RestResource(path = "email")
    List<UserEntity> getAllByEmail(String email);

    @RestResource(path = "role")
    List<UserEntity> getAllByRoles(String role);

    @RestResource(exported = false)
    void deleteById(String id);
}
