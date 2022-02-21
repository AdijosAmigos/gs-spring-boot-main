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

    //poprawilem
    @GetMapping("/names")
    ResponseEntity<List<String>> all() {
        Optional<List<String>> allnames = namesRepository.findAll();
        return allnames.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/names")
    ResponseEntity<Object> add(@RequestBody String name) {
        if (name.length() > 20)
            throw new NameAlreadyExist("Name already exist!");
        namesRepository.add(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/names/{id}")
    ResponseEntity<String> findById(@PathVariable String id) {
        Optional<String> name = namesRepository.findById(Integer.parseInt(id));
        return name.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>("This id doesnt exist!", HttpStatus.BAD_REQUEST));
    }

    //dołożyłem response entity mam problem zeby ze streama wyciagnac akurat te slowa ktore zaczynaja sie na litere podana w endpoincie
    @GetMapping("/names/find")
    ResponseEntity<List<String>> findByFirstLetter(@RequestParam("firstLetter") String firstLetter) {
        Optional<List<String>> names = namesRepository.findAll()
                .stream().filter(s -> s.stream().filter(s1 -> s1.startsWith(firstLetter)).collect(Collectors.toList()));
        return names.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }



}