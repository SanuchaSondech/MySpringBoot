package com.sanucha.fistapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String display(){
        return "Update Hello Spring Boot";
    }

    @GetMapping("/about")
    public String about(){
        return "about me";
    }

    @GetMapping("/api")
    public String api(){
        return "test api";
    }

}
