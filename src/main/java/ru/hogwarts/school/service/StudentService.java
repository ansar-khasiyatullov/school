package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student add(Student student) {
        LOGGER.info("Student.add was invoked!");
        return repository.save(student);
    }

    public Student get(long id) {
        LOGGER.info("Student.get was invoked!");
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public boolean delete(long id) {
        LOGGER.info("Student.delete was invoked!");
        return repository.findById(id).map(entity -> {
            repository.delete(entity);
            return true;
        }).orElse(false);
    }

    public Student update(Student student) {
        LOGGER.info("Student.update was invoked!");
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    public Collection<Student> getByAge(int age) {
        LOGGER.info("Student.getByAge was invoked!");
        return repository.findByAge(age);
    }

    public Collection<Student> getByAgeBetween(int min, int max) {
        LOGGER.info("Student.getByAgeBetween was invoked!");
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAllStudents() {
        LOGGER.info("Student.getAllStudents was invoked!");
        return repository.findAll();
    }

    public int getStudentCount() {
        LOGGER.info("Student.getStudentCount was invoked!");
        return repository.countStudents();
    }

    public double getAvgAge() {
        LOGGER.info("Student.getAvgAge was invoked!");
        return repository.avgAge();
    }

    public Collection<Student> getLastFive() {
        LOGGER.info("Student.getLastFive was invoked!");
        return repository.getLastFive();
    }

    public Collection<String> getNameStartsWithA() {
        LOGGER.info("Student.getNameStartsWithA was invoked!");
        return repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAge() {
        LOGGER.info("Student.getAverageAge was invoked!");
        return repository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printParallel() {
        var students = repository.findAll();

        LOGGER.info(students.get(0).toString());
        LOGGER.info(students.get(1).toString());

        new Thread(() -> {
            try {
                Thread.sleep(3000); // 3000 ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info(students.get(2).toString());
            LOGGER.info(students.get(3).toString());
        }).start();

        new Thread(() -> {
            LOGGER.info(students.get(4).toString());
            LOGGER.info(students.get(5).toString());
        }).start();
    }

    public void printSynchronized(){
        var students = repository.findAll();

        print(students.get(0));
        print(students.get(1));

        new Thread(() -> {
            try {
                Thread.sleep(3000); // 3000 ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            print(students.get(2));
            print(students.get(3));
        }).start();

        new Thread(() -> {
            print(students.get(4));
            print(students.get(5));
        }).start();
    }

    private synchronized void print(Object o){
        LOGGER.info(o.toString());
    }
}
