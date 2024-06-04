package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @GetMapping
    public Student get(@RequestParam long id) {
        return studentService.get(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return studentService.delete(id);
    }

    @GetMapping("/byAge")
    public Collection<Student> getByAge(@RequestParam(required = false) Integer age) {
        return studentService.getByAge(age);
    }

    @GetMapping("/byAgeBetween")
    public Collection<Student> getByAgeBetween(@RequestParam(required = false) Integer minAge,
                                                @RequestParam(required = false) Integer maxAge) {
        if (minAge != null && maxAge != null) {
            return studentService.getByAgeBetween(minAge, maxAge);
        }
        return studentService.getAllStudents();
    }

    @GetMapping("/faculty")
    public Faculty getStudentFaculty(@RequestParam long studentId) {
        return studentService.get(studentId).getFaculty();
    }

    @GetMapping("/count")
    public int getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/avg-age")
    public double getAvgAge() {//
        return studentService.getAvgAge();
    }

    @GetMapping("/last")//
    public Collection<Student> getLastStudents() {
        return studentService.getLastFive();
    }

    @GetMapping("/nameStartsA")//
    public Collection<String> getStudentNameStartsA() {
        return studentService.getNameStartsWithA();
    }

    @GetMapping("/avg-age-stream")//
    public double getAverageAgeStream() {
        return studentService.getAverageAge();
    }

    @GetMapping("/print-parallel")//
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("/print-synchronized")//
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}
