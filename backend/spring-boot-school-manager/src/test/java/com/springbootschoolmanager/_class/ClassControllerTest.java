package com.springbootschoolmanager._class;

import com.springbootschoolmanager.controller.ClassController;
import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.service.ClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClassController.class)
class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassService classService;

    @Test
    void getAllClasses_ShouldReturnTheListOfAllClasses() throws Exception {
        List<_Class> classList = Collections.emptyList();

        when(classService
                .getAllClasses())
                .thenReturn(classList);

        mockMvc.perform(get("/class"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(classList)));
        verify(classService).getAllClasses();
    }

    @Test
    void createClass_ShouldReturnBadRequest_WhenContentIsEmpty() throws Exception {
        mockMvc.perform(post("/class")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void createClass_ShouldReturnBadRequest_WhenContentDoesntMatchPattern() throws Exception {
        mockMvc.perform(post("/class")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{'id' : '3'}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(post("/class")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{'id' : '3ii'}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void createClass_ShouldReturnCreated_WhenContentIsValid() throws Exception {
        _Class testClass = new _Class("3a");

        doNothing().when(classService).createClass(testClass);

        mockMvc.perform(post("/class")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"id\" : \"3a\"\n" +
                            "}"))
                .andExpect(status().isCreated())
                .andReturn();

        verify(classService).createClass(testClass);
    }
}