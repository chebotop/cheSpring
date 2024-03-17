package ru.firstapp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.firstapp.dao.StudentDao;
import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;
import java.util.Queue;

@Setter
@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);
    @PersistenceContext
    private final EntityManager entityManager;


    /**
     * Находит студента по идентификатору.
     *
     * @param id Идентификатор студента.
     * @return Найденный студент или null, если студент не найден.
     */
    @Override
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional
    public Student findByPersonId(Long personId) {
        try {
            return entityManager.createQuery("SELECT s FROM Student s WHERE s.person.id = :personId", Student.class)
                    .setParameter("personId", personId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        }
    }
    /**
     * Возвращает список всех студентов.
     *
     * @return Список студентов.
     */
    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("from Student", Student.class).getResultList();
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }    }


    /**
     * Шаблон удаления через сущности. Что то не сработало. Удалил через HQL
     * {@code @Transactional} для управления транзакциями
     * @param entity Студент для удаления.
     */
//    @Override
//    @Transactional
//    public void delete(Student entity) {
//        if (entityManager.contains(entity)) {
//            entityManager.remove(entity);
//        } else {
//            entityManager.remove(entityManager.merge(entity));
//        }
//    }

    /**
     * Удаляет студента из базы данных по идентификатору, используя HQL
     * {@code @Transactional} для управления транзакциями
     * @param id идентификатор студента, которого нужно удалить
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
                logger.info("Student with id {} deleted successfully", id);
            } else {
                logger.warn("Student with id {} not found", id);
            }
        } catch (Exception e) {
            logger.error("Exception while deleting student with id {}: {}", id, e.getMessage(), e);
        }
    }
}
