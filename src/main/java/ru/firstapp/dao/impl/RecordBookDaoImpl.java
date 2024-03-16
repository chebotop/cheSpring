package ru.firstapp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.firstapp.dao.RecordBookDao;
import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class RecordBookDaoImpl implements RecordBookDao {
    private static final Logger logger = LoggerFactory.getLogger(RecordBookDaoImpl.class);

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Зная ID зачетной книжки извлекаем связанную с ней сущность Student.
     * @param recordBook обьект, с которым связан студент.
     * @return Сущность Student
     */
    @Override
    public Student findStudent(RecordBook recordBook) {
        if (recordBook == null || recordBook.getId() == null) {
            return null;
        }
        return entityManager.createQuery("SELECT s FROM Student s WHERE s.recordBook.id = :recordBookId", Student.class)
                .setParameter("recordBookId", recordBook.getId())
                .getSingleResult();
    }

    @Override
    public List<RecordBook> findAll() {
        return entityManager.createQuery("from RecordBook", RecordBook.class).getResultList();
    }

    @Override
    @Transactional
    public RecordBook save(RecordBook entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        }   else {
            return entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void delete(RecordBook entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }
}
