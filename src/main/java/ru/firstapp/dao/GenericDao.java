package ru.firstapp.dao;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;

import java.util.List;

public interface GenericDao<T, Id> {
    T findById(Id id);
    List<T> findAll();
    T save(T entity);
    void deleteById(Id id);
}
