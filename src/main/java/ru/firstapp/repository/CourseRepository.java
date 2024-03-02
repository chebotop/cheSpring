package ru.firstapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.firstapp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    void deleteByTitle(String title);
    Course findCourseByTitle(String name);
}
