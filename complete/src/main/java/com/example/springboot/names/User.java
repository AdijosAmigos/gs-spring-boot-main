package com.example.springboot.names;

import java.util.List;
import java.util.Objects;


public class User {
    private long id;
    private String name;
    private String email;
    private List<Course> courses;

    public User() {
    }

    public User(long id, String name, String email, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public User(String name, String email, List<Course> courses) {
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(courses, user.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, courses);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", courses=" + courses +
                '}';
    }
}
