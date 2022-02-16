package com.example.springboot.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NamesController {

    private final NamesRepository namesRepository;
    private final UserRepository userRepository;

    @Autowired
    public NamesController(NamesRepository namesRepository, UserRepository userRepository) {
        this.namesRepository = namesRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/helloWorld")
    String hello(@RequestParam String name) {
        return "Hello World " + name;
    }

    @GetMapping("/names")
    List<String> all() {
        try {
            namesRepository.findAll();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("404 not found");
        }
        return namesRepository.findAll();
    }

    @PostMapping("/names")
    void add(@RequestBody String name) {
        namesRepository.add(name);
    }

    @GetMapping("/names/{id}")
    String findById(@PathVariable String id) {
        try {
            namesRepository.getById(Integer.parseInt(id));
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("404 not found");
        }
        return namesRepository.getById(Integer.parseInt(id));
    }

    @GetMapping("/names/find")
    List<String> findByFirstLetter(@RequestParam("firstLetter") String firstLetter) {
        return namesRepository.findAll().stream()
                .filter(name -> name.startsWith(firstLetter))
                .collect(Collectors.toList());
    }

}