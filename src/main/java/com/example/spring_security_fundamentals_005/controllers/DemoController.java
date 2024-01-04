package com.example.spring_security_fundamentals_005.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/demo")
    public String demoEndpoint(){
        return "Mastering Authorization";
    }

    @GetMapping("/hello")
    public String demoEndpointTwo(){
        return "Mvc Matchers Test";
    }
}
