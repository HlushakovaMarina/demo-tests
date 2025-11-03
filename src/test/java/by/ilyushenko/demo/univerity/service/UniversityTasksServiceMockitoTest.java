package by.ilyushenko.demo.univerity.service;

import by.ilyushenko.demo.university.model.Group;
import by.ilyushenko.demo.university.model.Student;
import by.ilyushenko.demo.university.model.University;
import by.ilyushenko.demo.university.service.UniversityTasksServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversityTasksServiceMockitoTest {

    @Mock
    private University university;

    @InjectMocks
    private UniversityTasksServiceImpl service;


    private List<Group> groups;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        students = new ArrayList<>();
        Student s6 = new Student("S006", "Марина", 2);
        Student s7 = new Student("S007", "Коля", 1);
        students.add(s6);
        students.add(s7);


        groups = new ArrayList<>();
        Group g3 = new Group("G003", "CS-103");
        Group g4 = new Group("G004", "Math-204");

        g3.addStudent(s6);
        g4.addStudent(s7);
        groups.add(g3);
        groups.add(g4);
    }

    @Test
    void countTotalStudents() {
        when(university.getGroups()).thenReturn(groups);
        int count = service.countTotalStudents(university);
        assertEquals(2, count);
        verify(university, times(1)).getGroups();
    }

    @Test
    void findStudentsByName() {
        when(university.getGroups()).thenReturn(groups);
        List<Student> result = service.findStudentsByName(university, "Коля");
        assertEquals(1, result.size());
        assertEquals("Коля", result.get(0).getName());
        verify(university, atLeastOnce()).getGroups();
    }
    @Test
    void findStudentsByYear(){
        when(university.getGroups()).thenReturn(groups);
        List<Student> year = service.findStudentsByYear(university, 1);
        assertEquals(1,year.size());
        assertEquals(1, year.get(0).getYear());
        verify(university, atLeastOnce()).getGroups();
    }
}


