package com.example.springboot.names;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NamesRepository {
    final List<String> names;

    public NamesRepository() {
        this.names = new ArrayList<>();
    }

    public void add(String name ) {
        names.add(name);
    }

    public List<String> findAll() {
        return names;
    }

    public String getById(int id) {
        return names.get(id);
    }

}