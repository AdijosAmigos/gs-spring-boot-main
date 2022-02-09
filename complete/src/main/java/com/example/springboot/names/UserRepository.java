package com.example.springboot.names;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepository {
    public List<User> userList;

    public UserRepository(List<User> userList) {
        this.userList = userList;
    }

    public List<User> findAll() {
        return userList;
    }

    public User getById(int id){
        return userList.get(id);
    }

    public void addUser (User user){
        userList.add(user);
    }

    public void deleteUser (User user){
        userList.remove(user);
    }


}
