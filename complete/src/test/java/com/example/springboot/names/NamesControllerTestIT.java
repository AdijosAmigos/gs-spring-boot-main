package com.example.springboot.names;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NamesControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NamesRepository namesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ApplicationContext context;

    @Test
    void should_return_all_names_when_exists() {
        //given
        namesRepository.add("adrian");
        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/names", String[].class);
        //then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly("adrian");
    }

    @Test
    void should_add_name() {
        //given
        var body = ("adrian");
        var expected = ("adrian");

        //when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/names", body, String.class);
        //then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(namesRepository.findAll()).contains(expected);

    }

    @Test
    void should_find_by_id() {
        //given
        namesRepository.add("adrian");
        namesRepository.add("czesiek");
        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/1", String.class);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo("czesiek");
    }

    @Test
    void should_return_by_first_letter() {

        namesRepository.add("adrian");
        namesRepository.add("czesiek");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/find?firstLetter=a", String[].class);
        // czy w 79 nie powinno być String[].class? przeciez może znaleść więcej niż jedno imie zaczynajace sie na dana literke
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly("adrian");
    }

//    dopisac testy na mozliwosc ze te metody nie dzialaja
//    dopisac test (znajduje dwa imiona na litere a)
//    co zwroci findbyid jezeli nie znajdzie zadnego id



    @Test
    void should_check_user() {
        //given
        Course course = new Course("math");
        User user = new User(1L, "adrian", "adrian@email.com", new ArrayList<>(List.of(course)));
        userRepository.addUser(user);
        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly(user);

    }

// przeniesc do klasy usertest oraz stworzyc api do niego (dodawanie, usuwanie, szukanie)

}