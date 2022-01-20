package com.example.springboot;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sun.jdi.event.ExceptionEvent;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_return_correct_hello_string() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").param("name", "adrian"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello adrian"));

    }

    @Test
    public void should_add_name() throws Exception {

        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders
                .post("/hello")
                .content(new JSONObject().put("name", "adrian").toString());

        mvc.perform(postRequest).andExpect(status().isOk());
    }

    @Test
    public void should_getEmployeesByID() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders
                .get("/hello/{id}", 0);
    //        tutaj mam problem
        mvc.perform(getRequest).andExpect(status().isOk());
    }


    //    działa?
    @Test
    public void should_return_names() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders
                .get("/names1");

        mvc.perform(getRequest).andDo(print()).andExpect(status().isOk());

    }


}
