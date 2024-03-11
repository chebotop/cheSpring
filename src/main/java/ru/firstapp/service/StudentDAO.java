package ru.firstapp.service;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import ru.firstapp.model.Student;

import java.util.List;

public interface StudentDAO extends GenericDao<Student, Long> {

    Student findById(Long aLong);
    List<Student> findAll();
    Student save(Student entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void delete(Student entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void deleteById(Long aLong) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
}
