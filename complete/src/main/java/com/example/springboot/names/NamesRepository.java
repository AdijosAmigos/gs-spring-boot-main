package com.example.springboot.names;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NamesRepository {
    private final List<String> names;

    public NamesRepository() {
        this.names = new ArrayList<>();
    }

    public void add(String name) {
        names.add(name);
    }

    public List<String> findAll() {
        return names;
    }

    public Optional<String> findById(int id) {
        if (id > names.size()-1) {
            return Optional.empty();
        }
        return Optional.ofNullable(names.get(id));
    }

    public String getById(int id) throws NotFoundException {
        if (id > names.size()) {
            throw new NotFoundException();
        }
        return names.get(id);

    }

}