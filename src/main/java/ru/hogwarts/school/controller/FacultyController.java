package ru.hogwarts.school.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }

    @GetMapping
    public Faculty get(@RequestParam long id) {
        return service.get(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return service.update(faculty);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return service.delete(id);
    }

    @GetMapping("/byColorAndName")
    public Collection<Faculty> getByColorOrName(@RequestParam(required = false) String color,
                                                @RequestParam(required = false) String name) {
        if (color == null && name == null) {
            return service.getAll();
        }
        return service.getByColorOrName(color, name);
    }

    @GetMapping("faculty/students")
    public List<Student> getStudentFaculty(@RequestParam long facultyId) {
        return service.get(facultyId).getStudents();
    }
}
