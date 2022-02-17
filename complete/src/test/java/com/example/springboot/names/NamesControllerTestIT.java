package com.example.springboot.names;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpServerErrorException;

import java.rmi.ServerError;
import java.sql.Time;
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

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly("adrian");
    }

//    dopisac testy na mozliwosc ze te metody nie dzialaja
//    dopisac test (znajduje dwa imiona na litere a)
//    co zwroci findbyid jezeli nie znajdzie zadnego id

    //bad path
    @Test
    void should_throw_exception_when_name_doesnt_exist() throws Exception {

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names", String[].class);

        assertThat(result.getStatusCode().is5xxServerError()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    void should_throw_exception_when_name_is_diff() throws Exception {

        String expected = "adrian";
        String body = "adriann";

        var result = restTemplate.postForEntity("http://localhost:" + port + "/names", body, String[].class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(namesRepository.findAll()).isNotEqualTo(expected);
    }

    @Test
    void should_throw_exception_when_doesnt_find_by_id() throws Exception {

        namesRepository.add("adrian");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/1", String.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.hasBody()).isTrue();
        //asercja poprawiona ?
        assertThat(result.getBody()).isEqualTo("This is dosnt exist!");

    }

    @Test
    void should_throw_exception_when_doesnt_find_name_by_first_letter() throws Exception {

        namesRepository.add("adrian");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/find?firstLetter=s", String[].class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is5xxServerError()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isNotEqualTo("adrian");

    }

    @Test
    void should_find_more_than_one_name_by_first_letter() {

        namesRepository.add("adrian");
        namesRepository.add("adam");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/find?firstLetter=a", String[].class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).contains("adrian", "adam");
    }




}