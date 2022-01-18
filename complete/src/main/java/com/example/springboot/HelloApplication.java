package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HelloApplication {

    @Autowired
    Hello hello;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
//        uruchomienie kontekstu springa
    }

    @GetMapping("/hello")
    public String sayHello (){
        return hello.sayHello();
    }
}
