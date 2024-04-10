package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static long nextId = 1;

    public Student add(Student student) {
        student.setId(nextId++);
        students.put(student.getId(), student);
        return student;
    }

    public Student get(long id) {
        return students.get(id);
    }

    public boolean delete(long id) {
        return students.remove(id) != null;
    }
    public Student update(Student student){
        if(students.containsKey(student.getId())){
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }
    public Collection<Student> getByAge(int age){
        return students.values().stream().filter(s->s.getAge()==age).toList();
    }
}
