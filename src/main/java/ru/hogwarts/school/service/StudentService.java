package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public boolean deleteStudent(long id) {
        return studentRepository.findById(id).map(entity -> {
            studentRepository.delete(entity);
            return true;
        }).orElse(false);
    }

    public Student editStudent(Student student) {
        return studentRepository.findById(student.getId())
                .map(entity -> studentRepository.save(student))
                .orElse(null);
    }

    public Collection<Student> getByAgeStudents(int age){
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getByAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Faculty getFacultyByStudent(Long id){
        return findStudent(id).getFaculty();
    }

    public int getStudentCount(){
        return studentRepository.countStudents();
    }

    public double getAvgAge(){
        return studentRepository.avgAge();
    }

    public Collection<Student> getLastFive(){
        return studentRepository.getLastFive();
    }
}
