package ru.firstapp.service;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;

import java.util.List;

public interface GenericDao<T, Id> {
    T findById(Id id);
    List<T> findAll();
    T save(T entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void delete(T entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void deleteById(Id id) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;


}
