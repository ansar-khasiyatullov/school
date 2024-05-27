package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student add(Student student) {
        return repository.save(student);
    }

    public Student get(long id) {
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public boolean delete(long id) {
        return repository.findById(id).map(entity -> {
            repository.delete(entity);
            return true;
        }).orElse(false);
    }

    public Student update(Student student) {
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    public Collection<Student> getByAge(int age){
        return repository.findByAge(age);
    }

    public Collection<Student> getByAgeBetween(int min, int max) {
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        return repository.findAll();
    }

    public int getStudentCount(){
        return repository.countStudents();
    }

    public double getAvgAge(){
        return repository.avgAge();
    }

    public Collection<Student> getLastFive(){
        return repository.getLastFive();
    }
}
