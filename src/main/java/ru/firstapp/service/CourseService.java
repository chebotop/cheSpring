package ru.firstapp.service;

import ru.firstapp.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAllCourse();
    Course saveCourse(Course course);
    Course findByTitle(String title); // todo нужна еще категория курса, к примеру
    Course updateCourse(Course course);
    void deleteCourse(String title);
}
