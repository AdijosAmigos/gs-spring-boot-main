package com.example.springboot.names;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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
/*
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

 */

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


    //bad path
    @Test
    void should_return_empty_list_when_no_names() throws Exception {

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names", String[].class);

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        String[] names = result.getBody();
        assertThat(names).isEmpty();
    }

    @Test
    void should_return_badRequest_when_name_longer_then_20_chaarcters() throws Exception {

        String name = "asasdasdasdasdasdasdasdasdasdasd";

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/names", name, String.class);

        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.getBody()).isEqualTo("Name too long!");

    }

    @Test
    void should_throw_exception_when_doesnt_find_by_id() throws Exception {

        namesRepository.add("adrian");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/1", String.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo("This id doesnt exist!");

    }

    //poprawiony nie działa zwraca 200 zamiast 400 bo w controllerze nie mam poprawnie
    // zakodzonego streama z ktorego wyciaga imiona ktore zaczynaja sie na podany firstLetter
    @Test
    void should_throw_exception_when_doesnt_find_name_by_first_letter() throws Exception {

        namesRepository.add("adrian");

        var result = restTemplate.getForEntity("http://localhost:" + port + "/names/find", String.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.hasBody()).isTrue();

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