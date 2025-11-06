package by.ilyushenko.demo.univerity.controller;

import by.ilyushenko.demo.university.model.Lesson;
import by.ilyushenko.demo.university.model.LessonType;
import by.ilyushenko.demo.university.model.Student;
import by.ilyushenko.demo.university.model.University;
import by.ilyushenko.demo.university.service.UniversityTasksService;
import by.ilyushenko.demo.university.service.UniversityTasksServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
//@WebMvcTest()
public class UniversityTasksControllerTest {

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
    void countStudentsShouldReturn200AndCount() throws Exception {
        when(service.countTotalStudents(any(University.class))).thenReturn(5);
        mockMvc.perform(get("/api/university/students/count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(5));
        verify(service, times(1)).countTotalStudents(any(University.class));
    }

    @Test
    void findStudentsByNameShouldReturnList() throws Exception {
        List<Student> students = Arrays.asList(
                new Student("S001", "Алексей", 1),
                new Student("S002", "Мария", 2));
        when(service.findStudentsByName(any(University.class), eq("Алексей")))
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

    @Test
    void findLessonsBySubjectShouldReturnLessons() throws Exception {
        List<Lesson> lessons = Arrays.asList(
                new Lesson("L001", "Алгоритмы", LessonType.PRACTICE),
                new Lesson("L002", "Математика", LessonType.LAB));
        when(service.findLessonsBySubject(any(University.class), eq("Алгоритмы")))
                .thenReturn(Collections.singletonList(lessons.get(0)));
        mockMvc.perform(get("/api/university/lessons/by-subject")
                        .param("subject", "Алгоритмы")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].subject").value("Алгоритмы"))
                .andExpect(jsonPath("$[0].lessonId").value("L001"));
        verify(service).findLessonsBySubject(any(University.class), eq("Алгоритмы"));
    }
}
