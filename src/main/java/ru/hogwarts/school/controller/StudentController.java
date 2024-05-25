package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
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
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if(student==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byAge/{age}")
    public Collection<Student> findByAge(@PathVariable int age) {
        return studentService.getByAgeStudents(age);
    }

    @GetMapping("/byAge")
    public Collection<Student> findByAgeBetween(@RequestParam(required = false) Integer minAge,
                                                @RequestParam(required = false) Integer maxAge) {
        if (minAge != null && maxAge != null) {
            return studentService.getByAgeBetween(minAge, maxAge);
        }
        return studentService.getAllStudents();
    }

    @GetMapping("student/faculty")
    public Faculty getStudentFaculty(@RequestParam long studentId) {
        return studentService.findStudent(studentId).getFaculty();
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
