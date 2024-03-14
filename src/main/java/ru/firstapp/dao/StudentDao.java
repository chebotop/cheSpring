package ru.firstapp.dao;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import ru.firstapp.model.Student;

import java.util.List;

public interface StudentDao extends GenericDao<Student, Long> {

    Student findById(Long aLong);
    List<Student> findAll();
    Student save(Student entity);
    void delete(Student entity);
    void deleteById(Long aLong);
}
