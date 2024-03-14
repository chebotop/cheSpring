package ru.firstapp.dao;

import jakarta.transaction.Transactional;
import ru.firstapp.model.Person;

import java.util.List;

public interface PersonDao {
    Person findById(Long id);
    List<Person> findAll();
    Person save(Person entity);
    void delete(Person entity);
}
