package ru.firstapp.dao;

import jakarta.transaction.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.firstapp.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public class StudentDAOImpl implements StudentDAO {
    private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
    }

    /**
     * Удаляет студента из базы данных.
     *
     * @param entity Студент для удаления.
     */
    @Override
    public void delete(Student entity) throws SystemException, HeuristicRollbackException, HeuristicMixedException,
                                              RollbackException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = (Transaction) session.beginTransaction();
            session.remove(entity);
            tx.commit();
        }   catch (RuntimeException | RollbackException | HeuristicMixedException | HeuristicRollbackException |
                   SystemException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void deleteById(Long aLong) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = (Transaction) session.beginTransaction();

            String hql = "DELETE FROM Student s WHERE s.id = :id";
            int affectedRows = session.createMutationQuery(hql)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
            logger.info("Rows deleted: {}", affectedRows);
        }   catch (RuntimeException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
