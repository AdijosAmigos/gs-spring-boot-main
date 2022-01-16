package com.example.springboot;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    //    List<String> names = List.of("Adrian", "Ola", "Maciek");
    List<String> names = new ArrayList<>();

    @GetMapping("/hello")
    public String helloWithName(@RequestParam("name") String name) {
        return "Hello " + name;
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
    public String getNames(){
        return names.toString();
    }


}
