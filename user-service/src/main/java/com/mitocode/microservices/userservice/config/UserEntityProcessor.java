package com.mitocode.microservices.userservice.config;


import com.mitocode.microservices.commonmodels.model.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;


@Configuration
public class UserEntityProcessor implements RepresentationModelProcessor<EntityModel<UserEntity>> {

    @Override
    public EntityModel<UserEntity> process(EntityModel<UserEntity> model) {
        return EntityModel.of(model.getContent());
    }
}

