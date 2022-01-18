package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControlerBykowski {

    @Autowired
    Hello hello;

    @GetMapping("/helloWorld")
    public String helloWorld(){
        return hello.sayHello();
    }

}
