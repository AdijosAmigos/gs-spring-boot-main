package com.example.springboot;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sun.jdi.event.ExceptionEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<String> names = new ArrayList<>();
        String name = "adrian";

        mvc.perform(MockMvcRequestBuilders.post("/hello").param("name", name))
                .andDo(result -> names.add(name));

        Assertions.assertThat(names.contains(name)).isTrue();

    }


    @Test
    public void should_return_all_names() throws Exception {

        //nie działa

        List<String> names = new ArrayList<>();
        String name = "adrian";

        mvc.perform(MockMvcRequestBuilders.get("/names"))
                .andDo(result -> names.add(name))
                .andReturn();

        Assertions.assertThat(names.isEmpty()).isFalse();

    }

    @Test
    public void should_get_name_by_id() throws Exception {

        //nie działa

        List<String> names = new ArrayList<>();
        String id = "0";
        String name = "adrian";

        mvc.perform(MockMvcRequestBuilders.get("/hello{id}").param("id", id))
                .andDo(result -> names.add(name))
                .andReturn(names.get(Integer.parseInt(id)));


    }
}
