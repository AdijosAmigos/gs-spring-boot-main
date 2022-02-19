package com.example.springboot.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    ResponseEntity<Object> add(@RequestBody String name) {
        if(name.length() > 20)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        namesRepository.add(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/names/{id}")
    ResponseEntity<String> findById(@PathVariable String id) {
        Optional<String> name = namesRepository.findById(Integer.parseInt(id));
        return name.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>("This id doesnt exist!", HttpStatus.BAD_REQUEST));
    }

    /*
    ResponseEntity<String> findById(@PathVariable String id) {
        String name;
        try {
            name = namesRepository.getById(Integer.parseInt(id));
        } catch(NotFoundException e){
            return new ResponseEntity<>("This is dosnt exist!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
     */

    @GetMapping("/names/find")
    List<String> findByFirstLetter(@RequestParam("firstLetter") String firstLetter) {
        return namesRepository.findAll().stream()
                .filter(name -> name.startsWith(firstLetter))
                .collect(Collectors.toList());
    }

}