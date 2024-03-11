package ru.firstapp.dao;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import ru.firstapp.model.Student;

import java.util.List;

public interface StudentDAO extends GenericDao<Student, Long> {

    public Student findById(Long aLong);
    public List<Student> findAll();
    public Student save(Student entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    public void delete(Student entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    public void deleteById(Long aLong) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
}
