package com.example.springboot.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class NamesControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NamesRepository namesRepository;

}