package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate template;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(null, "test_student", 12);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo(12);
        assertThat(result.getName()).isEqualTo("test_student");

        ResponseEntity<Student> resultAfterDelete = template.exchange("/student?id=-1",
                HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void testDelete() {
        Student student = new Student(null, "test_student", 12);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo(12);
        assertThat(result.getName()).isEqualTo("test_student");

        template.delete("/student?id=" + addedStudent.getId());

        ResponseEntity<Student> resultAfterDelete = template.exchange("/student?id=" + addedStudent.getId(),
                HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void testUpdate() {
        Student student = new Student(null, "test_student", 12);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        addedStudent.setName("changed_name");
        addedStudent.setAge(14);
        template.put("/student?id=" + addedStudent.getId(), addedStudent);

        var result = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo(14);
        assertThat(result.getName()).isEqualTo("changed_name");
    }

    @Test
    void testFilter() {
        var s1 = template.postForEntity("/student", new Student(null, "test_name1", 15), Student.class).getBody();
        var s2 = template.postForEntity("/student", new Student(null, "test_name2", 12), Student.class).getBody();
        var s3 = template.postForEntity("/student", new Student(null, "test_name3", 11), Student.class).getBody();
        var s4 = template.postForEntity("/student", new Student(null, "test_name4", 14), Student.class).getBody();

        var students = template.getForObject("/student/byAgeBetween?minAge=10&maxAge=12", Student[].class);
        assertThat(students.length).isEqualTo(2);
        assertThat(students).containsExactlyInAnyOrder(s2, s3);
    }
}
