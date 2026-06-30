package com.mitocode.microservices.common_models.authentication_client_oauth2.expose.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/authenticate")
    public String login(@AuthenticationPrincipal OAuth2User user) {
        String result = "error";

        if (user != null) {
            result = "Logueo exitoso";
            System.out.println(user);


        }

        return result;
    }
}