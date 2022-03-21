package com.springbootschoolmanager.student;

import com.springbootschoolmanager.controller.StudentController;
import com.springbootschoolmanager.entity.Student;
import com.springbootschoolmanager.exception._class.ClassNotFoundException;
import com.springbootschoolmanager.exception.student.StudentNotFoundException;
import com.springbootschoolmanager.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void getAllStudents_ShouldReturnAllStudents_WhenThereIsNoParameterSet() throws Exception {

        List<Student> studentList = Collections.emptyList();

        when(studentService
                .getAllStudents())
                .thenReturn(studentList);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(studentList)));
        verify(studentService).getAllStudents();
    }

    @Test
    void getAllStudents_ShouldReturnAllStudentsByClassId_WhenThereIsParameterSet() throws Exception {

        List<Student> studentList = Collections.emptyList();

        when(studentService
                .getAllStudentsByClassId("3f"))
                .thenReturn(studentList);
        when(studentService
                .getAllStudentsByClassId("3a"))
                .thenReturn(studentList);

        mockMvc.perform(get("/student")
                .param("class_id", "3f"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(studentList)));

        mockMvc.perform(get("/student")
                .param("class_id", "3a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(studentList)));

        verify(studentService).getAllStudentsByClassId("3f");
        verify(studentService).getAllStudentsByClassId("3a");
    }

    @Test
    void createStudent_ShouldReturnCreated_WhenContentIsValid() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"77777777777\",\n" +
                                "    \"first_name\" : \"Logan\",\n" +
                                "    \"last_name\" : \"Novak\",\n" +
                                "    \"class_id\" : \"3a\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andReturn();
        verify(studentService).createStudent(any());
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenContentIsEmpty() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenContentIsNotValid() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"77777777777\",\n" +
                                "    \"first_name\" : \"Logan\",\n" +
                                "    \"last_name\" : \"Novak\",\n" +
                                "    \"class_id\" : \"3aa\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"777777\",\n" +
                                "    \"first_name\" : \"Logan\",\n" +
                                "    \"last_name\" : \"Novak\",\n" +
                                "    \"class_id\" : \"3aa\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"77777777777\",\n" +
                                "    \"first_name\" : \"\",\n" +
                                "    \"last_name\" : \"Novak\",\n" +
                                "    \"class_id\" : \"3a\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteStudent_ShouldReturnOk_WhenPeselParameterIsValid() throws Exception {
        String pesel = "77777777777";

        when(studentService.deleteStudent(pesel))
                .thenReturn( String.format("Student with pesel %s deleted successfully", pesel));

        mockMvc.perform(delete("/student")
                .param("pesel", pesel))
                .andExpect(status().isOk())
                .andReturn();

        verify(studentService).deleteStudent(pesel);
    }

    @Test
    void deleteStudent_ShouldReturnNotFound_WhenSuchPeselDoesntExists() throws Exception {
        when(studentService.deleteStudent("77777")).thenThrow(StudentNotFoundException.class);
        when(studentService.deleteStudent("")).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(delete("/student")
                        .param("pesel", "77777"))
                .andExpect(status().isNotFound())
                .andReturn();

        mockMvc.perform(delete("/student")
                        .param("pesel", ""))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void assignStudentToClass_ShouldReturnOk_WhenContentIsValid() throws Exception {
        mockMvc.perform(post("/student/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"pesel\" : \"55555555555\",\n" +
                        "    \"class_id\" : \"3a\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
        verify(studentService).assignStudentToClass("3a", "55555555555");
    }

    @Test
    void assignStudentToClass_ShouldReturnBadRequest_WhenReceivedDtoIsNotValid() throws Exception {
        mockMvc.perform(post("/student/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"1234\",\n" +
                                "    \"class_id\" : \"3a\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(post("/student/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"55555555555\",\n" +
                                "    \"class_id\" : \"3aa\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(studentService, never()).assignStudentToClass(any(), any());
    }

    @Test
    void assignStudentToClass_ShouldReturnNotFound_WhenSuchPeselDoesntExists() throws Exception {
        when(studentService.assignStudentToClass(any(), any())).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(post("/student/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"99999999999\",\n" +
                                "    \"class_id\" : \"3a\"\n" +
                                "}"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void assignStudentToClass_ShouldReturnNotFound_WhenSuchClassDoesntExists() throws Exception {
        when(studentService.assignStudentToClass(any(), any())).thenThrow(ClassNotFoundException.class);

        mockMvc.perform(post("/student/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"pesel\" : \"99999999999\",\n" +
                                "    \"class_id\" : \"3z\"\n" +
                                "}"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}