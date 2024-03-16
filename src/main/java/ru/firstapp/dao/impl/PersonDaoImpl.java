package ru.firstapp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.firstapp.dao.PersonDao;
import ru.firstapp.model.Person;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Transactional
public class PersonDaoImpl implements PersonDao {
    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Находит персону по идентификатору.
     *
     * @param id
     * @return Найденная персона или null, если персона не найдена.
     */
    @Override
    public Person findById(Long id) {
        return entityManager.find(Person.class, id);
    }


    /**
     * Возвращает список всех персон.
     *
     * @return Список персон.
     */
    @Override
    public List<Person> findAll() {
        return entityManager.createQuery("from Person", Person.class).getResultList();
    }

    @Override
    @Transactional
    public Person save(Person entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    /**
     * Удаляет персону из базы данных.
     * @param entity Персона для удаления.
     */
    @Override
    @Transactional
    public void delete(Person entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }
}
