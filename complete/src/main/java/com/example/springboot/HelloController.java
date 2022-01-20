package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    List<String> names = new ArrayList<>();

    //    przekazywanie poprzez parametr
    @GetMapping("/hello")
    public String helloWithName(@RequestParam("name") String name) {
        return "hello " + name;
    }

    //    przekazywanie poprzez adres
    @GetMapping("/hello/{id}")
    @ResponseBody
    public String getEmployeesByID(@PathVariable String id) {
        return "hello " + names.get(Integer.parseInt(id));
    }

    //    przekazywanie poprzez ciało
    @PostMapping("/hello")
    public void addName(@RequestBody String name) {
        names.add(name);
    }

    @GetMapping("/names1")
    public String getNames() {
        return names.toString();
    }

    //    zapytanie http
    @GetMapping("/example")
    public String tranfser(HttpServletRequest request) {
        String browserName = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        return "Browser name: " + browserName + System.lineSeparator() +
                "IP Address: " + ipAddress;
    }


}
