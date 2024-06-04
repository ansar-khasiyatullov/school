package ru.hogwarts.school.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;

@Service
public class FacultyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        LOGGER.info("Faculty.add was invoked!");
        return repository.save(faculty);
    }

    public Faculty get(long id) {
        LOGGER.info("Faculty.get was invoked!");
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public boolean delete(long id) {
        LOGGER.info("Faculty.delete was invoked!");
        return repository.findById(id).map(entity -> {
            repository.delete(entity);
            return true;
        }).orElse(false);
    }

    public Faculty update(Faculty faculty) {
        LOGGER.info("Faculty.update was invoked!");
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public Collection<Faculty> getByColorOrName(String color, String name) {
        LOGGER.info("Faculty.getByColorOrNameIgnoreCase was invoked!");
        return repository.findAllByColorOrNameIgnoreCase(color, name);
    }

    public Collection<Faculty> getAll() {
        LOGGER.info("Faculty.getAll was invoked!");
        return repository.findAll();
    }

    public String getLongestName(){
        LOGGER.info("Faculty.getLongestName was invoked!");
        return repository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
