package ru.firstapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.firstapp.model.Student;
import ru.firstapp.repository.StudentRepository;
import ru.firstapp.service.StudentService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    @Override
    public List<Student> findAllStudent() {
        return repository.findAll();
    }


    @Override
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student findByEmail(String email) {
        return repository.findStudentByEmail(email);
    }

    @Override
    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String email) {
        repository.deleteByEmail(email);
    }


    @Override
    public List<Student> findAllStudentsByCourseId(Long courseId) { // поиск студентов по курсу
        return repository.findAllStudentsByCourseId(courseId);
    }

}
