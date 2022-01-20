package com.example.springboot.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NamesController {

    private final NamesRepository namesRepository;

    @Autowired
    public NamesController(NamesRepository namesRepository) {
        this.namesRepository = namesRepository;
    }

    @GetMapping("/names")
    List<String> all() {
        return namesRepository.findAll();
    }

    @PostMapping("/names")
    void add(@RequestBody String name) {
        namesRepository.add(name);
    }

    @GetMapping("/names/{id}")
    String findById(@PathVariable String id) {
        return namesRepository.getById(Integer.parseInt(id));
    }

    @GetMapping("/names/find")
    List<String> findByFirstLetter(@RequestParam("firstLetter") String firstLetter) {
        return namesRepository.names.stream()
                .filter(name -> name.startsWith(firstLetter))
                .collect(Collectors.toList());
    }
}