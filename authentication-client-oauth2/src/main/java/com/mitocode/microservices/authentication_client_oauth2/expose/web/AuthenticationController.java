package com.mitocode.microservices.authentication_client_oauth2.expose.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

 @GetMapping("/authenticate")
    public String login(){
        return "Hello World";
    }
}
