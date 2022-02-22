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


    @GetMapping("/names")
    ResponseEntity<List<String>> all() {
        List<String> allNames = namesRepository.findAll();
        return new ResponseEntity<>(allNames, HttpStatus.OK);
    }


    @PostMapping("/names")
    ResponseEntity<Object> add(@RequestBody String name) {
        if (name.length() > 20)
            throw new ValidationException("Name too long!");
        namesRepository.add(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/names/{id}")
    ResponseEntity<String> findById(@PathVariable String id) {
        Optional<String> name = namesRepository.findById(Integer.parseInt(id));
        return name.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>("This id doesnt exist!", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/names/find")
    ResponseEntity<List<String>> findByFirstLetter(@RequestParam(name = "firstLetter") String firstLetter) {
        if (firstLetter == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> names = namesRepository.findAll().stream()
                .filter(s -> s.startsWith(firstLetter))
                .collect(Collectors.toList());
        return new ResponseEntity<>(names, HttpStatus.OK);
    }


}