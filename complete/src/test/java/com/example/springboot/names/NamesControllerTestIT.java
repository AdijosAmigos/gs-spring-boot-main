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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NamesControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate resttemplate;

    @Autowired
    private NamesRepository namesRepository;

    @Autowired
    ApplicationContext context;

    @Test
    void should_return_all_names_when_exists() {
        //given
        namesRepository.add("adrian");
        //when
        var result = resttemplate.getForEntity("http://localhost:" + port + "/names", String[].class);
        //then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly("adrian");
    }

}