package ru.firstapp.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.firstapp.model.Teacher;
import ru.firstapp.repository.TeacherRepository;
import ru.firstapp.service.TeacherService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository repository;
    @Override
    public List<Teacher> findAllTeacher() {
        return repository.findAll();
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return repository.save(teacher);
    }

    @Override
    public Teacher findByEmail(String email) {
        return repository.findTeacherByEmail(email);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return repository.save(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(String email) {
        repository.deleteByEmail(email);
    }
}
