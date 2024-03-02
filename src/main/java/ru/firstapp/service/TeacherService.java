package ru.firstapp.service;

import ru.firstapp.model.Teacher;

import java.util.List;

public interface TeacherService {
        List<Teacher> findAllTeacher();
        Teacher saveTeacher(Teacher teacher);
        Teacher findByEmail(String email);
        Teacher findById(Long id);
        Teacher updateTeacher(Teacher teacher);
        void deleteTeacher(String email);
        void deleteTeacher(Long id);
}