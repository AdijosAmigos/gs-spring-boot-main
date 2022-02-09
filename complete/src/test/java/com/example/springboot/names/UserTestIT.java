package com.example.springboot.names;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("userTest")
class UserTestIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ApplicationContext context;

    @LocalServerPort
    private int port;


    @Test
    void should_get_all_users_when_exist() {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        userRepository.addUser(user);

        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly(user);
    }

    @Test
    void should_throw_exception_when_users_doesnt_exist() throws Exception {

        User user = new User(1L, "adrian", "adrian@emgial.com");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).doesNotContain(user);

    }

    @Test
    void should_get_user_by_id() {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        userRepository.addUser(user);

        var result = restTemplate.getForEntity("http://localhost:" + port + "/user/0", User.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(user);

    }

    @Test
    void should_throw_exception_when_cant_get_user_by_id() throws Exception {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        userRepository.addUser(user);

        var result = restTemplate.getForEntity("http://localhost:" + port + "/user/1", User.class);
        // probujac uderzyc do "user/1" -> out of bound execption "wyjscie poza tablice" czy w 88 poprawnie obsłużyłem bląd?
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is5xxServerError()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isNotEqualTo(user);

    }

    @Test
    void should_add_user() {

        User user = new User(1L, "adrian", "adrian@emgial.com");

        var result = restTemplate.postForEntity("http://localhost:" + port + "/addUser", user, User.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userRepository.findAll()).containsExactly(user);
    }

    @Test
    void should_throw_exception_when_cant_add_user() throws Exception {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        User expectedUser = new User(1L, "stasiek", "adrian@emgial.com");


        var result = restTemplate.postForEntity("http://localhost:" + port + "/addUser", user, User.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userRepository.findAll()).isNotEqualTo(expectedUser);

    }

    @Test
    void should_delete_user() {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        userRepository.addUser(user);

        var result = restTemplate.postForEntity("http://localhost:" + port + "/deleteUser", user, User.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        // czy asercja ponizej moze wygladac inaczej(czy mozna sprawdzic inaczej czy uzytkownik zostal usuniety?)
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void should_throw_exception_when_cant_delete_user() throws Exception {

        User user = new User(1L, "adrian", "adrian@emgial.com");
        User delUser = new User(1L, "stasiek", "adrian@emgial.com");
        userRepository.addUser(user);

        var result = restTemplate.postForEntity("http://localhost:" + port + "/deleteUser", delUser, User.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        //czy ta logika jest poprawna?
        assertThat(userRepository.findAll()).containsExactly(user);

    }


}

