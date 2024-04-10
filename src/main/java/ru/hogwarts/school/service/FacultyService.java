package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long nextId = 1;

    public Faculty add(Faculty faculty) {
        faculty.setId(nextId++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty get(long id) {
        return faculties.get(id);
    }

    public boolean delete(long id) {
        return faculties.remove(id) != null;
    }
    public Faculty update(Faculty faculty){
        if(faculties.containsKey(faculty.getId())){
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }
    public Collection<Faculty> getByColor(String color){
        return faculties.values().stream().filter(s->s.getColor().equals(color)).toList();
    }
}
