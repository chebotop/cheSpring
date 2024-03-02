package ru.firstapp.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.firstapp.model.Course;
import ru.firstapp.repository.CourseRepository;
import ru.firstapp.service.CourseService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    @Override
    public List<Course> findAllCourse() {
        return repository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course findByTitle(String title) {
        return repository.findCourseByTitle(title);
    }

    @Override
    public Course updateCourse(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(String title) {
        repository.deleteByTitle(title);
    }
}
