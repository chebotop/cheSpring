package ru.firstapp.service;

import ru.firstapp.model.Course;

import java.util.List;

public interface CourseService {
    Course findCourseById(Long id); // todo
    List<Course> findAllCourse();

    Course saveCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(String title);
    List<Course> findAllByTeacherId(Long teacherId);

    // новый метод для назначения студента курсу
    List<Course> findAllByStudentId(Long studentId);
    List<Course> findAllByIds(List<Long> courseIds);

}
