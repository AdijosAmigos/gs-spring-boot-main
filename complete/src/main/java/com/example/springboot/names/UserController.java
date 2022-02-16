package com.example.springboot.names;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable String id){
        return userRepository.getById(Integer.parseInt(id));
    }

    @PostMapping("/addUser")
    void addUser (@RequestBody User user){
        userRepository.addUser(user);
    }

    //czy w taki sposob usuwamy usera? czy nie powinnismy zamiast przesylac caly obiekt przeslac tylko id?
    @PostMapping("/deleteUser")
    void deleteUser(@RequestBody String id){
        userRepository.deleteUser(Integer.parseInt(id));
    }
}
