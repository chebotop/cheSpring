package ru.firstapp.dao;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;

import java.util.List;

public interface GenericDao<T, ID> {
    T findById(ID id);
    List<T> findAll();
    T save(T entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void delete(T entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void deleteById(ID id) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException;


}
