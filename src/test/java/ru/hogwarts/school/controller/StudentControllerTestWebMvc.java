package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class StudentControllerTestWebMvc {
    @Autowired
    MockMvc mvc;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentService studentService;
    @MockBean
    AvatarService avatarService;
    @MockBean
    FacultyService facultyService;

    @InjectMocks
    StudentController controller;

    @Test
    void testGet() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 11)));
        mvc.perform(MockMvcRequestBuilders.get("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_student_mvc"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    void testUpdate() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 11)));
        Student student = new Student(1L, "updated_name", 15);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders.get("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated_name"))
                .andExpect(jsonPath("$.age").value(15));
    }

    @Test
    void testDelete() throws Exception {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 11)));
        mvc.perform(MockMvcRequestBuilders.delete("/student?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(studentRepository.findById(333L)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.delete("/student?id=333"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testAdd() throws Exception {

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "test_student_mvc");

        Student student = new Student(1L, "test_student_mvc", 11);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test_student_mvc"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    void testByAgeBetween() throws Exception {
        when(studentRepository.findAllByAgeBetween(anyInt(), anyInt())).
                thenReturn(List.of(
                        new Student(1L, "name1", 11),
                        new Student(2L, "name2", 15)
                ));

        mvc.perform(MockMvcRequestBuilders.get("/student/byAgeBetween?minAge=14&maxAge=16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("name2"))
                .andExpect(jsonPath("$[0].age").value(15));
    }

    @Test
    void testGetFaculty() throws Exception {
        Student s = new Student(1L, "s1", 11);
        s.setFaculty(new Faculty(1L, "f1", "c1"));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        mvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("f1"))
                .andExpect(jsonPath("$[0].color").value("c1"));

        mvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId="))
                .andExpect(status().is(400));
    }
}
