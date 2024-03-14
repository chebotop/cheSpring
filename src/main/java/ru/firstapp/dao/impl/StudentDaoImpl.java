package ru.firstapp.dao.impl;

import jakarta.transaction.*;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.firstapp.dao.StudentDao;
import ru.firstapp.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;

@Setter
@Repository
public class StudentDaoImpl implements StudentDao {
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);
    private SessionFactory sessionFactory;

    /**
     * Находит студента по идентификатору.
     *
     * @param id Идентификатор студента.
     * @return Найденный студент или null, если студент не найден.
     */
    @Override
    public Student findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        }
    }

    /**
     * Возвращает список всех студентов.
     *
     * @return Список студентов.
     */
    @Override
    public List<Student> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    /**
     * Удаляет студента из базы данных.
     * {@code @Transactional} для управления транзакциями
     * @param entity Студент для удаления.
     */
    @Override
    @Transactional
    public void delete(Student entity) {
        Session session = sessionFactory.openSession();
        session.remove(entity);
    }

    /**
     * Удаляет студента из базы данных по идентификатору.
     * {@code @Transactional} для управления транзакциями
     * @param id идентификатор студента, которого нужно удалить
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            String hql = "DELETE FROM Student s WHERE s.id = :id";
            int affectedRows = session.createMutationQuery(hql)
                    .setParameter("id", id)
                    .executeUpdate();

            logger.info("Rows deleted: {}", affectedRows);
        } catch (Exception e) {
            logger.error("Exception while deleting student with id {}: {}", id, e.getMessage(), e);
        }
    }
}
