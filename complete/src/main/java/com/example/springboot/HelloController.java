package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    List<String> names = new ArrayList<>();

    @GetMapping("/hello")
    public String helloWithName(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("/hello/{id}")
    @ResponseBody
    public String getEmployeesByID(@PathVariable String id) {
        return "hello " + names.get(Integer.parseInt(id));
    }

    @PostMapping("/hello")
    public void addName(@RequestBody String name) {
        names.add(name);
    }

    @GetMapping("/names")
    public String getNames() {
        return names.toString();
    }


}
