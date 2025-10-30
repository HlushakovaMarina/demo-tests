package by.ilyushenko.demo.univerity.service;

import by.ilyushenko.demo.university.data.UniversityFactory;
import by.ilyushenko.demo.university.model.Lesson;
import by.ilyushenko.demo.university.model.LessonType;
import by.ilyushenko.demo.university.model.Student;
import by.ilyushenko.demo.university.model.University;
import by.ilyushenko.demo.university.service.UniversityTasksService;
import by.ilyushenko.demo.university.service.UniversityTasksServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityTasksServiceTest {

    private UniversityTasksService service;
    private University uni;

    @BeforeEach
    void setUp() {
        uni = UniversityFactory.createSampleUniversity();
        service = new UniversityTasksServiceImpl();
    }

    @Test
    void findStudentByNameShouldReturnExactMatches() {
        List<Student> students = service.findStudentsByName(uni, "Алексей");
        assertEquals(1, students.size());
        assertEquals("Алексей", students.get(0).getName());
    }

    @Test
    void findLessonsBySubject() {
        List<Lesson> lessons = service.findLessonsBySubject(uni, "Алгоритмы");
        assertEquals(1, lessons.size());
        assertEquals("Алгоритмы", lessons.get(0).getSubject());
    }

    @Test
    void findStudentsWithGrades() {
        List<Student> grades = service.findStudentsWithGrades(uni);
        assertEquals(5, grades.size());
    }

    @Test
    void findStudentsWithoutGrades() {
        List<Student> withoutGrades = service.findStudentsWithoutGrades(uni);
        assertTrue(withoutGrades.isEmpty());
    }

    @Test
    void groupedAssertions() {
        List<Student> studentsByName = service.findStudentsByName(uni, "Алексей");
        Student student = studentsByName.isEmpty() ? null : studentsByName.get(0);
        assertAll("student-alexey",
                () -> assertNotNull(student),
                () -> assertEquals("Алексей", student.getName()),
                () -> assertTrue(student.getYear() >= 1));
    }

    @Test
    void timeAndTypeAssertion() {
        assertTimeout(Duration.ofMillis(100), () -> service.countTotalStudents(uni));
        Object type = service.findLessonsByType(uni, LessonType.LAB);
        assertInstanceOf(List.class, type);
    }

    @Test
        // метод работает без исключений
    void assertDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> service.findLessonsBySubject(uni, "Алгоритмы"));
    }

    static Stream<String> subjectsProvider() {
        return Stream.of("Программирование", "Алгоритмы", "Математика");
    }

    @ParameterizedTest
    @MethodSource("subjectsProvider")
    void methodSource_subjects(String subject) {
        assertNotNull(service.findLessonsBySubject(uni, subject));
    }

    @Test
    void findMostPopularLessons() {
        List<Lesson> mostPopularLessons = service.findMostPopularLessons(uni, 2);
        assertEquals(2, mostPopularLessons.size());
        assertTrue(mostPopularLessons.stream().allMatch(l -> l.getAttendees().size() == 2));
    }
    @Test
    void getStudentRanking(){
        List<Map.Entry<Student, Double>> ranking = service.getStudentRanking(uni);
        assertEquals(5, ranking.size());
        assertEquals("Иван", ranking.get(0).getKey().getName());
        assertEquals(5, ranking.get(0).getValue());
        assertEquals("Петр", ranking.get(ranking.size()-1).getKey().getName());
        assertEquals(1,ranking.get(ranking.size()-1).getValue());

    }
}
