package ru.firstapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.firstapp.model.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    void deleteByTitle(String title);
    Course findCourseById(Long id);

    List<Course> findAllByTeacherId(Long teacherId);

}
