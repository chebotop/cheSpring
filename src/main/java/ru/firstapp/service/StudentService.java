package ru.firstapp.service;

import ru.firstapp.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAllStudent();


    // rename from findAllById
    Student saveStudent(Student student);
    Student findByEmail(String email);
    Student updateStudent(Student student);
    void deleteStudent(String email);


    List<Student> findAllStudentsByCourseId(Long studentIds);

}