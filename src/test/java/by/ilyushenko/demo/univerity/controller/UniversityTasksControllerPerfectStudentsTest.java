package by.ilyushenko.demo.univerity.controller;

import by.ilyushenko.demo.university.model.GradeRecord;
import by.ilyushenko.demo.university.model.Student;
import by.ilyushenko.demo.university.model.University;
import by.ilyushenko.demo.university.service.UniversityTasksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static by.ilyushenko.demo.university.model.Grade.A;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UniversityTasksControllerPerfectStudentsTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private UniversityTasksService service;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void getPerfectStudentsShouldReturn200AndList() throws Exception {
        List<GradeRecord> gradeRecords = Arrays.asList(
                new Student("S001", "Иван", A),
                new Student("S002", "Мария", 2));
        when(service.getPerfectStudents(any(University.class), eq("Алексей")))
                .thenReturn(Collections.singletonList(students.get(0)));
        mockMvc.perform(get("/api/university/students/by-name")
                        .param("name", "Алексей")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Алексей"))
                .andExpect(jsonPath("$[0].studentId").value("S001"));
        verify(service).findStudentsByName(any(University.class), eq("Алексей"));

    }
}
