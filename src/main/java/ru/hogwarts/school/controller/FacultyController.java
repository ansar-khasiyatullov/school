package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService service) {
        this.facultyService = service;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @GetMapping
    public Faculty get(@RequestParam long id) {
        return facultyService.get(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/byColorOrName")
    public Collection<Faculty> getByColorOrName(@RequestParam(required = false) String color,
                                                @RequestParam(required = false) String name) {
        if (color == null && name == null) {
            return facultyService.getAll();
        }
        return facultyService.getByColorOrName(color, name);
    }

    @GetMapping("/students")
    public List<Student> getFacultyStudents(@RequestParam long facultyId) {
        return facultyService.get(facultyId).getStudents();
    }

    @GetMapping("/longestName")
    public String getLongestName() {
        return facultyService.getLongestName();
    }
}
